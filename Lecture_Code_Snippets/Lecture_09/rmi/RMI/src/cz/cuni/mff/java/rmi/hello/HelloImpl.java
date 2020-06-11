package cz.cuni.mff.java.rmi.hello;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloImpl extends UnicastRemoteObject implements Hello {

    public HelloImpl() throws RemoteException {
    }

    @Override
    public String sayHello() throws RemoteException {
        System.out.println("sayHello called");
        return "Hello, world!";
    }

    public static void main(String[] args) {
        try {
            HelloImpl obj = new HelloImpl();
            System.out.println("Hello object created.");
            Naming.rebind("Hello", obj);
        } catch (RemoteException | MalformedURLException ex) {
            Logger.getLogger(HelloImpl.class.getName()).log(Level.SEVERE, "Exception occurred", ex);
        }
    }

}
