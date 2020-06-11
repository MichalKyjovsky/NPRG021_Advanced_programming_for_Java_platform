package cz.cuni.mff.java.rmi.sockets;

import java.io.*;
import java.net.*;

class ShiftSocket extends Socket {

    private final int number;

    private InputStream in = null;
    private OutputStream out = null;

    public ShiftSocket(int number) throws IOException {
        super();
        this.number = number;
    }

    public ShiftSocket(String host, int port, int number) throws IOException {
        super(host, port);
        this.number = number;
    }

    @Override
    public synchronized InputStream getInputStream() throws IOException {
        if (in == null) {
            in = new ShiftInputStream(super.getInputStream(), number);
        }
        return in;
    }

    @Override
    public synchronized OutputStream getOutputStream() throws IOException {
        if (out == null) {
            out = new ShiftOutputStream(super.getOutputStream(), number);
        }
        return out;
    }
}
