package cz.cuni.mff.java.swing;

import javax.swing.*;
import java.awt.*;

public class ButtonAndLabel2 {

    private static Component createComponents() {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        JButton button = new JButton("Click here");
        panel.add(button);
        JLabel label = new JLabel("Hello World");
        panel.add(label);
        return panel;
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Button and Label 2");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Component panel = createComponents();

        frame.getContentPane().add(panel);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(ButtonAndLabel2::createAndShowGUI);
    }

}
