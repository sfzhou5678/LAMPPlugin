package http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class LAMPHttpUtil {
    /**
     * Given url and query, return response(json format).
     *
     * @param url
     * @param query
     * @return
     * @throws IOException
     */
    public static String post(String url, String query) {
        try {
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

            return jsonData;
        } catch (IOException e) {
            return "";
        }
    }

}
