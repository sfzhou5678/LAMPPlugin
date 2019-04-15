package http.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import infos.MethodInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class HttpDemo {
    public static void postMethod(String url, String query) throws IOException {
        URL restURL = new URL(url);

        HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        PrintStream ps = new PrintStream(conn.getOutputStream());
        ps.print(query);
        ps.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String jsonData = br.readLine();
        br.close();

        // TODO: 2019/4/15 get snippets list, draw them.
        if (jsonData != null) {
            Gson gson = new Gson();
            List<MethodInfo> methodInfoList = gson.fromJson(
                    jsonData, new TypeToken<List<MethodInfo>>() {
                    }.getType());
            for (MethodInfo info : methodInfoList) {
                System.out.println(info);
            }
        }
    }

    public static void main(String[] args) {
        try {

            String url = "http://localhost:58362/search_codes";
            String query = "{\"topk\":\"3\"}"; //json格式
            postMethod(url, query);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
