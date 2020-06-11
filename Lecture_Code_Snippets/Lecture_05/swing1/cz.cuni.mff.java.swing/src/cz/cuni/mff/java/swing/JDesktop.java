package cz.cuni.mff.java.swing;

import java.awt.Container;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

public class JDesktop {
    public static void createAndShowGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        final JFrame frame = new JFrame("DesktopPane example");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = frame.getContentPane();

        JDesktopPane dp = new JDesktopPane();
        content.add(dp);

        JInternalFrame jf = new JInternalFrame("Window", true, true, true, true);
        jf.setSize(100, 100);
        dp.add(jf);
        jf.setVisible(true);


        frame.pack();
        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    public static void main(String[] argv) {
        javax.swing.SwingUtilities.invokeLater(JDesktop::createAndShowGUI);
    }
}
