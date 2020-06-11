package cz.cuni.mff.java.plugin;


import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Main {
  
/*
  public static class Internal implements Plugin {
        @Override
        public void perform(String msg) {
            System.out.println("Internal: " + msg);
        }
    }
  }*/

    public static void main(String[] args) {
   /*java.util.ArrayList<Plugin> plugins = new java.util.ArrayList<>();
        Class<Plugin> pluginIface = Plugin.class;
        plugins.add(new Internal());

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
                plugins.add((Plugin) cls.getDeclaredConstructor().newInstance());
            } catch (ClassNotFoundException e) {
                System.out.println("The class " + arg + " does not exists.");
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                System.out.println("It is not possible to instantiate " + arg);
            }
        }
     */
//        Class cls = null;
//        try{
//            cls = Class.forName(Plugin.class.getName());
//            cls.getMethod("perform", String.class).invoke(new P1(), "Ahoj");
//        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
//            e.printStackTrace();
//        }

        ServiceLoader<Plugin> sl = ServiceLoader.load(Plugin.class);

        for (Plugin pl : sl) {
            pl.perform("Ahoj");
        }
    }
}
