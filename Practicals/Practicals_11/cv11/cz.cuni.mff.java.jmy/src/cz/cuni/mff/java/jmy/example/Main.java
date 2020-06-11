package cz.cuni.mff.java.jmy.example;

import cz.cuni.mff.java.jmy.JMYException;
import cz.cuni.mff.java.jmy.JMYServer;

public class Main {

    public static void main(String[] args) throws InterruptedException, JMYException {
        JMYServer server = new JMYServer(8080);
        server.register("Petr", new MBeanImpl("Petr", 42));
        server.register("Karel", new MBeanImpl("Karel", 100));
        // server.register("obj", new Object());
        Thread.sleep(Integer.MAX_VALUE);
    }
    
}
