package cz.cuni.mff.java.swing.editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Editor {

    private static JFrame frame;
    private static Path path;
    private static JTextArea textArea = new JTextArea();
    private static List<String> cnt;
    private static boolean needSave;
    private static final String TITLE = "Editor";
    private static final String TITLE_SAVE = "Editor *UNSAVED*";

    private static void createAndShowGUI() {
        frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Container content = frame.getContentPane();

        if (cnt != null) {
            textArea.setText(String.join("\n", cnt));
        }
        content.add(new JScrollPane(textArea));

        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                needSave = true;
                frame.setTitle(TITLE_SAVE);
            }
        });

        frame.add(createButtonBar(), BorderLayout.WEST);

        //JButton exitButton = new JButton(new ExitAction());
        //frame.add(exitButton, BorderLayout.SOUTH);

        frame.setJMenuBar(createMenu());

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (needSave) {
                    save();
                }
                System.exit(0);
            }
        });

        frame.pack();
        frame.setSize(300, 200);
        frame.setVisible(true);
    }

    private static JPanel createButtonBar() {
        JPanel buttons = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> textArea.setText(""));
        buttons.add(clearButton, c);

        JButton saveButton = new JButton(new SaveAction());
        buttons.add(saveButton, c);

        JButton saveExitButton = new JButton("Save & Exit");
        // TODO
        buttons.add(saveExitButton, c);

        JButton exitButton = new JButton(new ExitAction());
        c.weighty = 1.0;
        buttons.add(exitButton, c);

        return buttons;
    }

    private static JMenuBar createMenu() {
        JMenuBar mb = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveItem = new JMenuItem(new SaveAction());
        fileMenu.add(saveItem);
        fileMenu.add(new JSeparator());
        JMenuItem exitMenuItem = new JMenuItem(new ExitAction());
        fileMenu.add(exitMenuItem);
        mb.add(fileMenu);
        return mb;
    }

    private static class ExitAction extends AbstractAction {

        public ExitAction() {
            super("Exit", new ImageIcon(HelloWorld.class.getResource("/cz/cuni/mff/java/swing/editor/exit.png")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (needSave) {
                save();
            }
            System.exit(0);
        }
    }

    private static void save() {
        try {
            Files.write(path, Collections.singletonList(textArea.getText()));
            System.out.println("Saved");
            needSave = false;
        } catch (IOException ex) {
            System.out.println("Cannot be saved");
        }
    }

    private static class SaveAction extends AbstractAction {
        public SaveAction() {
            super("Save", new ImageIcon(HelloWorld.class.getResource("/cz/cuni/mff/java/swing/editor/save.png")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            save();
            frame.setTitle(TITLE);
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Give me a file");
            System.exit(1);
        }

        path = Path.of(args[0]);
        if (Files.exists(path)) {
            try {
                cnt = Files.readAllLines(path);
            } catch (IOException ex) {
                System.out.println("Cannot read a file");
                System.exit(1);
            }
        }

        SwingUtilities.invokeLater(Editor::createAndShowGUI);
    }
}
