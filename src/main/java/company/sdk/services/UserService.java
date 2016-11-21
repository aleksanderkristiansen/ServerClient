package company.sdk.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import company.sdk.connection.ResponseCallback;
import company.sdk.connection.ResponseParser;
import company.sdk.connection.ServerConnection;
import company.sdk.encryption.Crypter;
import company.sdk.encryption.Digester;
import company.sdk.model.Message;
import company.sdk.model.User;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by aleksanderkristiansen on 20/11/2016.
 */
public class UserService {



    private ServerConnection connection;
    private Gson gson;

    public UserService(){
        this.connection = new ServerConnection();
        this.gson = new Gson();
    }

    public void login(String email, String password, final ResponseCallback<User> responseCallback){

        HttpPost postRequest = new HttpPost(ServerConnection.serverURL + "/user/login");

        User login = new User();
        login.setEmail(email);
        String hashedPassword = Digester.hashWithSalt(password);
        login.setPassword(hashedPassword);

        try {
            StringEntity loginInfo = new StringEntity(Crypter.encryptDecryptXOR(this.gson.toJson(login)));
            postRequest.setEntity(loginInfo);
            postRequest.setHeader("Content-Type", "application/json");

            this.connection.execute(postRequest, new ResponseParser() {
                public void payload(String json) {

                    User accessUser = gson.fromJson(Crypter.encryptDecryptXOR(json), User.class);
                    responseCallback.success(accessUser);
                }
                public void error(int status) {
                    responseCallback.error(status);
                }
            });


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void getAll(String token, final ResponseCallback<ArrayList<User>> responseCallback){
        HttpGet getRequest = new HttpGet(ServerConnection.serverURL + "/user");

        getRequest.setHeader("authorization", token);

        this.connection.execute(getRequest, new ResponseParser() {
            public void payload(String json) {
                ArrayList<User> users = gson.fromJson(Crypter.encryptDecryptXOR(json), new TypeToken<ArrayList<User>>() {
                }.getType());
                responseCallback.success(users);
            }

            public void error(int status) {
                responseCallback.error(status);
            }
        });
    }

    public void findById(String id, final ResponseCallback<User> responseCallback){
        HttpGet getRequest = new HttpGet(ServerConnection.serverURL = "/staffs/" + id);

        this.connection.execute(getRequest, new ResponseParser() {
            public void payload(String json) {
                User user = gson.fromJson(json, User.class);
                responseCallback.success(user);
            }

            public void error(int status) {
                responseCallback.error(status);
            }
        });

    }
    public void deleteUser(int id, String token, final ResponseCallback<String> responseCallback){
        HttpDelete deleteRequest = new HttpDelete(ServerConnection.serverURL + "/user/" + id);
        deleteRequest.setHeader("authorization", token);

        this.connection.execute(deleteRequest, new ResponseParser() {
            public void payload(String message) {
                responseCallback.success(message);
            }

            public void error(int status) {
                responseCallback.error(status);

            }
        });
    }

    public void createUser(User user, final ResponseCallback<String> responseCallback){

        HttpPost postRequest = new HttpPost(ServerConnection.serverURL + "/user");

        String userS = this.gson.toJson(user);

        String userC = Crypter.encryptDecryptXOR(userS);

        try{

            StringEntity userString = new StringEntity(userC);

            postRequest.setEntity(userString);
            postRequest.setHeader("Content-Type", "application/json");

            this.connection.execute(postRequest, new ResponseParser() {
                public void payload(String json) {
                    responseCallback.success(json);
                }

                public void error(int status) {
                    responseCallback.error(status);
                }
            });

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

    public static void logOut(){
        //AuthService.clear();
    }

    /*public static User current(){
        //return AuthService.getAccessToken().getUser();
    }*/
}
