package cz.cuni.mff.java.swing.browser;


import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BrowserWithCombo {

    private static JFrame frame;
    private static JPanel panel;
    private static JTextArea textArea = new JTextArea();
    private static JComboBox<String> urlCombo = new JComboBox<>();
    private static List<String> usedUrls = new ArrayList<>();

    private static final String BROWSER = "BROWSER";
    private static final String LOADING = "LOADING";

    private static void loading(boolean add) {
        String url = ((String)urlCombo.getEditor().getItem()).trim();
        if (!url.isEmpty()) {
            //textArea.setText("Loading...");
            ((CardLayout) panel.getLayout()).show(panel, LOADING);
            urlCombo.setEnabled(false);
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
                        Logger.getLogger(BrowserWithCombo.class.getName()).log(Level.WARNING, "Exception occurred", ex);
                    }
                    ((CardLayout) panel.getLayout()).show(panel, BROWSER);
                    if (add && !usedUrls.contains(url)) {
                        usedUrls.add(url);
                        urlCombo.addItem(url);
                    }
                    urlCombo.setEnabled(true);
                }
            };
            sw.execute();
        }
    }

    private static void createAndShowGUI() {
        frame = new JFrame("Browser");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Container content = frame.getContentPane();

        panel = new JPanel(new CardLayout());


        textArea.setEditable(false);
        panel.add(new JScrollPane(textArea), BROWSER);

        ImageIcon icon = new ImageIcon(BrowserWithCombo.class.getResource("hour-glass.png"));
        panel.add(new JLabel(icon), LOADING);

        content.add(panel);

        urlCombo.setEditable(true);
        content.add(urlCombo, BorderLayout.NORTH);

        urlCombo.addItemListener(e -> loading(false));
        urlCombo.getEditor().addActionListener(e -> loading(true));

        frame.pack();
        frame.setSize(300, 200);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BrowserWithCombo::createAndShowGUI);
    }
}