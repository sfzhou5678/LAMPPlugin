package action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import gui.LampMainToolWindow;
import http.LAMPHttpClient;
import javafx.util.Pair;
import service.MainToolWindowService;
import slp.core.infos.MethodInfo;
import slp.core.lexing.code.JavaDetailLexer;

import java.util.List;

import static slp.core.lexing.DetailLexerRunner.extractCurrentMethodInfo;

public class InvokeLAMPAction extends AnAction {
    private JavaDetailLexer lexer = new JavaDetailLexer();
    private LAMPHttpClient httpClient = new LAMPHttpClient("localhost", 58362);

    {
        lexer.setMinSnippetLength(2);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        new Thread(() -> {
            // get get registered service
            Project project = e.getData(PlatformDataKeys.PROJECT);
            MainToolWindowService mainToolWindowService = ServiceManager.getService(project, MainToolWindowService.class);
            LampMainToolWindow toolWindow = mainToolWindowService.getToolWindow();

            // get current-edit code and something relevant
            try {
                Editor editor = e.getData(PlatformDataKeys.EDITOR);
                final Document doc = editor.getDocument();
                int offset = editor.getCaretModel().getOffset();    // the pos (offset) of cursor in the given document.
                String codeContext = doc.getText().substring(0, offset);

                // extract the current-edit method
                MethodInfo currentMethod = extractCurrentMethodInfo(lexer, codeContext);
                if (currentMethod != null) {
                    // 1. remote LM & remote retriever
                    // TODO: 2019/5/17 show the confidence scores
                    List<Pair<MethodInfo, Double>> methodInfoList = httpClient.searchCode(codeContext, currentMethod);

                    // if the retrieved results are different with the current code context, it will be ignored.
                    int offset2 = editor.getCaretModel().getOffset();
                    if (Math.abs(offset2 - offset) <= 30) {
                        toolWindow.initView();
                        toolWindow.updateView(methodInfoList);
//                        showSnippets(methodInfoList, toolWindow);
                    }
                    // TODO: 2019/4/24  2. local LM & local retriever

                    // TODO: 2019/4/24  3. merge results from remote & local, if remote overtimes, only show the local results.
                }
            } catch (Exception exception) {
                return;
            }
        }).start();


    }
}
