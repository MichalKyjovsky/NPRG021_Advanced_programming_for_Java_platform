package cz.cuni.mff.java.swing;

import javax.swing.*;

public class TabbedPaneTest {

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Card Layout");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel card1 = new JPanel();
        card1.add(new JButton("Button 1"));
        card1.add(new JButton("Button 2"));
        card1.add(new JButton("Button 3"));

        JPanel card2 = new JPanel();
        card2.add(new JTextField("TextField", 20));

        JTabbedPane tb = new JTabbedPane();
        tb.setTabPlacement(JTabbedPane.BOTTOM);
        tb.add("Panel 1", card1);
        tb.add("Panel 2", card2);

        frame.getContentPane().add(tb);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(TabbedPaneTest::createAndShowGUI);
    }
}
