package cz.cuni.mff.java.shell;

public interface Command {
    String getCommand();

    String getHelp();

    String execute(String... args);
}
