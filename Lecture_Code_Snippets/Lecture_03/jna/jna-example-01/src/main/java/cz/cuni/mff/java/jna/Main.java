package cz.cuni.mff.java.jna;

public class Main {

    public static void main(String[] args) {
        JNAApiInterface jnaLib = JNAApiInterface.INSTANCE;
        jnaLib.printf("Hello World\n");
    }

}
