package cz.cuni.mff.java.reflect;

public class MyClass2 extends MyClass implements Iface {

    public MyClass2() {
    }

    public MyClass2(int a) {
        this.a = a;
    }

    @Override
    public int foo(long a, boolean b) {
        return (int)a;
    }
}
