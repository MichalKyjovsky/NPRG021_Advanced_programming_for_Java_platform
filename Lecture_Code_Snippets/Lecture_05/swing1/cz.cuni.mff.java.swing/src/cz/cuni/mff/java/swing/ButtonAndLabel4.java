package cz.cuni.mff.java.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ButtonAndLabel4 implements ActionListener {

    private JLabel label;
    private int clicks = 0;

    private Component createComponents() {
        JPanel panel = new JPanel(new GridLayout(0, 1));

        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));

        JButton button = new JButton("Click here");
        button.addActionListener(this);
        panel.add(button);

        label = new JLabel("Hello World: " + clicks);
        panel.add(label);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        clicks++;
        label.setText("Hello World: " + clicks);
    }

    private static void initLookAndFeel(String[] args) {
        String lookAndFeel;

        if (args.length == 0) {
            lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
        } else {
            if (args[0].equals("Metal")) {
                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
            } else if (args[0].equals("System")) {
                lookAndFeel = UIManager.getSystemLookAndFeelClassName();
            } else if (args[0].equals("Motif")) {
                lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
            } else if (args[0].equals("GTK+")) {
                lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
            } else if (args[0].equals("Win")) {
                lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
            } else {
                lookAndFeel = args[0];
            }
        }
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void createAndShowGUI(String[] args) {
        JFrame frame = new JFrame("Button and Label 4");

        initLookAndFeel(args);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ButtonAndLabel4 ap = new ButtonAndLabel4();
        Component panel = ap.createComponents();

        frame.getContentPane().add(panel);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(final String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI(args));
    }

}
