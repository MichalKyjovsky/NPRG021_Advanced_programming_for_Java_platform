package cz.cuni.mff.java.loaders.textprocessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ServiceLoader;

public class Main {
    public static void main(String[] args) {
        /*List<TextProcessor> processors = null;
        if (args.length > 0) {
            try {
                List<String> lines = Files.readAllLines(Path.of(args[0]));
                processors = Loader.loadPlugins(TextProcessor.class, lines.toArray(new String [0]));
            } catch (IOException ex) {
                System.out.println("Cannot read files with plugins. Exiting");
                System.exit(1);
            }
        } else {A
            System.out.println("Warning. No processors");
            processors = Collections.emptyList();
        }*/
        System.out.println("");
        ServiceLoader<TextProcessor> serviceLoader = ServiceLoader.load(TextProcessor.class, ClassLoader.getSystemClassLoader());
        try {
            int c;
            StringBuilder sb = new StringBuilder();
            Reader in = new BufferedReader(new InputStreamReader(System.in));
            while ((c = in.read()) != -1) {
                sb.append((char) c);
            }
            String input = sb.toString();
            for (TextProcessor tp: serviceLoader) {
                input = tp.process(input);
            }
            System.out.println(input);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
