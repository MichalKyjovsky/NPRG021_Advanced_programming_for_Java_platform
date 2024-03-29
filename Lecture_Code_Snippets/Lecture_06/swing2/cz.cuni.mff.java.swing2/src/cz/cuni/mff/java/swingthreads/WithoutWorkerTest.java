package cz.cuni.mff.java.swingthreads;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WithoutWorkerTest implements ActionListener {
    private JLabel label;

    private Component createComponents() {
        JPanel panel = new JPanel(new GridLayout(0, 1));

        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));

        JButton button = new JButton("Compute");
        button.addActionListener(this);
        panel.add(button);

        label = new JLabel("No value computer yet");
        panel.add(label);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        label.setText("Computing...");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
        }
        label.setText("Finished :-)");
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("WorkerTest");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        WithoutWorkerTest ap = new WithoutWorkerTest();
        Component panel = ap.createComponents();

        frame.getContentPane().add(panel);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(WithoutWorkerTest::createAndShowGUI);
    }


}
