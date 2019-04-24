package action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import gui.LampMianToolWindow;
import service.MainToolWindowService;
import slp.core.infos.MethodInfo;
import slp.core.lexing.code.JavaDetailLexer;

import java.util.stream.Collectors;

import static slp.core.lexing.DetailLexerRunner.extractCurrentMethodInfo;

public class InvokeLAMPAction extends AnAction {
    private JavaDetailLexer lexer = new JavaDetailLexer();

    {
        lexer.setMinSnippetLength(2);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        // get get registered service
        Project project = e.getData(PlatformDataKeys.PROJECT);
        MainToolWindowService mainToolWindowService = ServiceManager.getService(project, MainToolWindowService.class);
        LampMianToolWindow toolWindow = mainToolWindowService.getToolWindow();
        toolWindow.initView();

        // get current-edit code and something relevant
        try {
            Editor editor = e.getData(PlatformDataKeys.EDITOR);
            final Document doc = editor.getDocument();

            int offset = editor.getCaretModel().getOffset();    // the pos (offset) of cursor in the given document.
            String codeContext = doc.getText().substring(0, offset);

            // extract the current-edit method
            MethodInfo currentMethod = extractCurrentMethodInfo(lexer, codeContext);
            if (currentMethod != null) {
                // then get relevant data and feed forward into toolWindow
                System.out.println(currentMethod);

                // TODO: 2019/4/24 1. remote LM & remote retriever

                // TODO: 2019/4/24  2. local LM & local retriever

                // TODO: 2019/4/24  3. merge results from remote & local, if remote overtimes, only show the local results.
            }
        } catch (Exception exception) {
            return;
        }
    }
}
