package company.sdk.controller;

import company.sdk.Encrypters.Crypter;
import company.sdk.ServerConnection;
import com.google.gson.Gson;
import company.sdk.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by aleksanderkristiansen on 24/10/2016.
 */
public class UserController {

    public String getAuth(String username, String password) throws IOException {

        String path = "user/login";
        String httpMetode = "POST";

        ServerConnection.openServerConnection(path, httpMetode);

        Gson gson = new Gson();

        User loginUser = new User(username, password);

        String jsonUser = gson.toJson(loginUser);

        String jsonUserC = Crypter.encryptDecryptXOR(jsonUser);

        OutputStreamWriter out = new OutputStreamWriter(ServerConnection.conn.getOutputStream());

        out.write(jsonUserC);

        out.close();

        BufferedReader br = new BufferedReader(new InputStreamReader((ServerConnection.conn.getInputStream())));

        String auth = br.readLine();

        ServerConnection.conn.disconnect();



        return auth;
    }


}
