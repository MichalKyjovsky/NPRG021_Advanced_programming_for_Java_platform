package cz.cuni.mff.java.rmi.sockets;

import java.io.*;
import java.net.*;
import java.rmi.server.*;

public class ShiftClientSocketFactory implements RMIClientSocketFactory, Serializable {

    private final int number;

    public ShiftClientSocketFactory(int number) {
        this.number = number;
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException {
        return new ShiftSocket(host, port, number);
    }

    @Override
    public int hashCode() {
        return number;
    }

    @Override
    public boolean equals(Object obj) {
        return (getClass() == obj.getClass() && number == ((ShiftClientSocketFactory) obj).number);
    }
}

