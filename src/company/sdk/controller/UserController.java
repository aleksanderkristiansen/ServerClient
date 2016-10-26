package company.sdk.controller;

import company.sdk.ServerConnection;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by aleksanderkristiansen on 24/10/2016.
 */
public class UserController {

    public String getAuth(String username, String password) throws IOException {

        String path = "user/login";
        String httpMetode = "POST";

        ServerConnection.openServerConnection(path, httpMetode);

        BufferedReader br = new BufferedReader(new InputStreamReader((ServerConnection.conn.getInputStream())));

        Gson gson = new Gson();

        String auth = gson.fromJson(br, String.class);

        ServerConnection.conn.disconnect();

        return auth;
    }


}
