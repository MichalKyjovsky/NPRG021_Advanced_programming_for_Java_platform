package cz.cuni.mff.java.swing;

import javax.swing.*;
import java.awt.*;

public class IconWithToolTips {

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Icons");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container pane = frame.getContentPane();
        pane.setLayout(new GridLayout(0, 1));

        JButton button = new JButton("Click", new ImageIcon(Icons.class.getResource("/cz/cuni/mff/java/swing/ystar.png")));
        button.setToolTipText("Click here");
        pane.add(button);

        JLabel label = new JLabel("Hello", new ImageIcon(Icons.class.getResource("/cz/cuni/mff/java/swing/gstar.png")), SwingConstants.CENTER);
        label.setToolTipText("Label");
        pane.add(label);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(IconWithToolTips::createAndShowGUI);
    }

}
