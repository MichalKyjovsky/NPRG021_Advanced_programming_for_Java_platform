package cz.cuni.mff.java.examservletloader;

import cz.cuni.mff.java.exam.Examlet;

import java.io.IOException;
import java.nio.file.*;

public class Main {

    public static void main(String[] args) {
        if (args.length > 0) {
            Path dir = Path.of(args[0]);
            if (! Files.isDirectory(dir)) {
                System.err.println("Given path is not a directory!" + System.lineSeparator() + "Exiting application.");
                System.exit(1);
            } else {
                try {
                    Monitor monitor = new Monitor(dir);
                    monitor.service();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
