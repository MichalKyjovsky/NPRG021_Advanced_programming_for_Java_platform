// -Djava.security.manager= -Djava.security.policy=./all.policy -Djava.rmi.server.useCodebaseOnly=false
package cz.cuni.mff.java.rmi.browser;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BrowseRegistry {

    public static void main(String[] argv) {
        try {
            String host = argv.length == 0 ? "localhost" : argv[0];
            String port = argv.length <= 1 ? "1099" : argv[1];
            Registry reg = LocateRegistry.getRegistry(host, Integer.parseInt(port));
            String[] names = reg.list();
            if (names.length == 0) {
                System.out.println("No registered objects");
            } else {
                System.out.println("Registered objects");
                System.out.println("==================");
                for (String name : names) {
                    Object obj = reg.lookup(name);
                    System.out.println(name);
                    for (Class<?> iface : obj.getClass().getInterfaces()) {
                        System.out.println("  " + iface.getName());
                    }
                }
            }
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(BrowseRegistry.class.getName()).log(Level.SEVERE, "Exception occurred", ex);
        }
    }
}
