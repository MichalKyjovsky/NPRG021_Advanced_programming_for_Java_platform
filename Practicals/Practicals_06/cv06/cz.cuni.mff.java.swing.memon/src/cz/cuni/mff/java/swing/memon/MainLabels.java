package cz.cuni.mff.java.swing.memon;

import javax.swing.*;
import java.awt.*;

public class MainLabels {

    static Runtime rt = Runtime.getRuntime();

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Memory monitor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = frame.getContentPane();
        content.setLayout(new GridLayout(0, 1));

        JLabel memLabel = new JLabel(new MemInfo().label);
        content.add(memLabel);

        JProgressBar pb = new JProgressBar(0, 100);
        pb.setStringPainted(true);
        content.add(pb);

        JButton gcButton = new JButton("Run GC");
        gcButton.addActionListener(e -> System.gc());
        content.add(gcButton);

        Timer tt = new Timer(1, e -> {
            MemInfo mi = new MemInfo();
            memLabel.setText(mi.label);
            pb.setString(mi.label);
            pb.setValue((int) ((mi.total - mi.free) * 100. / mi.total) );
        });
        tt.start();

        frame.pack();
        frame.setSize(300, 200);
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainLabels::createAndShowGUI);
    }

    private static class MemInfo {
        long free;
        long total;
        String label;

        public MemInfo() {
            this.free = rt.freeMemory();
            this.total = rt.totalMemory();
            this.label = free + " / " + total + " B";
        }

        @Override
        public String toString() {
            return label;
        }
    }

}
