package cz.cuni.mff.java.xml;

import javax.xml.parsers.*;

import org.w3c.dom.*;


public class DOMTest {

    public static void main(String[] argv) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse("file.xml");

        Element root = document.getDocumentElement();
        System.out.println(root.getNodeName());
        NodeList nl = root.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node n = nl.item(i);
            System.out.println("  " + n.getNodeName());
        }
    }
}
