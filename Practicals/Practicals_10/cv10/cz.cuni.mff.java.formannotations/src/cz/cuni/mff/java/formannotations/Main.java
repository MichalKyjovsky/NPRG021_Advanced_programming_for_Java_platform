package cz.cuni.mff.java.formannotations;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
        
    }
    
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Forms");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = frame.getContentPane();

        FormPanel<TestData> fp = new FormPanel<>(TestData.class);
        content.add(fp);
        
        JButton ok = new JButton("OK");
        ok.addActionListener(e ->{
            TestData td = new TestData();
            fp.getObjectValues(td);
            System.out.println(td);
        });
        content.add(ok, BorderLayout.SOUTH);
       
        
        TestData td = new TestData("Petr", "password", true);
        fp.setObjectValues(td);
        
        frame.pack();
        frame.setVisible(true);
    }
    
}
