package cz.cuni.mff.java.testing;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Testing {

    private static class ClassInfo {
        Priority priority;
        Class<?> clazz;
        TesterInfo ti;
        Method[] before;
        Method[] after;
        Method[] tests;

        public ClassInfo(Priority priority, Class<?> clazz, TesterInfo ti, List<Method> before, List<Method> after, List<Method> tests) {
            this.priority = priority;
            this.clazz = clazz;
            this.ti = ti;
            this.before = before.toArray(new Method[0]);
            this.after = after.toArray(new Method[0]);
            this.tests = tests.toArray(new Method[0]);
        }

        public Priority getPriority() {
            return priority;
        }

        public Class<?> getClazz() {
            return clazz;
        }

        public String createdBy() {
            return ti != null ? ti.createdBy() : "N/A";
        }

        public String lastModified() {
            return ti != null ? ti.lastModified() : "N/A";
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Nothing to test. Exiting.");
            System.exit(0);
        }

        List<ClassInfo> classes = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Path.of(args[0]));
            for (String className : lines) {
                Class<?> clazz;
                try {
                    clazz = Class.forName(className.trim());
                } catch (ClassNotFoundException ex) {
                    System.out.println("Unknown class " + className);
                    continue;
                }

                List<Method> beforeMethods = new ArrayList<>();
                List<Method> afterMethods = new ArrayList<>();
                List<Method> testMethods = new ArrayList<>();
                for (Method m : clazz.getMethods()) {
                    if (m.isAnnotationPresent(Before.class)) {
                        beforeMethods.add(m);
                    } else if (m.isAnnotationPresent(After.class)) {
                        afterMethods.add(m);
                    } else if (m.isAnnotationPresent(Test.class)) {
                        testMethods.add(m);
                    }
                }

                if (clazz.isAnnotationPresent(TesterInfo.class)) {
                    TesterInfo ti = clazz.getAnnotation(TesterInfo.class);
                    classes.add(new ClassInfo(ti.priority(), clazz, ti, beforeMethods, afterMethods, testMethods));
                } else {
                    classes.add(new ClassInfo(Priority.LOW, clazz, null, beforeMethods, afterMethods, testMethods));
                }
            }
        } catch (IOException ex) {
            System.out.println(ex);
            System.out.println("Cannot read input file");
            System.exit(1);
        }

        classes.sort(Comparator.comparingInt(a -> -a.getPriority().getVal()));

        int passed = 0, failed = 0, skipped = 0;
        for (ClassInfo classInfo : classes) {
            Class<?> clazz = classInfo.getClazz();

            System.out.println("Processing class: " + clazz.getName());
            System.out.println("Tester: " + classInfo.createdBy());
            System.out.println("Last modified: " + classInfo.lastModified());

            Object obj;
            try {
                obj = clazz.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                System.out.println("Cannot create instance of " + clazz.getName());
                System.out.println("Skipping class");
                continue;
            }

            for (Method m: classInfo.tests) {
                boolean ignore = false;
                Test annot = m.getAnnotation(Test.class);
                if (annot.enabled()) {
                    try {
                        for (Method mBefore : classInfo.before) {
                            try {
                                mBefore.invoke(obj);
                            } catch (Throwable e) {
                                System.out.printf("Before method %s failed, ignoring the test\n", mBefore.getName());
                                ignore = true;
                            }
                        }
                        if (ignore) {
                            continue;
                        }
                        m.invoke(obj);
                        for (Method mAfter : classInfo.after) {
                            try {
                                mAfter.invoke(obj);
                            } catch (Throwable e) {
                                System.out.printf("Before method %s failed, ignoring the test\n", mAfter.getName());
                                ignore = true;
                            }
                        }
                        if (ignore) {
                            continue;
                        }
                        if (annot.expectedExceptions().length == 0) {
                            System.out.printf("Test %s passed\n", m.getName());
                            passed++;
                        } else {
                            System.out.printf("Test %s failed, an exception expected\n", m.getName());
                            failed++;
                        }
                    } catch (Throwable ex) {
                        if (ex instanceof InvocationTargetException) {
                            if (annot.expectedExceptions().length == 0) {
                                failed++;
                                System.out.printf("Test %s failed with the exception %s\n", m.getName(), ex.getCause().getClass());
                            } else {
                                boolean ok = false;
                                for (Class<? extends Throwable> exClass: annot.expectedExceptions()) {
                                    if (exClass.isAssignableFrom(ex.getCause().getClass())) {
                                        passed++;
                                        System.out.printf("Test %s passed with the exception %s\n", m.getName(), exClass);
                                        ok = true;
                                        break;
                                    }
                                }
                                if (!ok) {
                                    failed++;
                                    System.out.printf("Test %s failed with the exception %s\n", m.getName(), ex.getCause().getClass());
                                }
                            }
                        } else {
                            System.out.println("Unexpected exception " + ex);
                        }
                    }
                } else {
                    skipped++;
                    System.out.printf("Test %s skipped\n", m.getName());
                }
            }
            System.out.println("=========================================================");
        }
        System.out.printf("\n\nPassed: %d, Failed %d, Skipped %d\n", passed, failed, skipped);
    }
}
