package cz.cuni.mff.java.rmiboards;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerMain {

    public static void main(String[] args) {
        try {
            ServerImpl server = new ServerImpl();
            Naming.rebind("Boards", server);
        } catch (RemoteException | MalformedURLException ex) {
            Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, "Exception occurred.", ex);
        }
    }
}
