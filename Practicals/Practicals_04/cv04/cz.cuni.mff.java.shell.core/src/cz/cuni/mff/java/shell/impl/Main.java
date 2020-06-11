package cz.cuni.mff.java.shell.impl;

import cz.cuni.mff.java.shell.Command;

import java.io.Console;
import java.util.HashMap;
import java.util.ServiceLoader;

public class Main {
    static class HelpCommand implements Command {
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

        ServiceLoader<Command> serviceLoader = ServiceLoader.load(Command.class);
        for (Command command : serviceLoader) {
            commands.put(command.getCommand(), command);
        }

        HelpCommand helpCommand = new HelpCommand(commands);
        commands.put(helpCommand.getCommand(), helpCommand);

        System.out.println("Loaded commands:");
        commands.keySet().forEach(System.out::println);

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
