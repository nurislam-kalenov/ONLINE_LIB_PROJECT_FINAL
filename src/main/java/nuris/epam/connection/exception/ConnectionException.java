package nuris.epam.connection.exception;

/**
 * @author Kalenov Nurislam
 */
public class ConnectionException extends Exception {
    public ConnectionException(String message, Exception cause) {
        super(message, cause);
    }
}
