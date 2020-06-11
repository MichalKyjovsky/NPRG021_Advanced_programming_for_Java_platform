package cz.cuni.mff.java.xmldb;

import org.jdom2.JDOMException;

import java.io.IOException;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws JDOMException, IOException {
        DB db = new DB(Path.of("database"));

        Data data = new Data("1", "Hello world");
        db.store(data);
    }
}
