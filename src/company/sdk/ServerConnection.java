package company.sdk;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by aleksanderkristiansen on 24/10/2016.
 */
public class ServerConnection {

    public static URL url;

    public static HttpURLConnection conn;

    public ServerConnection(){

    }

    public static void openServerConnection(String path, String httpMethod) throws IOException {
        url = new URL("http://localhost:8080/myServer_war_exploded/" + path);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(httpMethod);
        conn.setRequestProperty("Accept", "application/json");

        conn.setDoInput(true);
        conn.setDoOutput(true);

        if (httpMethod.equals("POST")){
            conn.setRequestProperty("Content-Type",
                    "application/json");

        }

    }
}
