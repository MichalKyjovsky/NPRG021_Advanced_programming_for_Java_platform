package cz.cuni.mff.java.rmi.hello;

import java.rmi.Remote;

public interface Hello extends Remote {
    public String sayHello() throws java.rmi.RemoteException;
}
