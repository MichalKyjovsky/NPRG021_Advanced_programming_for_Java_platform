package cz.cuni.mff.java.loaders.shell;

import java.io.Console;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ServiceLoader;

public class MainWithRemotePlugins {
    public static void main(String[] args) {
        HashMap<String, Command> commands = new HashMap<>();

        if (args.length > 0) {
            try {
                List<String> lines = Files.readAllLines(Path.of(args[0]));

                List<URL> urls = new ArrayList<>();
                for (String line: lines) {
                    try {
                        URL url = new URL(line);
                        urls.add(url);
                    } catch (MalformedURLException ex) {
                        System.out.println("WARNING: bad url" + line);
                    }
                }

                URLClassLoader urlClassLoader = new URLClassLoader(urls.toArray(new URL[0]));

                ServiceLoader<Command> serviceLoader = ServiceLoader.load(Command.class, urlClassLoader);
                for (Command command : serviceLoader) {
                    commands.put(command.getCommand(), command);
                }

            } catch (IOException e) {
                System.out.println("Cannot read file with plugin names. Exiting.");
                System.exit(1);
            }
        } else {
            System.out.println("WARNING: no plugins");
        }

        Main.HelpCommand helpCommand = new Main.HelpCommand(commands);
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
