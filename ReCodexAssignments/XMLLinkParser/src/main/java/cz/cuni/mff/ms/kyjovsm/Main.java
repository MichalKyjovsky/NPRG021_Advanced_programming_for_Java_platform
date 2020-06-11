package cz.cuni.mff.ms.kyjovsm;

import org.jdom2.JDOMException;

import javax.xml.stream.XMLStreamException;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        SectionParser sp = new SectionParser();
        try {
            sp.processSectionsLink(sp.parseDocumentToSection(System.in));
        } catch (JDOMException | XMLStreamException exception) {
            exception.printStackTrace();
        }
    }
}
