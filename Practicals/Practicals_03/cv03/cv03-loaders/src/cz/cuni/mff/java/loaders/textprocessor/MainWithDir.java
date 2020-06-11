package cz.cuni.mff.java.loaders.textprocessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public class MainWithDir {
    public static void main(String[] args) {
        List<TextProcessor> processors = null;
        if (args.length > 0) {
            Path dir = Path.of(args[0]);
            if (! Files.isDirectory(dir)) {
                System.out.println("Given path is not a directory");
                System.exit(1);
            }
            try {
                List<Path> jars = Files.list(dir).filter(p -> p.toString().endsWith(".jar")).collect(Collectors.toList());
                List<URL> urls = new ArrayList<>();
                for (Path path: jars) {
                    try {
                        urls.add(path.toUri().toURL());
                    } catch (MalformedURLException ex) {
                        System.out.println("WARNING: bad url " + path);
                        System.out.println("Skipping");
                    }
                }

                URLClassLoader urlClassLoader = URLClassLoader.newInstance(urls.toArray(new URL[0]));

                ServiceLoader<TextProcessor> serviceLoader = ServiceLoader.load(TextProcessor.class, urlClassLoader);
                processors = new ArrayList<>();
                for (TextProcessor textProcessor : serviceLoader) {
                    System.out.println("adding processor");
                    processors.add(textProcessor);
                }
            } catch (IOException ex) {
                System.out.println("Cannot list the directory");
                System.exit(1);
            }

        } else {
            System.out.println("WARNING: no directory with plugins");
            processors = new ArrayList<>();
        }

        try {
            int c;
            StringBuilder sb = new StringBuilder();
            Reader in = new BufferedReader(new InputStreamReader(System.in));
            while ((c = in.read()) != -1) {
                sb.append((char) c);
            }
            String input = sb.toString();
            for (TextProcessor tp : processors) {
                input = tp.process(input);
            }
            System.out.println(input);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
