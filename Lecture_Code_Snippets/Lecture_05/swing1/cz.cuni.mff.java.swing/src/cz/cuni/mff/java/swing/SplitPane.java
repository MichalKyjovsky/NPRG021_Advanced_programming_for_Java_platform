package cz.cuni.mff.java.swing;

import javax.swing.*;
import java.awt.*;

public class SplitPane {

    public static void createAndShowGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("SplitPane example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container content = frame.getContentPane();

        JSplitPane sp = new JSplitPane();


        sp.setTopComponent(new JScrollPane(new JTextArea()));
        sp.setBottomComponent(new JScrollPane(new JTextArea()));

        content.add(sp);

        frame.setSize(400, 300);
        frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public static void main(String[] argv) {
        javax.swing.SwingUtilities.invokeLater(SplitPane::createAndShowGUI);
    }
}
