package nuris.epam.dao.exception;

/**
 * @author Kalenov Nurislam
 */
public class DaoException extends Exception {

    public DaoException(String message, Exception cause) {
        super(message, cause);
    }
}
