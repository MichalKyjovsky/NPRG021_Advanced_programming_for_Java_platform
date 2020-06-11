package cz.cuni.mff.java.xmldb;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB {
    private final Path path;
    private final HashMap<Class<?>, Path> dbs = new HashMap<>();

    public DB(Path path) throws IOException {
        this.path = path;
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        } else if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("not a directory");
        }
        Files.list(path).forEach(p -> {
            try {
                Class<?> clazz = fnameToClassName(p);
                dbs.put(clazz, p);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, "Cannot load class, skipping a file " + p, ex);
            }
        });
    }


    public void store(Object o) throws JDOMException, IOException {
        Class<?> clazz = o.getClass();
        Document doc;
        if (dbs.containsKey(clazz)) {
            SAXBuilder builder = new SAXBuilder();
            doc = builder.build(dbs.get(clazz).toFile());
        } else {
            doc = new Document(new Element(classNameToElemName(clazz) + "-db"));
            dbs.put(clazz, Path.of(path.toString(), classNameToFname(clazz)));
        }
        Element root = doc.getRootElement();
        // TODO test existence of the object in DB
        Element newEl = new Element(classNameToElemName(clazz));
        storeFields(newEl, o);
        root.addContent(newEl);

        XMLOutputter outputter = new XMLOutputter();
        outputter.output(doc, Files.newOutputStream(dbs.get(clazz)));
    }

    public <T> List<T> load(Class<T> clazz, String... exprs) throws JDOMException, IOException {
        if (dbs.containsKey(clazz)) {
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(dbs.get(clazz).toFile());
            Element root = doc.getRootElement();
            List<T> ret = new ArrayList<>();
            root.getChildren().forEach(e -> {
                try {
                    T obj = clazz.getConstructor().newInstance();
                    Arrays.stream(clazz.getDeclaredFields()).forEach(field -> {
                        field.setAccessible(true);
                        String name = field.getName();
                        String value = e.getAttribute(name).getValue();
                        try {
                            field.set(obj, value);
                        } catch (IllegalAccessException ex) {
                            ex.printStackTrace();
                        }
                    });
                    ret.add(obj);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                    ex.printStackTrace();
                }
            });
            return ret;
        } else {
            return Collections.emptyList();
        }
    }

    private void storeFields(Element element, Object o) {
        Class<?> clazz = o.getClass();
        // TODO we are processing the declared fields and thus we need to iterate over parents
        Arrays.stream(clazz.getDeclaredFields()).forEach(field -> {
            try {
                field.setAccessible(true);
                String name = field.getName();
                // TODO now we support only String fields
                String value = field.get(o).toString();
                element.setAttribute(name, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    private static String classNameToElemName(Class<?> clazz) {
        return clazz.getName().replaceAll("\\.", "-");
    }

    private static String classNameToFname(Class<?> clazz) {
        return clazz.getName().replaceAll("\\.", "-") + ".db";
    }

    private Class<?> fnameToClassName(Path fname) throws ClassNotFoundException {
        String fs = fname.getFileName().toString();
        fs = fs.substring(0, fs.indexOf('.')).replaceAll("-", ".");
        return Class.forName(fs);
    }
}
