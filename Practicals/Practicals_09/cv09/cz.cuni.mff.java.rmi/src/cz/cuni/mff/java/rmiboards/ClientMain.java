package cz.cuni.mff.java.rmiboards;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientMain {

    private static JFrame frame;
    private static Server server;
    private static JTextArea area;
    private static Client client;

    public static void main(String[] args) {
        try {
            String registryUrl = "Boards";
            if (args.length > 0) {
                registryUrl = args[0];
            }
            server = (Server) Naming.lookup(registryUrl);
            SwingUtilities.invokeLater(ClientMain::createAndShowGUI);
        } catch (RemoteException | NotBoundException | MalformedURLException ex) {
            Logger.getLogger(ClientMain.class.getName()).log(Level.SEVERE, "Exception occurred.", ex);
        }
    }

    private static void createAndShowGUI() {
        frame = new JFrame("Boards");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Container content = frame.getContentPane();

        area = new JTextArea();
        area.setEditable(false);
        content.add(new JScrollPane(area));

        JToolBar tb = new JToolBar();

        JButton b = new JButton("Post");
        b.addActionListener(e -> {
            String msg = JOptionPane.showInputDialog(frame, "Message", "Message", JOptionPane.PLAIN_MESSAGE);
            if (msg != null) {
                try {
                    server.post(msg);
                } catch (RemoteException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "RMIException", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        tb.add(b);

        b = new JButton("List");
        b.addActionListener(e -> {
            try {
                String[] msgs = server.list();
                area.setText(String.join("\n", msgs));
            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "RMIException", JOptionPane.ERROR_MESSAGE);
            }
        });
        tb.add(b);

        JButton regb = new JButton("Register");
        JButton unregb = new JButton("Unregister");
        unregb.setEnabled(false);
        regb.addActionListener(e -> {
            try {
                if (client == null) {
                    client = new ClientImpl(server, area);
                    server.register(client);
                    frame.setTitle("Boards (registered)");
                    regb.setEnabled(false);
                    unregb.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(frame, "Client already registered", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "RMI exception", JOptionPane.ERROR_MESSAGE);
            }
        });

        unregb.addActionListener(e -> {
            try {
                if (client != null) {
                    server.unregister(client);
                    frame.setTitle("Boards");
                    unregb.setEnabled(false);
                    regb.setEnabled(true);
                    client = null;
                } else {
                    JOptionPane.showMessageDialog(frame, "Client not registered", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "RMI exception", JOptionPane.ERROR_MESSAGE);
            }
        });

        tb.add(regb);
        tb.add(unregb);


        content.add(tb, BorderLayout.NORTH);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (client != null) {
                    try {
                        server.unregister(client);
                    } catch (RemoteException ex) {
                        Logger.getLogger(ClientMain.class.getName()).log(Level.SEVERE, "Client has not been unregistered.", ex);
                    }
                }
                System.exit(0);
            }
        });

        frame.pack();
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
