package cz.cuni.mff.java.rmiboards;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerImpl extends UnicastRemoteObject implements Server {

    private final List<String> messages = Collections.synchronizedList(new ArrayList<>());
    private final List<Client> clients = Collections.synchronizedList(new ArrayList<>());

    public ServerImpl() throws RemoteException {
    }

    @Override
    public void post(String msg) throws RemoteException {
        messages.add(msg);
        synchronized (clients) {
            for (Client cl : clients) {
                try {
                    cl.notifyMessage();
                } catch (RemoteException ex) {
                    Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, "Exception occurred when notifying a client.", ex);
                }
            }
        }
    }

    @Override
    public String[] list() throws RemoteException {
        return messages.toArray(new String[0]);
    }

    @Override
    public void register(Client cl) throws RemoteException {
        clients.add(cl);
    }

    @Override
    public void unregister(Client cl) throws RemoteException {
        clients.remove(cl);
    }
}
