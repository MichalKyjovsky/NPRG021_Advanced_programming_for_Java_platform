package cz.cuni.mff.java.swing;

import javax.swing.*;
import java.awt.*;

public class ToolBar {

    public static void createAndShowGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Tool bar example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container content = frame.getContentPane();

        JToolBar tb = new JToolBar();
        JButton b1 = new JButton(new ImageIcon(ToolBar.class.getResource("/cz/cuni/mff/java/swing/ystar.png")));
        JButton b2 = new JButton(new ImageIcon(ToolBar.class.getResource("/cz/cuni/mff/java/swing/gstar.png")));
        tb.add(b1);
        tb.add(b2);

        content.add(tb, BorderLayout.NORTH);
        content.add(new JScrollPane(new JTextArea()));

        frame.setSize(400, 300);
        frame.setVisible(true);

    }


    public static void main(String[] argv) {
        javax.swing.SwingUtilities.invokeLater(ToolBar::createAndShowGUI);
    }
}
