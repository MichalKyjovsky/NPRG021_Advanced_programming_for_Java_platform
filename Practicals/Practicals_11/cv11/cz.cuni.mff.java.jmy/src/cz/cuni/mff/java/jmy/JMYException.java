package cz.cuni.mff.java.jmy;

public class JMYException extends Exception {
    public JMYException() {
    }

    public JMYException(String message) {
        super(message);
    }

    public JMYException(String message, Throwable cause) {
        super(message, cause);
    }

    public JMYException(Throwable cause) {
        super(cause);
    }

    public JMYException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
