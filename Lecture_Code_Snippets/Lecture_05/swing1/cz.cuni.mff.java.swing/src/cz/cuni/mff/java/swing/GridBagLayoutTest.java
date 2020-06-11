package cz.cuni.mff.java.swing;

import javax.swing.*;
import java.awt.*;

public class GridBagLayoutTest {

    private static void makebutton(String name, Container pane, GridBagConstraints c) {
        JButton button = new JButton(name);
        pane.add(button, c);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Grid Layout");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container pane = frame.getContentPane();
        pane.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        makebutton("Button1", pane, c);
        makebutton("Button2", pane, c);
        makebutton("Button3", pane, c);

        c.gridwidth = GridBagConstraints.REMAINDER;
        makebutton("Button4", pane, c);

        c.weightx = 0.0;
        makebutton("Button5", pane, c);

        c.gridwidth = GridBagConstraints.RELATIVE;
        makebutton("Button6", pane, c);

        c.gridwidth = GridBagConstraints.REMAINDER;
        makebutton("Button7", pane, c);

        c.gridwidth = 1;
        c.gridheight = 2;
        c.weighty = 1.0;
        makebutton("Button8", pane, c);

        c.weighty = 1.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridheight = 1;
        makebutton("Button9", pane, c);
        makebutton("Button10", pane, c);

        frame.setSize(new Dimension(300, 100));

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(GridBagLayoutTest::createAndShowGUI);
    }
}
