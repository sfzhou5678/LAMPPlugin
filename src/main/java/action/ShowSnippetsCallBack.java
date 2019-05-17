package action;

import gui.LampMainToolWindow;
import slp.core.infos.MethodInfo;

import java.util.List;

public interface ShowSnippetsCallBack {
    public void showSnippets(List<MethodInfo> methodInfoList, LampMainToolWindow toolWindow);
}
