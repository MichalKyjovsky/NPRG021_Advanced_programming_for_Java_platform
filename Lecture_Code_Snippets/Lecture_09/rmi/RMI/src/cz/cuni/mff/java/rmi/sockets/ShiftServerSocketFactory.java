package cz.cuni.mff.java.rmi.sockets;

import java.io.*;
import java.net.*;
import java.rmi.server.*;

public class ShiftServerSocketFactory implements RMIServerSocketFactory {

    private final int number;

    public ShiftServerSocketFactory(int number) {
        this.number = number;
    }

    @Override
    public ServerSocket createServerSocket(int port) throws IOException {
        return new ShiftServerSocket(port, number);
    }

    @Override
    public int hashCode() {
        return number;
    }

    @Override
    public boolean equals(Object obj) {
        return (getClass() == obj.getClass() && number == ((ShiftServerSocketFactory) obj).number);
    }

}

