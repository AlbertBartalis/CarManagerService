package ExceptieProprie;

public class ExceptiiClient extends RuntimeException{


    public ExceptiiClient(String message) {
        super(message);
    }

    public ExceptiiClient(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptiiClient(Throwable cause) {
        super(cause);
    }

    protected ExceptiiClient(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
