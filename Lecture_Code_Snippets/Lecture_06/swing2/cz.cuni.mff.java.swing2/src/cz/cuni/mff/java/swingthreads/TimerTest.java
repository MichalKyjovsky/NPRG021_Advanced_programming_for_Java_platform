package cz.cuni.mff.java.swingthreads;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ExecutionException;

public class TimerTest implements ActionListener {
    private JPanel panel;
    private JLabel label;
    private JButton button;
    private JProgressBar bar;
    private SwingWorker<String, Object> sw;
    private Timer timer;

    private Component createComponents() {
        panel = new JPanel(new GridLayout(0, 1));

        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));

        button = new JButton("Compute");
        button.addActionListener(this);
        panel.add(button);

        bar = new JProgressBar(0, 100);
        panel.add(bar);

        label = new JLabel("No value computed yet");
        panel.add(label);

        timer = new Timer(100, (ActionEvent evt) -> {
            bar.setValue(sw.getProgress());
            if (sw.isDone()) {
                timer.stop();
                button.setEnabled(true);
                panel.setCursor(null);
                String ret = null;
                try {
                    ret = sw.get();
                } catch (InterruptedException | ExecutionException ex) {
                }
                label.setText(ret);
                bar.setValue(bar.getMinimum());
            }
        });

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        label.setText("Working...");
        button.setEnabled(false);
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        sw = new SwingWorker<>() {
            @Override
            public String doInBackground() {
                for (int i = 0; i < 100; i++) {
                    setProgress(i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                    }
                }
                return "Finished :-)";
            }
        };
        sw.execute();
        timer.start();
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("TimerTest");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TimerTest ap = new TimerTest();
        Component panel = ap.createComponents();

        frame.getContentPane().add(panel);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(TimerTest::createAndShowGUI);
    }
}


