package company.sdk.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import company.sdk.connection.ResponseCallback;
import company.sdk.connection.ResponseParser;
import company.sdk.connection.ServerConnection;
import company.sdk.encryption.Crypter;
import company.sdk.model.Book;
import company.sdk.model.Curriculum;
import company.sdk.model.User;
import org.apache.http.client.methods.HttpGet;

import java.util.ArrayList;

/**
 * Created by aleksanderkristiansen on 22/11/2016.
 */
public class CurriculumService {
    private ServerConnection connection;
    private Gson gson;

    public CurriculumService(){
        this.connection = new ServerConnection();
        this.gson = new Gson();
    }

    public void getCurriculums(final ResponseCallback<ArrayList<Curriculum>> responseCallback){
        HttpGet getRequest = new HttpGet(ServerConnection.serverURL + "/curriculum");

        this.connection.execute(getRequest, new ResponseParser() {
            public void payload(String json) {
                ArrayList<Curriculum> curriculums = gson.fromJson(Crypter.encryptDecryptXOR(json), new TypeToken<ArrayList<Curriculum>>() {
                }.getType());
                responseCallback.success(curriculums);
            }

            public void error(int status) {
                responseCallback.error(status);
            }
        });
    }

    public void getCurriculumBooks(int id, final ResponseCallback<ArrayList<Book>> responseCallback){
        HttpGet getRequest = new HttpGet(ServerConnection.serverURL + "/curriculum/" + id + "/books");

        this.connection.execute(getRequest, new ResponseParser() {
            public void payload(String json) {
                ArrayList<Book> books = gson.fromJson(Crypter.encryptDecryptXOR(json), new TypeToken<ArrayList<Book>>() {
                }.getType());
                responseCallback.success(books);
            }

            public void error(int status) {
                responseCallback.error(status);
            }
        });
    }
}


