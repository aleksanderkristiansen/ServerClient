package company.sdk.connection;

/**
 * Created by aleksanderkristiansen on 20/11/2016.
 */
public interface ResponseCallback<T> {

    void success(T data);
    void error(int status);
}
