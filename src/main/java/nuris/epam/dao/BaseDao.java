package nuris.epam.dao;

import java.sql.Connection;

/**
 * An abstract class that allows specific Dao objects to connect to the database, for subsequent work with the database.
 *
 * @author Kalenov Nurislam
 */
public abstract class BaseDao{

    private Connection connection;

    /**
     * The method provides a connection to the database.
     *
     * @return Return the connection from the database.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * A method that allows the Dao to connect to the database, for further work with the database.
     *
     * @param connection - Connection to the database.
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
