package cz.cuni.mff.ms.kyjovsm;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class Main {
    private static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        AnnotationsFinder af = new AnnotationsFinder();
        try {
            List<Method> clsMethods = Arrays.stream(af.findMethods(Class.forName(args[0])))
                    .filter(method -> !method.getDeclaringClass().getName().equals(Object.class.getName()))
                    .filter(method -> !Modifier.isAbstract(method.getModifiers()))
                    .collect(Collectors.toList());
            List<Method> annotatedMethods = af.filterAnnotatedMethods(clsMethods);

            if (annotatedMethods.size() == 0) {
                System.out.printf("No annotations in \"%s\"",getClassName(args[0]));
                System.exit(0);
            }

            printHeader(Class.forName(args[0]).getName());
            af.getAnnotatedMethodsOutput(annotatedMethods)
                    .forEach(System.out::println);

        } catch (Exception e) {
            if (args.length > 0) {
                System.out.printf("\"%s\" does not exists", getClassName(args[0]));
                System.out.println();
            } else {
                System.out.printf("\"\" does not exists");
                System.out.println();
            }
        }
    }

    private static String getClassName(String inputName) {
        if (inputName.contains(".")) {
            return inputName.split("\\.")[inputName.split("\\.").length - 1];
        } else {
            return inputName;
        }
    }

    private static void printHeader(String name) {
        try {
            String className = Class.forName(name).getSimpleName();
            System.out.printf("Annotations in \"%s\":", className);
            System.out.println();
        } catch (ClassNotFoundException classNotFoundException) {
            logger.log(Level.SEVERE, "Class on input was unable to load.",classNotFoundException);
        }
    }
}
