package cz.cuni.mff.java.plugins.shell;

public class PrintICommand implements Command {
    @Override
    public String getCommand() {
        return "I";
    }

    @Override
    public String getHelp() {
        return "prints out I";
    }

    @Override
    public String execute(String... args) {
        return "IIIIII\n";
    }
}
