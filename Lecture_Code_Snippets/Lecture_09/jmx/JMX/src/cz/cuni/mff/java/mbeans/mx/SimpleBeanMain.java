package cz.cuni.mff.java.mbeans.mx;

import java.lang.management.*;
import javax.management.*;

public class SimpleBeanMain {
    public static void main(String[] args) throws Exception {

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        ObjectName name = new ObjectName("cz.cuni.mff.java.mbeans.mx:type=SimpleBean");

        SimpleBean mbean = new SimpleBeanImpl();
        mbs.registerMBean(mbean, name);

        System.out.println("Waiting forever...");
        Thread.sleep(Long.MAX_VALUE);
    }
}

