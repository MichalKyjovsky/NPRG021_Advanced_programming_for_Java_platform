package cz.cuni.mff.java.rmiboards;

import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements Client {
    private Server server;
    private JTextArea area;

    public ClientImpl(Server server, JTextArea are) throws RemoteException {
        this.server = server;
        this.area = are;
    }

    @Override
    public void notifyMessage() throws RemoteException {
        String[] msgs = server.list();
        area.setText(String.join("\n", msgs));
    }
}
