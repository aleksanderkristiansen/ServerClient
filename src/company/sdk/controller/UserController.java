package company.sdk.controller;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import company.sdk.Encrypters.Crypter;
import company.sdk.ServerConnection;
import com.google.gson.Gson;
import company.sdk.model.Book;
import company.sdk.model.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by aleksanderkristiansen on 24/10/2016.
 */
public class UserController {

    public void logout(String token) throws IOException {
        String path = "user/logout";
        String httpMetode = "POST";

        ServerConnection.openServerConnection(path, httpMetode);

        ServerConnection.conn.setRequestProperty("Content-Type",
                "text");

        OutputStreamWriter out = new OutputStreamWriter(ServerConnection.conn.getOutputStream());

        out.write(token);

        out.close();

        ServerConnection.conn.disconnect();
    }

    public User getAuth(String email, String password) throws IOException {

        String path = "user/login";
        String httpMetode = "POST";

        ServerConnection.openServerConnection(path, httpMetode);

        Gson gson = new Gson();

        User loginUser = new User(email, password);

        String jsonUser = gson.toJson(loginUser);

        String jsonUserC = Crypter.encryptDecryptXOR(jsonUser);

        OutputStreamWriter out = new OutputStreamWriter(ServerConnection.conn.getOutputStream());

        out.write(jsonUserC);

        out.close();

        BufferedReader br = new BufferedReader(new InputStreamReader((ServerConnection.conn.getInputStream())));

        StringBuilder builder = new StringBuilder();
        String aux = "";

        while ((aux = br.readLine()) != null) {
            builder.append(aux + "\n");
        }

        String text = builder.toString();

        String decrypt = Crypter.encryptDecryptXOR(text);

        JsonReader reader = new JsonReader(new StringReader(decrypt));
        reader.setLenient(true);

        User user = gson.fromJson(reader, User.class);

        ServerConnection.conn.disconnect();

        return user;
    }

    public ArrayList<User> getAllUsers(String token) throws IOException{

        String path = "user";
        String httpMetode = "GET";

        ServerConnection.openServerConnection(path, httpMetode);

        ServerConnection.conn.addRequestProperty("authorization", token);

        BufferedReader br = new BufferedReader(new InputStreamReader((ServerConnection.conn.getInputStream())));

        StringBuilder builder = new StringBuilder();
        String aux = "";

        while ((aux = br.readLine()) != null) {
            builder.append(aux + "\n");
        }

        String text = builder.toString();



        String decrypt = Crypter.encryptDecryptXOR(text);

        Gson gson = new Gson();

        JsonReader reader = new JsonReader(new StringReader(decrypt));

        reader.setLenient(true);

        Type type = new TypeToken<ArrayList<User>>() {}.getType();

        ArrayList<User> getAllUsers = gson.fromJson(reader, type);

        return getAllUsers;

    }

    public User getUser(String token, int id) throws IOException {
        String path = "user/" + id;
        String httpMetode = "GET";

        ServerConnection.openServerConnection(path, httpMetode);

        ServerConnection.conn.addRequestProperty("authorization", token);

        BufferedReader br = new BufferedReader(new InputStreamReader((ServerConnection.conn.getInputStream())));

        StringBuilder builder = new StringBuilder();
        String aux = "";

        while ((aux = br.readLine()) != null) {
            builder.append(aux + "\n");
        }

        String text = builder.toString();

        String decrypt = Crypter.encryptDecryptXOR(text);

        Gson gson = new Gson();

        JsonReader reader = new JsonReader(new StringReader(decrypt));
        reader.setLenient(true);

        User user = gson.fromJson(reader, User.class);

        ServerConnection.conn.disconnect();

        return user;
    }


}
