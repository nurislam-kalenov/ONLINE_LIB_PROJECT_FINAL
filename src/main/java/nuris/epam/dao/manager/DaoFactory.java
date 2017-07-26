package nuris.epam.dao.manager;

import nuris.epam.connection.ConnectionPool;
import nuris.epam.connection.exception.ConnectionException;
import nuris.epam.dao.BaseDao;
import nuris.epam.dao.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The class is intended for managing connections, namely the distribution and closing of connections.
 * Also creates instances of Dao objects and puts a connection in them.
 *
 * @author Kalenov Nurislam
 */
public class DaoFactory implements AutoCloseable {
    private static final Logger log = LoggerFactory.getLogger(DaoFactory.class);
    /**
     * Field - contains a connection pool
     */
    private ConnectionPool connectionPool;
    /**
     * Field - Connect to the database.
     */
    private Connection connection;
    /**
     * Field - Returns the object of the dao, depending on the type of database.
     */
    private TypeDao typeDao;

    public DaoFactory() {
        connectionPool = ConnectionPool.getInstance();
        typeDao = TypeDao.getInstance();
        try {
            connection = connectionPool.getConnection();
        } catch (ConnectionException e) {
            log.error("Can't get connection from connection pool", e);
        }
    }

    /**
     * Creates a new Dao object, also gives it a connection to the database.
     *
     * @param clazz - Object type. (Reflection)
     * @return Dao object.
     */
    public <T extends BaseDao> T getDao(Class<T> clazz) throws DaoException {
        T t;
        try {
            log.debug("Taken connect");

            t = clazz.newInstance();
            t.setConnection(connection);

        } catch (InstantiationException | IllegalAccessException e) {
            log.error("Can't create or give new DAO object", e);
            throw new DaoException("Can't create or give new DAO object", e);
        }
        return t;
    }

    /**
     * Puts the connection to the connection pool.
     */
    private void returnConnect() {
        connectionPool.returnConnection(connection);
    }

    /**
     * @return Returns the Tao object, depending on the type of database.
     */
    public TypeDao typeDao() {
        return typeDao;
    }

    /**
     * The method allows you to start a transaction.
     */
    public void startTransaction() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            log.error("Can't starting date transaction", e);
        }
    }

    /**
     * The method allows you to perform a transaction.
     **/
    public void commitTransaction() {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            log.error("Can't committing date transaction", e);
        }
    }

    /**
     * The method to cancel the transaction.
     */
    public void rollbackTransaction() {
        try {
            log.debug("Call rollback transaction");
            connection.rollback();
        } catch (SQLException e) {
            log.error("Can't rollback data transaction", e);
        }
    }

    /**
     * The method returns a connection back to the pool of connotes
     */
    @Override
    public void close() {
        returnConnect();
    }
}
