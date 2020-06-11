package cz.cuni.mff.java.mbeans.rmi;

import java.io.IOException;
import java.lang.management.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.*;
import javax.management.remote.*;

public class MyClassMain {
    public static void main(String[] args) {

        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

            ObjectName name = new ObjectName("cz.cuni.mff.java.mbeans.rmi:type=MyClass");

            MyClass mbean = new MyClass();
            mbs.registerMBean(mbean, name);

            JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9999/server");
            JMXConnectorServer cs = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);

            cs.start();

            System.out.println("Waiting forever...");
            Thread.sleep(Long.MAX_VALUE);
        } catch (MalformedObjectNameException | InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException | InterruptedException | IOException ex) {
            Logger.getLogger(MyClassMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
