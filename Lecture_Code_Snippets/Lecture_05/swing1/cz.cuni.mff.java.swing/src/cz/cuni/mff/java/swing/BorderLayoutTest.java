package cz.cuni.mff.java.swing;

import javax.swing.*;
import java.awt.*;

public class BorderLayoutTest {
    private static void createAndShowGUI(String[] args) {
        JFrame frame = new JFrame("Border Layout");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container pane = frame.getContentPane();

        pane.setLayout(new BorderLayout(5, 10));

        if (args.length != 0) {
            pane.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
        }

        JButton button = new JButton("PAGE_START");
        pane.add(button, BorderLayout.PAGE_START);

        button = new JButton("CENTER");
        button.setPreferredSize(new Dimension(200, 100));
        pane.add(button, BorderLayout.CENTER);

        button = new JButton("LINE_START");
        pane.add(button, BorderLayout.LINE_START);

        button = new JButton("PAGE_END");
        pane.add(button, BorderLayout.PAGE_END);

        button = new JButton("LINE_END");
        pane.add(button, BorderLayout.LINE_END);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(final String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI(args));
    }
}
