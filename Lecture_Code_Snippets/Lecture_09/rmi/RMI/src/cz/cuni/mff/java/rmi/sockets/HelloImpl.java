package cz.cuni.mff.java.rmi.sockets;

import cz.cuni.mff.java.rmi.hello.Hello;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloImpl extends UnicastRemoteObject implements Hello {

    public HelloImpl() throws RemoteException {
        super(0, new ShiftClientSocketFactory(20), new ShiftServerSocketFactory(20));
    }

    @Override
    public String sayHello() throws RemoteException {
        return "Hello, world!";
    }

    public static void main(String args[]) {
        try {
            HelloImpl obj = new HelloImpl();
            System.out.println("Hello object created.");
            Naming.rebind("Hello", obj);
        } catch (RemoteException | MalformedURLException ex) {
            Logger.getLogger(HelloImpl.class.getName()).log(Level.SEVERE, "Exception occurred", ex);
        }
    }
}
