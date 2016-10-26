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
        url = new URL("http://localhost:8080/server2_0_war_exploded/" + path);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(httpMethod);
        conn.setRequestProperty("Accept", "application/json");

    }


//
//    public String ServerConnection(String path, String httpMethod){
//
//        try {
//
//            URL url = new URL("http://localhost:8080/server2_0_war_exploded/" + path);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod(httpMethod);
//            conn.setRequestProperty("Accept", "application/json");
//
//            if (conn.getResponseCode() != 200) {
//                throw new RuntimeException("Failed : HTTP error code : "
//                        + conn.getResponseCode());
//            }
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(
//                    (conn.getInputStream())));
//
//            conn.disconnect();
//
//            return br.readLine();
///*
//            String output;
//            System.out.println("Output from Server .... \n");
//            while ((output = br.readLine()) != null) {
//                System.out.println(output);
//            }*/
//
//
//        } catch (MalformedURLException e) {
//
//            e.printStackTrace();
//            return null;
//
//        } catch (IOException e) {
//
//            e.printStackTrace();
//            return null;
//
//        }
//    }


}
