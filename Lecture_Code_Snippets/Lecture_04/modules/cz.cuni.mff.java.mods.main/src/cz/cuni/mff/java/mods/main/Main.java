package cz.cuni.mff.java.mods.main;

import cz.cuni.mff.java.mods.printers.external.Printer;
//import cz.cuni.mff.java.mods.printers.internal.InternalPrinter;

public class Main {
    public static void main(String[] args) {
        Printer.print("Hello modular world");

        //InternalPrinter.print("Hello modular world");

        Printer.printViaInternal("Hello modular world");

    }
}
