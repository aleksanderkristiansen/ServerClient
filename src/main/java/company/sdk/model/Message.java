package company.sdk.model;

/**
 * Created by aleksanderkristiansen on 20/11/2016.
 */
public class Message {

    private String message;

    public Message(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
