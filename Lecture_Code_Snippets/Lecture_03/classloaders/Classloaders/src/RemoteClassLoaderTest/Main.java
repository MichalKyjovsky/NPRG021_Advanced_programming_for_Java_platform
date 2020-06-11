package RemoteClassLoaderTest;

public class Main {

    public static void main(String[] argv) throws Exception {
        ClassLoader cl = new URLClassLoader("java.lang.String");
        MyInterface obj = (MyInterface) Class.forName("RemoteClassLoaderTest.MyClass", true, cl).getConstructor().newInstance();

        obj.print();
    }

}
