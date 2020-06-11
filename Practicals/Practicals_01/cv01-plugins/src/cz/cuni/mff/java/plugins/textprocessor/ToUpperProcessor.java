package cz.cuni.mff.java.plugins.textprocessor;

public class ToUpperProcessor implements TextProcessor {
    @Override
    public String process(String text) {
        return text.toUpperCase();
    }
}
