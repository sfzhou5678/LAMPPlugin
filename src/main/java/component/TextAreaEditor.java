package component;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class TextAreaEditor extends DefaultCellEditor {
    protected JScrollPane scrollpane;
    protected JTextArea textarea;

    public TextAreaEditor() {
        super(new JCheckBox());
        scrollpane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        textarea = new JTextArea();
        textarea.setLineWrap(true);
        textarea.setEditable(true);
        textarea.setWrapStyleWord(true);
        textarea.setBorder(new TitledBorder("Title Border"));
        scrollpane.getViewport().add(textarea);
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        textarea.setText((String) value);

        return scrollpane;
    }

    public Object getCellEditorValue() {
        return textarea.getText();
    }
}