package cz.cuni.mff.java.formannotations;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FormPanel<T> extends JPanel {
    private final Class<T> clazz;
    private final LinkedHashMap<String, JComponent> jfields = new LinkedHashMap<>();

    public FormPanel(Class<T> clazz) {
        super(new GridBagLayout());
        this.clazz = clazz;

        GridBagConstraints c = new GridBagConstraints();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Class<?> fieldType = field.getType();
            String fieldName = field.getName();
            FormField annot = field.getAnnotation(FormField.class);

            String labelText;

            if (annot == null || annot.name().trim().isEmpty()) {
                StringBuilder sb = new StringBuilder(fieldName);
                sb.setCharAt(0, Character.toUpperCase(fieldName.charAt(0)));
                labelText = sb.toString();
            } else {
                labelText = annot.name();
            }

            JComponent comp = null;

            if (String.class == fieldType) {
                if (annot == null) {
                    comp = new JTextField();
                } else {
                    switch (annot.kind()) {
                        case TEXT:
                            comp = new JTextField();
                            break;
                        case PASSWORD:
                            comp = new JPasswordField();
                            break;
                    }
                }
            } else if (boolean.class == fieldType) {
                comp = new JCheckBox();
            } else {
                System.out.println("Unsupported type. Ignored.");
                break;
            }

            jfields.put(fieldName, comp);

            if (field == fields[fields.length - 1]) {
                c.weighty = 1.;
            }

            c.gridwidth = GridBagConstraints.RELATIVE;
            c.weightx = 0.;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.anchor = GridBagConstraints.PAGE_START;
            c.insets = new Insets(2, 5, 0, 5);
            JLabel l = new JLabel(labelText);
            this.add(l, c);
            c.gridwidth = GridBagConstraints.REMAINDER;
            c.weightx = 1.;
            this.add(comp, c);
        }
    }

    public void setObjectValues(T obj) {
        for (String s : jfields.keySet()) {
            try {
                Field f = obj.getClass().getDeclaredField(s);
                Object value = f.get(obj);
                JComponent c = jfields.get(s);
                if (c instanceof JCheckBox) {
                    ((JCheckBox) c).setSelected((Boolean) value);
                } else {
                    ((JTextField) c).setText((String) value);
                }
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(FormPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void getObjectValues(T obj) {
        for (String s: jfields.keySet()) {
            try {
                Field f = obj.getClass().getDeclaredField(s);
                Object value;
                JComponent c = jfields.get(s);
                if (c instanceof JCheckBox) {
                    value = ((JCheckBox) c).isSelected();
                } else {
                    value = ((JTextField) c).getText();
                }
                f.set(obj, value);
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(FormPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
