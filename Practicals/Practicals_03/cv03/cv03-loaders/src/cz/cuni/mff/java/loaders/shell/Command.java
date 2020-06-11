package cz.cuni.mff.java.loaders.shell;

public interface Command {
    String getCommand();

    String getHelp();

    String execute(String... args);
}
