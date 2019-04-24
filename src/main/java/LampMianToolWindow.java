import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.intellij.openapi.wm.ToolWindow;
import component.SnippetJTable;
import component.TextAreaEditor;
import component.TextAreaRenderer;
import http.LAMPHttpClient;
import infos.MethodInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class LampMianToolWindow {
    private JPanel mainPanel;
    private JScrollPane scroll;
    private JButton btnRefresh;

    Object[] columnIdentifiers = new Object[]{"CodeExamples"};
    private DefaultTableModel tableModel;
    private SnippetJTable table;

    private String localServerUrl = "http://localhost:58362";

    public LampMianToolWindow(ToolWindow toolWindow) {
        btnRefresh.addActionListener(e -> updateData());
        this.initDataList();
    }

    private void updateData() {
        String restfulAPI = "search_codes";
        String url = localServerUrl + "/" + restfulAPI;
        String query = "";    // fixme: use context code and other information.
        String jsonData = LAMPHttpClient.post(url, query);
        if (jsonData != null && !jsonData.isEmpty()) {
            Gson gson = new Gson();
            List<MethodInfo> methodInfoList = gson.fromJson(
                    jsonData, new TypeToken<List<MethodInfo>>() {
                    }.getType());

            Object[][] dataVector = new Object[methodInfoList.size()][1];
            for (int i = 0; i < methodInfoList.size(); i++) {
                MethodInfo methodInfo = methodInfoList.get(i);
                try {
                    dataVector[i][0] = String.join("", methodInfo.getRawLineCodes());
                } catch (Exception e) {
                    dataVector[i][0] = "";
                }

            }
            tableModel.setDataVector(dataVector, columnIdentifiers);

            table = new SnippetJTable(tableModel);
            table.getColumn("CodeExamples").setCellRenderer(new TextAreaRenderer());
            table.getColumn("CodeExamples").setCellEditor(new TextAreaEditor());

            scroll.setViewportView(table);
        }

    }

    /**
     * Paint retrieved snippets.
     */
    private void initData() {
        // TODO put real data.
        String tokens = "private void clickButtonAt(Point point) {\n" +
                "        int index = jlist.locationToIndex(point);\n" +
                "        PanelItem item = (PanelItem) jlist.getModel().getElementAt(index);\n" +
                "        item.getButton().doClick();\n" +
                "    }";
        Object[][] dataVector = new Object[][]{{tokens}, {tokens}, {tokens}, {tokens}};
        tableModel.setDataVector(dataVector, columnIdentifiers);

        table = new SnippetJTable(tableModel);
        table.getColumn("CodeExamples").setCellRenderer(new TextAreaRenderer());
        table.getColumn("CodeExamples").setCellEditor(new TextAreaEditor());

        scroll.setViewportView(table);
    }

    private void initDataList() {
        tableModel = new DefaultTableModel();
        initData();
    }


    public JPanel getContent() {
        return mainPanel;
    }

}
