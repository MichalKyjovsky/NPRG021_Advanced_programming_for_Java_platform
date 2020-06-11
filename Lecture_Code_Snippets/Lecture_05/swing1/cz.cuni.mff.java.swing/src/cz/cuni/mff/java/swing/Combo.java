package cz.cuni.mff.java.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Combo implements ActionListener {

  private JLabel label;
  private JComboBox<String> cb;
  
  private Component createComponents() {
    JPanel panel = new JPanel(new GridLayout(0, 1));
    
    panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
   
    String[] list = { "aaaa", "bbbb", "cccc", "dddd", "eeee" };
    cb = new JComboBox<>(list);
    cb.addActionListener(this);
    panel.add(cb);

    label = new JLabel("Hello World");
    panel.add(label);

    return panel;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    label.setText(cb.getSelectedItem().toString());
  }

  private static void createAndShowGUI(String[] args) {
    JFrame frame = new JFrame("Combo box");

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Combo ap = new Combo();
    Component panel = ap.createComponents();
    
    frame.getContentPane().add(panel);
    
    frame.pack();
    frame.setVisible(true);
  }

  public static void main(final String[] args) {
    javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI(args));
  }

}
