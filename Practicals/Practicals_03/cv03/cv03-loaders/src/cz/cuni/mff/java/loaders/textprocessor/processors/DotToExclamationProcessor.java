package cz.cuni.mff.java.loaders.textprocessor.processors;

import cz.cuni.mff.java.loaders.textprocessor.TextProcessor;

public class DotToExclamationProcessor implements TextProcessor {
    @Override
    public String process(String text) {
        return text.replaceAll("\\.", "!");
    }
}
