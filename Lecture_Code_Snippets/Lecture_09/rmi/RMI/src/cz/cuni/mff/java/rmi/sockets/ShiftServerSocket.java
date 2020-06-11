package cz.cuni.mff.java.rmi.sockets;

import java.io.*;
import java.net.*;

class ShiftServerSocket extends ServerSocket {

    private final int number;

    public ShiftServerSocket(int port, int number) throws IOException {
        super(port);
        this.number = number;
    }

    @Override
    public Socket accept() throws IOException {
        Socket s = new ShiftSocket(number);
        implAccept(s);
        return s;
    }
}
