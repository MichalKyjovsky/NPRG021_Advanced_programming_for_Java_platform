package cz.cuni.mff.java.plugins;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Loader {
    public static <T> List<T> loadPlugins(Class<T> pluginClass, String... pluginNames) {
        List<T> ret = new ArrayList<>();

        for (String pluginName : pluginNames) {
            try {
                Class<?> cls = Class.forName(pluginName);
                if (cls.isAnnotation() || cls.isArray() || cls.isEnum() || cls.isPrimitive() || cls.isInterface()) {
                    Logger.getLogger(Loader.class.getName()).log(Level.INFO, "{0} is not a class", pluginName);
                    continue;
                }
                if (! pluginClass.isAssignableFrom(cls)) {
                    Logger.getLogger(Loader.class.getName()).log(Level.INFO, "{0} does not implement {1}", new Object[]{pluginName, pluginClass.getName()});
                    continue;
                }
                ret.add(pluginClass.cast(cls.getDeclaredConstructor().newInstance()));
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, "Class " + pluginName + " cannot be found", ex);
            } catch (IllegalAccessException | InstantiationException |NoSuchMethodException | InvocationTargetException ex) {
                Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, "Class " + pluginName + " cannot be instantiated", ex);
            }
        }

        return ret;
    }
}
