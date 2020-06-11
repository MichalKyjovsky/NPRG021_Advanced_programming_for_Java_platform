package cz.cuni.mff.java.xml;

import javax.xml.parsers.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

public class SAXTest extends DefaultHandler {

    public static void main(String[] argv) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse("file.xml", new SAXTest());
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        System.out.println("Element name: " + qName);
        System.out.println("  #Attributes: " + attributes.getLength());

    }

}
