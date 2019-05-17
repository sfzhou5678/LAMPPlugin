package handler;

import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import gui.LampMainToolWindow;
import http.LAMPHttpClient;
import javafx.util.Pair;
import slp.core.infos.MethodInfo;
import slp.core.lexing.code.JavaDetailLexer;

import java.util.List;

import static slp.core.lexing.DetailLexerRunner.extractCurrentMethodInfo;

public class RecommendSnippetHandler {
    private JavaDetailLexer lexer = new JavaDetailLexer();
    private LAMPHttpClient httpClient = new LAMPHttpClient("localhost", 58362);

    {
        lexer.setMinSnippetLength(1);
    }

    public void execute(LampMainToolWindow toolWindow, Editor editor, Document doc) {
        try {
            int offset = editor.getCaretModel().getOffset();    // the pos (offset) of cursor in the given document.
            String codeContext = doc.getText().substring(0, offset);

            MethodInfo currentMethod = extractCurrentMethodInfo(lexer, codeContext);
            if (currentMethod != null) {
                // 1. remote LM & remote retriever
                List<Pair<MethodInfo, Double>> methodInfoList = httpClient.searchCode(codeContext, currentMethod);

                // if the retrieved results are different with the current code context, it will be ignored.
                int offset2 = editor.getCaretModel().getOffset();
                if (Math.abs(offset2 - offset) <= 30) {
                    toolWindow.initView();
                    toolWindow.updateView(methodInfoList);
                }
                // TODO: 2019/4/24  2. local LM & local retriever

                // TODO: 2019/4/24  3. merge results from remote & local, if remote overtimes, only show the local results.
            }
        } catch (Exception exception) {
        }
    }
}
