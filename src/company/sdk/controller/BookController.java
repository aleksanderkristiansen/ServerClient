package company.sdk.controller;

import com.google.gson.stream.JsonReader;
import company.sdk.Encrypters.Crypter;
import company.sdk.ServerConnection;
import company.sdk.model.Book;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by aleksanderkristiansen on 24/10/2016.
 */
public class BookController {



    public ArrayList<Book> getAllBooks() throws IOException {
        String path = "book";
        String httpMetode = "GET";

        ServerConnection.openServerConnection(path, httpMetode);

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

        Type type = new TypeToken<ArrayList<Book>>() {}.getType();

        ArrayList<Book> yourList = gson.fromJson(reader, type);

        return yourList;
    }

    public Book getBook(int id) throws IOException {
        String path = "book/" + id;
        String httpMetode = "GET";

        ServerConnection.openServerConnection(path, httpMetode);

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

        Book book = gson.fromJson(reader, Book.class);

        ServerConnection.conn.disconnect();



        return book;
    }
}
