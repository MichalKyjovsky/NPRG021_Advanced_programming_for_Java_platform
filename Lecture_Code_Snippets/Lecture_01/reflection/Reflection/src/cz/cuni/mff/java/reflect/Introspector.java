package cz.cuni.mff.java.reflect;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Introspector {

    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName(args[0]);

        System.out.println("Name:         " + cls.getName());
        System.out.println("is primitive: " + cls.isPrimitive());
        System.out.println("is array:     " + cls.isArray());
        System.out.println("is interface: " + cls.isInterface());
        System.out.println("package:      " + cls.getPackage());
        System.out.println("Module:       " + cls.getModule());
        System.out.println("superclass:   " + cls.getSuperclass());
        System.out.print("interfaces:   ");
        Class<?>[] ifaces = cls.getInterfaces();
        System.out.println(Arrays.stream(ifaces).map(Class::getName).collect(Collectors.joining(", ")));

        System.out.print("methods:\n ");
        Method[] mt = cls.getMethods();
        System.out.println(Arrays.stream(mt).map(Method::toString).collect(Collectors.joining("\n")));

        System.out.println("declared methods\n ");
        mt = cls.getDeclaredMethods();
        System.out.println(Arrays.stream(mt).map(Method::toString).collect(Collectors.joining("\n")));

        System.out.println("fields:\n ");
        Field[] ft = cls.getFields();
        System.out.println(Arrays.stream(ft).map(Field::toString).collect(Collectors.joining("\n")));

        System.out.println("declared fields:\n ");
        ft = cls.getDeclaredFields();
        System.out.println(Arrays.stream(ft).map(Field::toString).collect(Collectors.joining("\n")));

        System.out.println("constructors:\n ");
        Constructor<?>[] ct = cls.getConstructors();
        System.out.println(Arrays.stream(ct).map(Constructor::toString).collect(Collectors.joining("\n")));

        System.out.println("declared constructors:\n ");
        ct = cls.getDeclaredConstructors();
        System.out.println(Arrays.stream(ct).map(Constructor::toString).collect(Collectors.joining("\n")));

        if (args[1].equals("i")) {
            System.out.println();
            System.out.println("new instance...");
            Object obj = cls.getDeclaredConstructor().newInstance();
            System.out.println(obj);

            System.out.println();
            System.out.println("invoking method foo(long, boolean)");
            Method foo = null;
            try {
                foo = cls.getMethod("foo", long.class, boolean.class);
            } catch (NoSuchMethodException | SecurityException e) {
                System.out.println("no such method");
                System.exit(0);
            }
            System.out.println("Result: " + foo.invoke(obj, 10L, true));


        }

    }

}
