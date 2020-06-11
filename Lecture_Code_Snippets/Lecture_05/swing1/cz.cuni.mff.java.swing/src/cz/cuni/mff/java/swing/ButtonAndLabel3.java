package cz.cuni.mff.java.swing;

import javax.swing.*;
import java.awt.*;

public class ButtonAndLabel3 {

    private static Component createComponents() {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        JButton button = new JButton("Click here");
        panel.add(button);
        JLabel label = new JLabel("Hello World");
        panel.add(label);
        return panel;
    }

    private static void initLookAndFeel(String[] args) {
        String lookAndFeel;

        if (args.length == 0) {
            lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
        } else {
            switch (args[0]) {
                case "Metal":
                    lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
                    break;
                case "System":
                    lookAndFeel = UIManager.getSystemLookAndFeelClassName();
                    break;
                case "Motif":
                    lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
                    break;
                case "GTK+":
                    lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
                    break;
                case "Win":
                    lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
                    break;
                case "Nimbus":
                    lookAndFeel = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
                    break;
                default:
                    lookAndFeel = args[0];
                    break;
            }
        }

        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void createAndShowGUI(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Button and Label 3");

        initLookAndFeel(args);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Component panel = createComponents();

        frame.getContentPane().add(panel);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(final String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI(args));
    }

}
