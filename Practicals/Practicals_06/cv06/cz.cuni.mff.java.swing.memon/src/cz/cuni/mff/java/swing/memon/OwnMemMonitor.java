package cz.cuni.mff.java.swing.memon;

import javax.swing.*;
import java.awt.*;

public class OwnMemMonitor {

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

        MemMonWidget mmw = new MemMonWidget();
        content.add(mmw);

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
        SwingUtilities.invokeLater(OwnMemMonitor::createAndShowGUI);
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

    private static class MemMonWidget extends JPanel {

        public MemMonWidget() {
            Timer tt = new Timer(1, e -> {
                repaint();
            });
            tt.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            MemInfo mi = new MemInfo();

            int w = getWidth();
            int h = getHeight();

            g.drawArc(1, 1, w-2, 2*h-2, 0, 180);
            g.setColor(Color.green);
            g.fillArc(1, 1, w-2, 2*h-2, (int)((((double)mi.free)/mi.total)*180), 180);
            g.setColor(Color.red);
            g.drawString(mi.label, 10, h / 2);
        }

    }
}
