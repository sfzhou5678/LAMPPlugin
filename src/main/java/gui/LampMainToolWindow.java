package gui;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.intellij.openapi.wm.ToolWindow;
import component.SnippetJTable;
import component.TextAreaEditor;
import component.TextAreaRenderer;
import http.LAMPHttpClient;
import http.LAMPHttpUtil;
import javafx.util.Pair;
import slp.core.infos.MethodInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class LampMainToolWindow {
    private JPanel mainPanel;
    private JScrollPane scroll;
    private JButton btnRefresh;

    Object[] columnIdentifiers = new Object[]{"CodeExamples"};
    private DefaultTableModel tableModel;
    private SnippetJTable table;

    public LampMainToolWindow(ToolWindow toolWindow) {
        btnRefresh.addActionListener(e -> updateData());
        this.initView();
    }

    public void initView() {
        tableModel = new DefaultTableModel();
        initData();
    }

    public void updateView(List<Pair<MethodInfo, Double>> methodInfoList) {
        if (methodInfoList == null || methodInfoList.size() == 0) {
            // do something
        } else {
            List<String> texts = new ArrayList<>();
            for (int i = 0; i < methodInfoList.size(); i++) {
                MethodInfo methodInfo = methodInfoList.get(i).getKey();
                Double score = methodInfoList.get(i).getValue();
                try {
                    StringBuilder stringBuilder = new StringBuilder();
//                    if (!methodInfo.getDocComments().isEmpty()) {
//                        stringBuilder.append("\t/**" + methodInfo.getDocComments() + "*/\n");
//                    }
                    stringBuilder.append("Score:" + String.valueOf(score) + "\n");  // FIXME: integrate this into GUI component
                    stringBuilder.append(String.join("\n", methodInfo.getLineCodes()));
                    texts.add(stringBuilder.toString());
                } catch (Exception e) {
                }
            }
            Object[][] dataVector = new Object[texts.size()][1];
            for (int i = 0; i < texts.size(); i++) {
                dataVector[i][0] = texts.get(i);
            }
            tableModel.setDataVector(dataVector, columnIdentifiers);

            table = new SnippetJTable(tableModel);
            table.getColumn("CodeExamples").setCellRenderer(new TextAreaRenderer());
            table.getColumn("CodeExamples").setCellEditor(new TextAreaEditor());

            scroll.setViewportView(table);
        }
    }

    private LAMPHttpClient httpClient;

    @Deprecated
    private void updateData() {
//        if (httpClient == null) {
//            httpClient = new LAMPHttpClient("localhost", 58362);
//        }
//        List<MethodInfo> methodInfoList = httpClient.showNextExample();
//        updateView(methodInfoList);
    }

    private void initData() {
        // TODO: 2019/4/24 welcome page or something else?
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


    public JPanel getContent() {
        return mainPanel;
    }

}
