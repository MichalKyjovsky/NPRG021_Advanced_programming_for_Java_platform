package cz.cuni.mff.java.rmiboards;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {

    void post(String msg) throws RemoteException;

    String[] list() throws RemoteException;

    void register(Client cl) throws RemoteException;

    void unregister(Client cl) throws RemoteException;
}
