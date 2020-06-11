package cz.cuni.mff.ms.kyjovsm;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class AnnotationsFinder {
    private Logger logger = Logger.getLogger(AnnotationsFinder.class.getName());

    public Method[] findMethods(Class<?> cls) {
        Set<Method> clsMethods = new HashSet<>();
        Method[] declaredMethods = cls.getDeclaredMethods();
        Method[] methods = cls.getMethods();
        if (cls.getSuperclass() != null) {
            Class<?> superClass  = cls.getSuperclass();
            Method[] superClassMethods = findMethods(superClass);
            clsMethods.addAll(Arrays.asList(superClassMethods));
        }
        clsMethods.addAll(Arrays.asList(declaredMethods));
        clsMethods.addAll(Arrays.asList(methods));
        return clsMethods.toArray(new Method[clsMethods.size()]);
    }

    public List<Method> filterAnnotatedMethods(List<Method> clsMethods) {
        return clsMethods
                 .stream()
                 .filter(method -> method.getDeclaredAnnotations().length > 0)
                 .filter(method -> method.getAnnotations().length > 0)
                 .collect(Collectors.toList());
    }

    public List<String> getAnnotatedMethodsOutput (List<Method> annotatedMethods) {
        StringBuilder sb = new StringBuilder();
        List<String> outputList = new ArrayList<>();
        annotatedMethods.sort((o1, o2) -> {
            String name1 = o1
                    .getName()
                    .split("\\.")[o1.getName().split("\\.").length - 1];
            String name2 = o2
                    .getName()
                    .split("\\.")[o1.getName().split("\\.").length - 1];
            return name1.compareTo(name2);
        });

        for (Method method : annotatedMethods) {
            int clsNameLen = method.getName().split("\\.").length;
            String methodName = method.getName().split("\\.")[clsNameLen - 1];
            List<Annotation> annotationsSet =
                    new ArrayList<>(Arrays.asList(method.getDeclaredAnnotations()));
            for (Annotation at : method.getAnnotations()) {
                if (!annotationsSet.contains(at)) {
                    annotationsSet.add(at);
                }
            }

            for (Annotation annotation : annotationsSet) {
                String annot = annotation.toString();
                int annotLen = annot.split("\\.").length;
                annot = annot.split("\\.")[annotLen - 1].replaceAll("\\(.*?\\)","");
                if (annot.startsWith("@")) {
                    sb.append(annot).append(" ");
                } else {
                    sb.append('@').append(annot).append(" ");
                }
            }
            outputList.add(sb.toString() + methodName + "()");
            sb.delete(0,sb.length());
        }
        return outputList;
    }
}
