package cz.cuni.mff.java.swingthreads;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WorkerTest implements ActionListener {
    private JLabel label;
    private JButton button;

    private Component createComponents() {
        JPanel panel = new JPanel(new GridLayout(0, 1));

        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));

        button = new JButton("Compute");
        button.addActionListener(this);
        panel.add(button);

        label = new JLabel("No value computed yet");
        panel.add(label);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        label.setText("Computing...");
        button.setEnabled(false);
        SwingWorker<String, Object> worker = new SwingWorker<>() {
            String ret;

            @Override
            public String doInBackground() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                }
                return ret = "Finished :-)";
            }

            @Override
            public void done() {
                label.setText(ret);
                button.setEnabled(true);
            }
        };
        worker.execute();
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("WorkerTest");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        WorkerTest ap = new WorkerTest();
        Component panel = ap.createComponents();

        frame.getContentPane().add(panel);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(WorkerTest::createAndShowGUI);
    }
}
