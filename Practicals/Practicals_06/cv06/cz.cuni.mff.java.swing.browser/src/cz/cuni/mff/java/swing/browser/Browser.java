package cz.cuni.mff.java.swing.browser;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Browser {

    private static JFrame frame;
    private static JTextArea textArea = new JTextArea();
    private static JTextField urlField = new JTextField();

    private static final String BROWSER = "BROWSER";
    private static final String LOADING = "LOADING";

    private static void createAndShowGUI() {
        frame = new JFrame("Browser");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Container content = frame.getContentPane();

        JPanel panel = new JPanel(new CardLayout());


        textArea.setEditable(false);
        panel.add(new JScrollPane(textArea), BROWSER);

        ImageIcon icon = new ImageIcon(Browser.class.getResource("hour-glass.png"));
        panel.add(new JLabel(icon), LOADING);

        content.add(panel);

        content.add(urlField, BorderLayout.NORTH);

        urlField.addActionListener(e -> {
            String url = urlField.getText().trim();
            if (!url.isEmpty()) {
                //textArea.setText("Loading...");
                ((CardLayout) panel.getLayout()).show(panel, LOADING);
                urlField.setEnabled(false);
                SwingWorker<String, Object> sw = new SwingWorker<>() {
                    @Override
                    protected String doInBackground() throws Exception {
                        try {
                            URL u = new URL(url);
                            try (BufferedReader br = new BufferedReader(new InputStreamReader(u.openStream()))) {
                                int c;
                                StringBuilder sb = new StringBuilder();
                                while ((c = br.read()) != -1) {
                                    sb.append((char) c);
                                }
                                return sb.toString();
                            } catch (IOException ex) {
                                return "IOException: " + ex.getMessage();
                            }
                        } catch (MalformedURLException ex) {
                            return "Malformed URL";
                        }
                    }

                    @Override
                    protected void done() {
                        try {
                            textArea.setText(get());
                        } catch (InterruptedException | ExecutionException ex) {
                            Logger.getLogger(Browser.class.getName()).log(Level.WARNING, "Exception occurred", ex);
                        }
                        ((CardLayout) panel.getLayout()).show(panel, BROWSER);
                        urlField.setEnabled(true);
                    }
                };
                sw.execute();
            }
        });

        frame.pack();
        frame.setSize(300, 200);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Browser::createAndShowGUI);
    }
}