package cz.cuni.mff.java.swing.editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class HelloWorld {

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Hello world");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label = new JLabel("Hello world");
        Container content = frame.getContentPane();
        content.add(label);
        JButton exitButton = new JButton(new ExitAction());
        //exitButton.addActionListener(e -> System.exit(0));
        frame.add(exitButton, BorderLayout.SOUTH);

        frame.setJMenuBar(createMenu());

        frame.pack();
        frame.setSize(300, 200);
        frame.setVisible(true);
    }

    private static JMenuBar createMenu() {
        JMenuBar mb = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitMenuItem = new JMenuItem(new ExitAction());
        //exitMenuItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitMenuItem);
        mb.add(fileMenu);
        return mb;
    }

    private static class ExitAction extends AbstractAction {

        public ExitAction() {
            super("Exit", new ImageIcon(HelloWorld.class.getResource("/cz/cuni/mff/java/swing/editor/exit.png")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HelloWorld::createAndShowGUI);
    }
}
