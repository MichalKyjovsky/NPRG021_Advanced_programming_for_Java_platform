package cz.cuni.mff.java.mbeans.not;

import java.lang.management.*;
import javax.management.*;

public class MyClassMain {
    public static void main(String[] args) throws Exception {

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        ObjectName name = new ObjectName("cz.cuni.mff.java.mbeans.not:type=MyClass");

        MyClass mbean = new MyClass();
        mbs.registerMBean(mbean, name);

        System.out.println("Waiting forever...");
        Thread.sleep(Long.MAX_VALUE);
    }
}
