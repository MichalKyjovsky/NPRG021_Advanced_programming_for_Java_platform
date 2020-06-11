package cz.cuni.mff.java.plugins.textprocessor;

public class DotToExclamationProcessor implements TextProcessor {
    @Override
    public String process(String text) {
        return text.replaceAll("\\.", "!");
    }
}
