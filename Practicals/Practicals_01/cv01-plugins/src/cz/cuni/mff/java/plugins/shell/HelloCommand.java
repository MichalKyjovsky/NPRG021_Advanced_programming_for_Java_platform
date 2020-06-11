package cz.cuni.mff.java.plugins.shell;

public class HelloCommand implements Command {
    @Override
    public String getCommand() {
        return "hello";
    }

    @Override
    public String getHelp() {
        return "prints out Hello World";
    }

    @Override
    public String execute(String... args) {
        return "Hello World\n";
    }
}
