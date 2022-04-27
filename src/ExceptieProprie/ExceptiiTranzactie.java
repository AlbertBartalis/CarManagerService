package ExceptieProprie;

public class ExceptiiTranzactie extends  RuntimeException{
    public ExceptiiTranzactie() {
        super();
    }

    public ExceptiiTranzactie(String message) {
        super(message);
    }

    public ExceptiiTranzactie(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptiiTranzactie(Throwable cause) {
        super(cause);
    }

    protected ExceptiiTranzactie(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
