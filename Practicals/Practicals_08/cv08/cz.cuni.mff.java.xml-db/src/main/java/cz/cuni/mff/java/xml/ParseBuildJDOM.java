package cz.cuni.mff.java.xml;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class ParseBuildJDOM {

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            run(args);
        }
        long start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            run(args);
        }
        long end = System.nanoTime();
        System.out.println("Time: " + ((end - start)/1000/1000));
        //run(args);
    }
    
    private static void run(String[] args) {
        try {
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(args[0]);
            doc.getRootElement().getChildren("target").forEach(e -> {
                String name = e.getAttributeValue("name");
                //System.out.println(name);
                String desc = e.getAttributeValue("description");
                if (desc != null && !desc.isEmpty()) {
                   //System.out.println("  " + desc);
                }
            });
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(ParseBuildJDOM.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
