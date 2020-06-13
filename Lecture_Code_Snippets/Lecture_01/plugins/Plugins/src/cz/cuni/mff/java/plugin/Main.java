package cz.cuni.mff.java.plugin;

import java.lang.reflect.InvocationTargetException;

public class Main {

    public static class Internal implements Plugin {
        @Override
        public void perform(String msg) {
            System.out.println("Internal: " + msg);
        }
    }

    public static void main(String[] args) {
        java.util.ArrayList<Plugin> plugins = new java.util.ArrayList<>();
        Class<Plugin> pluginIface = Plugin.class;
        plugins.add(new Internal());
        plugins.add(new P1());
        plugins.add(new P3());
        plugins.add(new P4());

        for (String arg : args) {
            try {
                Class<?> cls = Class.forName(arg);
                if (cls.isArray() || cls.isInterface() || cls.isPrimitive() || cls.isEnum() || cls.isAnnotation()) {
                    System.out.println(cls.getName() + " is not a class.");
                    continue;
                }
                if (!pluginIface.isAssignableFrom(cls)) {
                    System.out.println("The class " + cls.getName() + " does not implement the interface Plugin.");
                    continue;
                }
                plugins.add(pluginIface.cast(cls.getDeclaredConstructor().newInstance()));
            } catch (ClassNotFoundException e) {
                System.out.println("The class " + arg + " does not exists.");
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                System.out.println("It is not possible to instantiate " + arg);
            }
        }

        plugins.forEach(pl -> pl.perform("Hello world"));
    }
}
