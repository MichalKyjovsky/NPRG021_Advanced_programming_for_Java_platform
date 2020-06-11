package cz.cuni.mff.java.plugins.shell;

import cz.cuni.mff.java.plugins.Loader;


import java.io.Console;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public class Main {

    private static class HelpCommand implements Command {
        private HashMap<String, Command> commands;

        public HelpCommand(HashMap<String, Command> commands) {
            this.commands = commands;
        }

        @Override
        public String getCommand() {
            return "help";
        }

        @Override
        public String getHelp() {
            return "prints out list of all available commands";
        }

        @Override
        public String execute(String... args) {
            StringBuilder sb = new StringBuilder();
            sb.append("List of commands\n");
            sb.append("================\n\n");
            commands.keySet().stream().sorted().forEach(c -> {
                Command cmd = commands.get(c);
                sb.append(c).append("\n       ");
                sb.append(cmd.getHelp()).append("\n\n");
            });
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        HashMap<String, Command> commands = new HashMap<>();
        if (args.length > 0) {
            try {
                List<String> lines = Files.readAllLines(Path.of(args[0]));
                List<Command> lst = Loader.loadPlugins(Command.class, lines.toArray(new String[0]));
                lst.forEach(cmd -> commands.put(cmd.getCommand(), cmd));
            } catch (IOException e) {
                System.out.println("Cannot read file with plugin names. Exiting.");
                System.exit(1);
            }
        } else {
            System.out.println("WARNING: no plugins");
        }
        HelpCommand helpCommand = new HelpCommand(commands);
        commands.put(helpCommand.getCommand(), helpCommand);

        Console console = System.console();
        if (console == null) {
            System.out.println("No console available");
            System.exit(1);
        }
        String line;
        while ((line = console.readLine()) != null) {
            String cmdStr = line.trim();
            Command cmd = commands.get(cmdStr);
            if (cmd == null) {
                console.printf("No such command\n");
            } else {
                console.printf(cmd.execute());
                console.printf("\n");
            }
        }
    }
}
