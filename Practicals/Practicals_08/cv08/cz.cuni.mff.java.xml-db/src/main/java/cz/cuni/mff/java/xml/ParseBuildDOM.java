package cz.cuni.mff.java.xml;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ParseBuildDOM {

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
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(args[0]);
            Element root = doc.getDocumentElement();
            NodeList nl = root.getElementsByTagName("target");
            for (int i = 0; i < nl.getLength(); i++) {
                Node n = nl.item(i);
                Element el = (Element) n;
                String name = el.getAttribute("name");
                // for measurements, we comment out printing (otherwise, we measure speed of printing)
                //System.out.println(name);
                String desc = el.getAttribute("description");
                if (desc != null && !desc.isEmpty()) {
                    //System.out.println("  " + desc);
                }                
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(ParseBuildDOM.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }    
}
