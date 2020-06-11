package cz.cuni.mff.java.plugins.shell;

public interface Command {
    String getCommand();

    String getHelp();

    String execute(String... args);
}
