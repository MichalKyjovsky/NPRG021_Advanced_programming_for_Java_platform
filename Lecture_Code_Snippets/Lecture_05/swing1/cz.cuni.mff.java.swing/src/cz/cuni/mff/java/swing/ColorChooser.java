package cz.cuni.mff.java.swing;

import java.awt.Color;
import java.awt.Container;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;

public class ColorChooser {

    public static void createAndShowGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        final JFrame frame = new JFrame("ColorChooser example");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = frame.getContentPane();

        final JButton b = new JButton("Select color");
        b.addActionListener(ae -> {
            Color c = JColorChooser.showDialog(frame, "Select color", b.getForeground());
            b.setForeground(c);
        });
        content.add(b);

        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    public static void main(String[] argv) {
        javax.swing.SwingUtilities.invokeLater(ColorChooser::createAndShowGUI);
    }
}
