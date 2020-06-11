package cz.cuni.mff.java.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CardLayoutTest implements ItemListener {

    private JPanel cards;
    private final static String PANEL1 = "Panel 1";
    private final static String PANEL2 = "Panel 2";

    private void addComponentToPane(Container pane) {
        JPanel comboBoxPane = new JPanel();
        String[] comboBoxItems = {PANEL1, PANEL2};
        JComboBox<String> cb = new JComboBox<>(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);
        comboBoxPane.add(cb);

        JPanel card1 = new JPanel();
        card1.add(new JButton("Button 1"));
        card1.add(new JButton("Button 2"));
        card1.add(new JButton("Button 3"));

        JPanel card2 = new JPanel();
        card2.add(new JTextField("TextField", 20));

        cards = new JPanel(new CardLayout());
        cards.add(card1, PANEL1);
        cards.add(card2, PANEL2);

        pane.add(comboBoxPane, BorderLayout.PAGE_START);
        pane.add(cards, BorderLayout.CENTER);
    }

    @Override
    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, (String) evt.getItem());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Card Layout");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CardLayoutTest app = new CardLayoutTest();
        app.addComponentToPane(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(CardLayoutTest::createAndShowGUI);
    }

}
