package http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import config.SettingConfig;
import javafx.util.Pair;
import slp.core.infos.MethodInfo;

import java.io.UnsupportedEncodingException;
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

    public List<Pair<MethodInfo, Double>> searchCode(String codeContext, MethodInfo currentMethod, SettingConfig config) {
        String restfulAPI = "search_codes";
        String url = baseUrl + "/" + restfulAPI;
        JSONObject map = new JSONObject(true);
        map.put("codeContext", codeContext);
        map.put("snippet", currentMethod);
        map.put("useBert", config.isENABLE_DEEP_SEMANTIC());
        String query = map.toJSONString();
        String jsonData = LAMPHttpUtil.post(url, query);


        List<Pair<MethodInfo, Double>> results = null;
        if (jsonData != null && !jsonData.isEmpty()) {
            try {
                JSONArray jsonArray = JSON.parseArray(jsonData);
                results = new ArrayList<>();
                for (Object o : jsonArray) {
                    JSONObject jsonObject = (JSONObject) o;
                    MethodInfo methodInfo = JSON.parseObject(JSON.toJSONString(jsonObject.get("methodInfo")), MethodInfo.class);
                    Double score = Double.valueOf(jsonObject.get("score").toString());
                    results.add(new Pair<>(methodInfo, score));
                }
            } catch (Exception e) {
                System.out.println(jsonData);
            }
        }
        return results;
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
