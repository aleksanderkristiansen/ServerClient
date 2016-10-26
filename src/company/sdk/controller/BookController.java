package company.sdk.controller;

import company.sdk.Encrypters.Crypter;
import company.sdk.ServerConnection;
import company.sdk.model.Book;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

        System.out.print(br);

        String test = br.toString();

        System.out.print(test);

        String decrypt = Crypter.encryptDecryptXOR(test);

        Gson gson1 = new Gson();

        String bla = gson1.toJson(decrypt);




        Gson gson = new Gson();


//        System.out.print(decrypt);

//        System.out.print(test);



        //   Book[] navigationArray = gson.fromJson(br, Book[].class);                    ß

        //       System.out.print(navigationArray.);

        //      logger.info(navigationArray);

        //     assertEquals(4, navigationArray.length);

        // or

        @SuppressWarnings("serial")
        Type collectionType = new TypeToken<ArrayList<Book>>() {
        }.getType();
        ArrayList<Book> books = gson.fromJson(bla, collectionType);
//
//        logger.info(navigation);
//
//        assertEquals(4, navigation.size());



        ServerConnection.conn.disconnect();

        return books;
    }

    public Book getBook(int id) throws IOException {
        String path = "book/" + id;
        String httpMetode = "GET";

        ServerConnection.openServerConnection(path, httpMetode);

        BufferedReader br = new BufferedReader(new InputStreamReader((ServerConnection.conn.getInputStream())));

        String test = br.toString();

        String decrypt = Crypter.encryptDecryptXOR(test);

        Gson gson = new Gson();

        Book book = gson.fromJson(decrypt, Book.class);

        ServerConnection.conn.disconnect();



        return book;
    }
}
