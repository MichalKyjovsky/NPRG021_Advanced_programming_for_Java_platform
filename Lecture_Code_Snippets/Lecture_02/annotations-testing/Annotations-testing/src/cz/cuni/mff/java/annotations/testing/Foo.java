package cz.cuni.mff.java.annotations.testing;

public class Foo {

    @Test
    public static void m1() {
        System.out.println("Hello from \"m1\".");
    }

    public static void m2() {
        System.out.println("Hello from \"m2\".");
    }

    @Test
    public static void m3() {
        System.out.println("Hello from \"m3\".");
        throw new RuntimeException("Exception from \"m3\"");
    }
}