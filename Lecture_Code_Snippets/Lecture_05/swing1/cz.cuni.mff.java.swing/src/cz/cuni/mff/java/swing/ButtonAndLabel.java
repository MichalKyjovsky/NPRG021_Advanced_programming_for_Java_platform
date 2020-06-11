package cz.cuni.mff.java.swing;

import javax.swing.*;
import java.awt.*;

public class ButtonAndLabel {

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Button and Label");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container pane = frame.getContentPane();
        pane.setLayout(new GridLayout(0, 1));

        JButton button = new JButton("Click here");
        pane.add(button);

        JLabel label = new JLabel("Hello World");
        pane.add(label);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(ButtonAndLabel::createAndShowGUI);
    }

}
