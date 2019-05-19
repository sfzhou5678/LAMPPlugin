package action;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import com.intellij.openapi.project.Project;
import gui.LampMainToolWindow;
import handler.RecommendSnippetHandler;
import org.jetbrains.annotations.NotNull;
import service.MainToolWindowService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LAMPTypedHandler implements TypedActionHandler {
    private TypedActionHandler oldHandler;
    private RecommendSnippetHandler recommendSnippetHandler = new RecommendSnippetHandler();

    public static final Character[] reversedChars = {
            '\n', ' ', '(', ')', '{', '}', '.', ';', '@', '+', '[', ']', '=', ',', '=', ':', '<', '>', '!'
    };

    public static final Set<Character> REVERSED_SET = new HashSet<>(Arrays.asList(reversedChars));

    @Override
    public void execute(@NotNull Editor editor, char charTyped, @NotNull DataContext dataContext) {
        if (oldHandler != null)
            oldHandler.execute(editor, charTyped, dataContext);
        if (!REVERSED_SET.contains(charTyped)) {
            return;
        }

        new Thread(() -> {
            Project project = editor.getProject();
            MainToolWindowService mainToolWindowService = ServiceManager.getService(project, MainToolWindowService.class);
            LampMainToolWindow toolWindow = mainToolWindowService.getToolWindow();

            final Document doc = editor.getDocument();
            recommendSnippetHandler.execute(toolWindow, editor, doc);
        }).start();
    }

    public void setOldHandler(TypedActionHandler oldHandler) {
        this.oldHandler = oldHandler;
    }
}
