package company.sdk.connection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


/**
 * Created by aleksanderkristiansen on 24/10/2016.
 */
public class ServerConnection {
    public static String serverURL = "http://localhost:8080/myServer_war_exploded";
    private CloseableHttpClient httpClient;



  public ServerConnection(){
            this.httpClient = HttpClients.createDefault();
        }


    public void execute(HttpUriRequest uriRequest, final ResponseParser methods){

        // Create a custom response handler
        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

            public String handleResponse(final HttpResponse response) throws IOException {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    methods.error(status);
                }
                return null;
            }

        };

        try {
            String json = this.httpClient.execute(uriRequest, responseHandler);
            methods.payload(json);
        } catch (IOException e) {
            System.out.print(uriRequest);
            System.out.print("Bare ærgerligt");
            e.printStackTrace();
        }

    }
}
