package company.sdk.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import company.sdk.Encrypters.Crypter;
import company.sdk.ServerConnection;
import company.sdk.model.Book;
import company.sdk.model.Curriculum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by aleksanderkristiansen on 30/10/2016.
 */
public class CurriculumController {

    public ArrayList<Curriculum> getAllCurriculums() throws IOException {
        String path = "curriculum";
        String httpMetode = "GET";

        ServerConnection.openServerConnection(path, httpMetode);

        BufferedReader br = new BufferedReader(new InputStreamReader((ServerConnection.conn.getInputStream())));

        ServerConnection.conn.disconnect();

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

        Type type = new TypeToken<ArrayList<Curriculum>>() {}.getType();

        ArrayList<Curriculum> curriculums = gson.fromJson(reader, type);

        return curriculums;
    }

    public ArrayList<Book> getCurriculumsBooks(int id) throws IOException {
        String path = "curriculum/" + id + "/books";
        String httpMetode = "GET";

        ServerConnection.openServerConnection(path, httpMetode);

        BufferedReader br = new BufferedReader(new InputStreamReader((ServerConnection.conn.getInputStream())));

        ServerConnection.conn.disconnect();

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

        Type type = new TypeToken<ArrayList<Book>>() {}.getType();

        ArrayList<Book> curriculumsBooks = gson.fromJson(reader, type);

        return curriculumsBooks;
    }



}
