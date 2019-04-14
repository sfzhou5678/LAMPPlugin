package component;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class TextAreaRenderer extends JScrollPane implements TableCellRenderer {
    JTextArea textarea;

    public TextAreaRenderer() {
        textarea = new JTextArea();
        textarea.setLineWrap(true);
        textarea.setWrapStyleWord(true);
        textarea.setBorder(new TitledBorder("Title Border"));
        getViewport().add(textarea);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        table.setRowHeight(row, 150);   // TODO dynamic set row height
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
            textarea.setForeground(table.getSelectionForeground());
            textarea.setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
            textarea.setForeground(table.getForeground());
            textarea.setBackground(table.getBackground());
        }

        textarea.setText((String) value);
        textarea.setCaretPosition(0);
        return this;
    }
}