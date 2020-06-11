package cz.cuni.mff.ms.kyjovsm;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.StAXStreamBuilder;

import javax.xml.stream.XMLStreamException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SectionParser {
    private static final String SECTION_ELEMENT = "section";
    private static final String TITLE_ELEMENT = "title";
    private static final String LINK_ELEMENT = "link";
    private static final String ID_ATTRIBUTE = "id";
    private static final String LINKEND_ATTRIBUTE = "linkend";
    private List<Element> linkedSections;
    private List<Element> allSections;
    private boolean linkFound;

    public SectionParser() {
        linkedSections = new ArrayList<>();
        allSections = new ArrayList<>();
        linkFound = false;
    }


    public List<Element> parseDocumentToSection(InputStream input) throws JDOMException, XMLStreamException {
            javax.xml.stream.XMLInputFactory factory = javax.xml.stream.XMLInputFactory.newFactory();
            javax.xml.stream.XMLStreamReader reader = factory.createXMLStreamReader(input);
            StAXStreamBuilder builder = new StAXStreamBuilder();
            Document doc = builder.build(reader);

            Element root = doc.getRootElement();
            if (root.getChildren().size() > 0) {
                findAllSections(root);
                findLinkedSections(allSections);
            }
            return linkedSections;
    }

    public void processSectionsLink(List<Element> sectionsToProcess) {
        for (Element section : sectionsToProcess) {
            System.out.println(section.getChild(TITLE_ELEMENT).getText() + ":");
            setAndPrintLink(section);
        }
    }

    private void setAndPrintLink(Element parent) {
        for (Element child : parent.getChildren()) {
            if (child.getName().equals(LINK_ELEMENT)) {
                System.out.print("\t");
                mineLinkText(child);
                printLink(child.getAttributeValue(LINKEND_ATTRIBUTE));
                return;
            } else if (child.getChildren().size() > 0 && !child.getName().equals(SECTION_ELEMENT)) {
                setAndPrintLink(child);
            }
        }
    }

    private void printLink(String link) {
        for (Element section : allSections) {
            searchForId(section, link);
            if (linkFound) {
                System.out.print(" (");
                mineTitleText(section.getChild(TITLE_ELEMENT));
                System.out.println(")");
                linkFound = false;
            }
        }
    }

    private void mineTitleText(Element title) {
        System.out.print(title.getText());
        if (title.getChildren().size() > 0) {
            for (Element child : title.getChildren()) {
                mineLinkText(child);
            }
        }
    }

    private void searchForId(Element parent, String link) {
        if (linkFound) {
            return;
        }
        if (parent.getAttribute(ID_ATTRIBUTE) != null) {
            if (parent.getAttributeValue(ID_ATTRIBUTE).equals(link)) {
                linkFound = true;
                return;
            }
        }
         if (parent.getChildren().size() > 0 ) {
                for (Element child : parent.getChildren()) {
                    if (child.getName().equals(SECTION_ELEMENT)) {
                        continue;
                    }
                    searchForId(child, link);
                }
            }
        }

    private void mineLinkText(Element link) {
        System.out.print(link.getText());
        if (link.getChildren().size() > 0) {
            for (Element child : link.getChildren()) {
                mineLinkText(child);
            }
        }
    }

    private void findAllSections(Element parent) {
        if (parent.getName().equals(SECTION_ELEMENT)) {
            allSections.add(parent);
        }

        List<Element> children = parent.getChildren();

        if (children.size() > 0) {
            for (Element child : children) {
                findAllSections(child);
            }
        }
    }

    private void findLinkedSections(List<Element> sections) {
        for (Element section : sections) {
            hasLink(section);
            if (linkFound) {
                linkedSections.add(section);
                linkFound = false;
            }
        }
    }

    private void hasLink(Element parent) {
        if (linkFound) {
            return;
        }
        if (parent.getName().equals(LINK_ELEMENT)) {
            linkFound = true;
        } else if (parent.getChildren().size() > 0) {
            for (Element element : parent.getChildren()) {
                hasLink(element);
            }
        }
    }
}
