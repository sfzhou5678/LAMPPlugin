package http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import slp.core.infos.MethodInfo;

import java.util.ArrayList;
import java.util.List;

public class LAMPHttpClient {
    private String host;
    private int port;

    private String baseUrl;

    public LAMPHttpClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.baseUrl = String.format("http://%s:%d", host, port);
    }

    /**
     * A function only used for local evaluation.
     * each time be invoked, send a message to python server to get the next code example relevant code snippets.
     *
     * @return
     */
    @Deprecated
    public List<MethodInfo> showNextExample() {
        String restfulAPI = "next_bcb_example";
        String url = baseUrl + "/" + restfulAPI;
        String query = "";
        String jsonData = LAMPHttpUtil.post(url, query);

        List<MethodInfo> methodInfoList;
        if (jsonData != null && !jsonData.isEmpty()) {
            Gson gson = new Gson();
            methodInfoList = gson.fromJson(
                    jsonData, new TypeToken<List<MethodInfo>>() {
                    }.getType());
        } else {
            methodInfoList = new ArrayList<>();
        }
        return methodInfoList;
    }
}
