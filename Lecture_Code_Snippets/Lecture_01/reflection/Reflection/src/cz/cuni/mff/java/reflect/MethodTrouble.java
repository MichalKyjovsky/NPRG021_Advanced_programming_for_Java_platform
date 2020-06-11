package cz.cuni.mff.java.reflect;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MethodTrouble<T> {

    public void lookup(T value) {
    }

    public static void main(String[] argv) throws Exception {
        Class<?> c = (new MethodTrouble<Integer>()).getClass();
        Method[] methods = c.getDeclaredMethods();
        System.out.println(Arrays.stream(methods).map(Method::toString).collect(Collectors.joining("\n")));

        // Method m = c.getMethod("lookup", Integer.class);
        // does not work - throws NoSuchMethodException
    }
}
