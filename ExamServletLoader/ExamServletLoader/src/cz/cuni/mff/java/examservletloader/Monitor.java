package cz.cuni.mff.java.examservletloader;

import cz.cuni.mff.java.exam.Examlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;
import java.util.*;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import static java.nio.file.StandardWatchEventKinds.*;

public class Monitor implements Examlet {

    private final Path dir;
    private final WatchService watchService;
    private final boolean trace;
    private Map<String, String> jarAndClasses;
    private final Map<WatchKey, Path> keys;

    public Monitor(Path dir) throws IOException {
        this.dir = dir;
        this.trace = true;
        this.watchService = FileSystems.getDefault().newWatchService();
        this.keys = new HashMap<>();
        this.jarAndClasses = new HashMap<>();
        register(dir);
    }

    private void register(Path dir) {
        try {
            WatchKey key = this.dir.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

            if (trace) {
                Path prev = keys.get(key);
                if (prev == null) {
                    System.out.format("register: %s\n", dir);
                } else {
                    if (!dir.equals(prev)) {
                        System.out.format("update: %s -> %s\n", prev, dir);
                    }
                }
            }
            keys.put(key, dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>) event;
    }

    @Override
    public void service() {
        while (true) {
            WatchKey key = null;
            try {
                key = watchService.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Path curDir = keys.get(key);
            if (curDir == null) {
                System.err.println("WatchKey was not recognized!");
                continue;
            }
            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind kind = event.kind();

                if (kind == OVERFLOW) {
                    //Not necessary to solve
                    continue;
                }

                if (kind == ENTRY_CREATE) {
                    WatchEvent<Path> ev = cast(event);
                    Path name = ev.context();
                    Path child = curDir.resolve(name);

                    if (isJar(child)) {
                        String clazz = manifestContainExamlet(child);
                        jarAndClasses.put(child.toString(), clazz);
                        URLClassLoader urlClassLoader = null;
                        try {
                            URL[] urls = {child.toUri().toURL()};
                            urlClassLoader = URLClassLoader.newInstance(urls);
                            Class<?> cls = urlClassLoader.loadClass(clazz);
                            if (Examlet.class.isAssignableFrom(cls)) {
                                System.out.println("New Examlet: " + cls.getName() + "is loaded.");
                                System.out.print("\t");
                                Examlet e = (Examlet) cls.getDeclaredConstructor().newInstance();
                                e.service();
                                urlClassLoader.close();
                            }
                        } catch (ClassNotFoundException
                                | NoSuchMethodException
                                | InstantiationException
                                | IllegalAccessException
                                | InvocationTargetException
                                | IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (kind == ENTRY_DELETE) {
                    WatchEvent<Path> ev = cast(event);
                    Path name = ev.context();
                    Path child = curDir.resolve(name);
                    System.out.println("Previously added Examlet : " +
                            jarAndClasses.get(child.toString()) +
                            " has been removed.");
                    jarAndClasses.remove(child);
                }

                boolean valid = key.reset();
                if (!valid) {
                    keys.remove(key);

                    if (keys.isEmpty()) {
                        break;
                    }
                }
            }
        }
    }

    private boolean isJar(Path file) {
        return file.toString().endsWith(".jar");
    }

    private String manifestContainExamlet(Path jar) {
        try {
            Manifest mf = new JarFile(jar.toString()).getManifest();
            Attributes atts = mf.getMainAttributes();
            if (atts.containsKey(new Attributes.Name("Examlet"))) {
                return atts.getValue(new Attributes.Name("Examlet"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
