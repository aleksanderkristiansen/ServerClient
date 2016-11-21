package company.sdk.connection;

/**
 * Created by aleksanderkristiansen on 20/11/2016.
 */
public interface ResponseParser {

    void payload(String json);
    void error(int status);
}
