package cz.cuni.mff.java.sysint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TrayIconTest {
    private static JFrame frame;
    private static void createAndShowGUI() {


        //JFrame.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        JLabel label = new JLabel("Hello World");
        frame.getContentPane().add(label);
        frame.pack();
        frame.setSize(300, 200);

        if (SystemTray.isSupported()) {
            SystemTray st = SystemTray.getSystemTray();

            JPopupMenu pm = new JPopupMenu();
            JMenuItem mi = new JMenuItem("Exit");
            mi.addActionListener(e -> System.exit(0));
            pm.add(mi);
            pm.add(mi);
            pm.add(mi);
            pm.add(mi);

            ImageIcon im = new ImageIcon(TrayIconTest.class.getResource("/cz/cuni/mff/java/sysint/settings.png"));

            TrayIcon ti = new TrayIcon(im.getImage(), "Test app", null);
            ti.addActionListener(e -> frame.setVisible(true));

            ti.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        pm.setInvoker(pm);
                        pm.setVisible(true);
                        pm.setLocation(e.getX(), e.getY() - pm.getHeight());
                    }
                }
            });

            try {
                st.add(ti);
            } catch (AWTException e) {
                e.printStackTrace();
                System.exit(1);
            }
        } else {
            System.exit(1);
        }

    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(TrayIconTest::createAndShowGUI);
    }
}
