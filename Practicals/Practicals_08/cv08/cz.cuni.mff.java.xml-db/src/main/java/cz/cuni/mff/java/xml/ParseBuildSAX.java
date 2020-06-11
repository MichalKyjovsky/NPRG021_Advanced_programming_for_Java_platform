package cz.cuni.mff.java.xml;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParseBuildSAX {

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
    }
    
    private static void run(String[] args) {        
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(args[0], new DefaultHandler() {
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if (qName.equals("target")) {
                        String name = attributes.getValue("name");
                        //System.out.println(name);
                        String desc = attributes.getValue("description");
                        if (desc != null && !desc.isEmpty()) {
                            //System.out.println("  " + desc);
                        }  
                    }
                }
                
            });
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(ParseBuildSAX.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
}
