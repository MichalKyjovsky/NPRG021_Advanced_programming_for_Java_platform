module cz.cuni.mff.java.shell.commands.hello {
    requires cz.cuni.mff.java.shell.core;
    provides cz.cuni.mff.java.shell.Command with cz.cuni.mff.java.shell.commands.hello.HelloCommand;
}