package cz.cuni.mff.java.mbeans.rmi;

import java.io.IOException;
import java.net.MalformedURLException;
import javax.management.*;
import javax.management.remote.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    public static void main(String[] args) {
        try {
            JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9999/server");
            try (JMXConnector jmxc = JMXConnectorFactory.connect(url, null)) {
                MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

                ObjectName mname = new ObjectName("cz.cuni.mff.java.mbeans.rmi:type=MyClass");
                ObjectInstance inst = null;

                Set<ObjectInstance> beans = mbsc.queryMBeans(null, null);
                for (ObjectInstance oi : beans) {
                    ObjectName name = oi.getObjectName();
                    System.out.println(name);

                    if (name.equals(mname)) {
                        inst = oi;
                        System.out.println("OK");
                    }
                }

                System.out.println(mbsc.getAttribute(mname, "State"));
                mbsc.setAttribute(mname, new Attribute("State", 100));

                MyClassMBean proxy = JMX.newMBeanProxy(mbsc, mname, MyClassMBean.class, true);

                System.out.println(proxy.getState());

            } catch (IOException | MalformedObjectNameException | MBeanException | AttributeNotFoundException | InstanceNotFoundException | ReflectionException | InvalidAttributeValueException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
