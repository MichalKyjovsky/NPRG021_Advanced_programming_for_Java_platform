package cz.cuni.mff.java.mods.printers.external;

import cz.cuni.mff.java.mods.printers.internal.InternalPrinter;

public class Printer {
    public static void print(String msg) {
        System.out.println("Printer: " + msg);
    }

    public static void printViaInternal(String msg) {
        InternalPrinter.print(msg);
    }
}
