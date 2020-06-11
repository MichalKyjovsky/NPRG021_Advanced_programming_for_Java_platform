package cz.cuni.mff.java.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TempConvert implements ActionListener {

    private JPanel panel;
    private JTextField input;
    private JLabel celLabel, farLabel;
    private JButton convertButton;

    public TempConvert() {
        JFrame frame = new JFrame("Prevod stupnu - Fahrenheit -> Celsius");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(120, 40));

        panel = new JPanel(new GridLayout(2, 2));

        createComponents();

        frame.getContentPane().add(panel);

        frame.pack();
        frame.setVisible(true);
    }

    private void createComponents() {
        input = new JTextField();
        farLabel = new JLabel("Fahrenheit");
        convertButton = new JButton("Preved");
        celLabel = new JLabel("Celsius");

        convertButton.addActionListener(this);
        input.addActionListener(this);

        panel.add(input);
        panel.add(farLabel);
        panel.add(convertButton);
        panel.add(celLabel);

        celLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        farLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int temp = (int) ((Double.parseDouble(input.getText()) - 32) * 5 / 9);
        celLabel.setText(temp + " Celsius");
    }

    private static void createAndShowGUI(String[] args) {
        TempConvert app = new TempConvert();
    }

    public static void main(final String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI(args));
    }
}
