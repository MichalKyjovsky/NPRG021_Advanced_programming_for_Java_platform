package cz.cuni.mff.java.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu {

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Menu");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container pane = frame.getContentPane();
        pane.setLayout(new GridLayout(0, 1));

        JButton button = new JButton("Click here");
        pane.add(button);

        JLabel label = new JLabel("Hello World");
        pane.add(label);

        frame.setJMenuBar(createMenu());

        frame.pack();
        frame.setVisible(true);
    }

    private static JMenuBar createMenu() {
        JMenuBar mb = new JMenuBar();
        JMenu menu = new JMenu("File");

        JMenuItem item = new JMenuItem("Quit");
        item.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
        menu.add(item);
        mb.add(menu);

        menu = new JMenu("Help");
        item = new JMenuItem("Content");
        menu.add(item);
        menu.add(new JSeparator());
        item = new JMenuItem("About");
        menu.add(item);
        mb.add(menu);

        return mb;
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(Menu::createAndShowGUI);
    }

}
