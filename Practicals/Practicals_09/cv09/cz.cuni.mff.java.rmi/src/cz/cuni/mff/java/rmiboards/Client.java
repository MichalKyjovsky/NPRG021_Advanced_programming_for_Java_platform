package cz.cuni.mff.java.rmiboards;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
    void notifyMessage() throws RemoteException;
}
