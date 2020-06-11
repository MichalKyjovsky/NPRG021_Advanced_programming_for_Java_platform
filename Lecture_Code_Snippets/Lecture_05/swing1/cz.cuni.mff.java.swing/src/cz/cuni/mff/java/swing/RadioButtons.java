package cz.cuni.mff.java.swing;

import javax.swing.*;
import java.awt.*;

public class RadioButtons {

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("RadioButtons");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container pane = frame.getContentPane();
        pane.setLayout(new GridLayout(0, 1));

        JRadioButton[] buttons = new JRadioButton[4];

        for (int i = 0; i < 4; i++) {
            pane.add(buttons[i] = new JRadioButton("Button " + (i + 1)));
        }

        ButtonGroup bg = new ButtonGroup();

        for (int i = 0; i < 4; i++) {
            bg.add(buttons[i]);
        }

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(RadioButtons::createAndShowGUI);
    }

}
