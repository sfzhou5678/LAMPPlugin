import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import gui.LampMianToolWindow;
import service.MainToolWindowService;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey.Chursin
 * Date: Aug 25, 2010
 * Time: 2:09:00 PM
 */
public class LAMPToolWindowFactory implements ToolWindowFactory {
    // Create the tool window content.
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        LampMianToolWindow lampMianToolWindow = new LampMianToolWindow(toolWindow);

        // get registered service, used for communicating between Action & Tool window
        MainToolWindowService mainToolWindowService = ServiceManager.getService(project, MainToolWindowService.class);
        mainToolWindowService.setToolWindow(lampMianToolWindow);

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(lampMianToolWindow.getContent(), "", false);
        toolWindow.getContentManager().addContent(content);
    }
}
