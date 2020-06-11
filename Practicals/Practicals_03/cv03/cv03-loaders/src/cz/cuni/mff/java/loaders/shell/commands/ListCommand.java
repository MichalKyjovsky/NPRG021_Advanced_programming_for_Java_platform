package cz.cuni.mff.java.loaders.shell.commands;

import cz.cuni.mff.java.loaders.shell.Command;

public class ListCommand implements Command {
    @Override
    public String getCommand() {
        return "list";
    }

    @Override
    public String getHelp() {
        return "list directory content\n  list [dirname]";
    }

    @Override
    public String execute(String... args) {
        // TODO provide actual implementation
        return "";
    }
}
