package cz.cuni.mff.java.swing;

import javax.swing.*;
import java.awt.*;

public class FlowLayoutTest {

    private static void createAndShowGUI(String[] args) {
        JFrame frame = new JFrame("Flow Layout");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container pane = frame.getContentPane();
        pane.setLayout(new FlowLayout(FlowLayout.LEADING));

        if (args.length != 0) {
            pane.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
        }

        pane.add(new JButton("Button 1"));
        pane.add(new JButton("Button 2"));
        pane.add(new JButton("Button 3"));
        pane.add(new JButton("Long-Named Button 4"));
        pane.add(new JButton("5"));

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(final String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI(args));
    }
}
