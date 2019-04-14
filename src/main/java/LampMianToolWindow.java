import com.intellij.openapi.wm.ToolWindow;
import component.SnippetJTable;
import component.TextAreaEditor;
import component.TextAreaRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class LampMianToolWindow {
    private JPanel mainPanel;
    private JScrollPane scroll;
    private JButton btnRefresh;

    Object[] columnIdentifiers = new Object[]{"CodeExamples"};
    private DefaultTableModel tableModel;
    private SnippetJTable table;


    public LampMianToolWindow(ToolWindow toolWindow) {
        btnRefresh.addActionListener(e -> updateData());

        this.initDataList();
    }

    /**
     * Paint retrieved snippets.
     */
    private void updateData() {
        // TODO put real data.
        String tokens = "private void clickButtonAt(Point point) {\n" +
                "        int index = jlist.locationToIndex(point);\n" +
                "        PanelItem item = (PanelItem) jlist.getModel().getElementAt(index);\n" +
                "        item.getButton().doClick();\n" +
                "    }";
        Object[][] dataVector = new Object[][]{{tokens}, {tokens}, {tokens},
                {tokens}, {tokens}, {tokens}, {tokens}, {tokens}, {tokens}};
        tableModel.setDataVector(dataVector, columnIdentifiers);

        table = new SnippetJTable(tableModel);
        table.getColumn("CodeExamples").setCellRenderer(new TextAreaRenderer());
        table.getColumn("CodeExamples").setCellEditor(new TextAreaEditor());

        scroll.setViewportView(table);
    }

    private void initDataList() {
        tableModel = new DefaultTableModel();
        updateData();
    }


    public JPanel getContent() {
        return mainPanel;
    }
}
