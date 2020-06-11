package cz.cuni.mff.java.swing;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;

public class List implements ListSelectionListener {

    private JLabel label;
    private JList<String> list;

    private Component createComponents() {
        JPanel panel = new JPanel(new GridLayout(0, 1));

        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));

        String[] ls = {"aaaa", "bbbb", "cccc", "dddd", "eeee"};
        list = new JList<>(ls);
        list.addListSelectionListener(this);
        panel.add(new JScrollPane(list));

        label = new JLabel("Hello World");
        panel.add(label);

        return panel;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        label.setText(list.getSelectedValue());
    }

    private static void createAndShowGUI(String[] args) {
        JFrame frame = new JFrame("Combo box");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        List ap = new List();
        Component panel = ap.createComponents();

        frame.getContentPane().add(panel);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(final String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI(args));
    }

}
