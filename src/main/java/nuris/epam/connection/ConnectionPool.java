package nuris.epam.connection;

import nuris.epam.connection.exception.ConnectionException;
import nuris.epam.connection.exception.ResourcesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The class stores a list of initialized connections to the database.
 *
 * @author Kalenov Nurislam
 */
public class ConnectionPool {
    private static final Logger log = LoggerFactory.getLogger(ConnectionPool.class);

    /**
     * Field - user from the database.
     */
    private String user;
    /**
     * Field - the password from the database.
     */
    private String password;
    /**
     * Field - the path to the driver.
     */
    private String driver;
    /**
     * Field - the path to the database.
     */
    private String url;
    /**
     * Field - type of database.
     */
    private String type;
    /**
     * Field - the number of initialized connections.
     */
    private int maxActive;
    /**
     * Field - time to wait for the user to release the connection.
     */
    private int maxWait;
    /**
     * Field - a list for storing initialized connections.
     */
    private ResourcesQueue<Connection> connections = null;

    private static ConnectionPool connectionPool;

    private ConnectionPool() {
        init();
        log.info("Initialize all connection in connection pool");
    }


    private void init() {
        try {
            loadProperties();
            connections = new ResourcesQueue<>(maxActive, maxWait);
            while (connections.size() < maxActive) {
                try {
                    Class.forName(driver);
                } catch (ClassNotFoundException e) {
                    log.error("Can't find driver for JDBC MySql", e);
                }
                Connection connection = DriverManager.getConnection(url, user, password);
                connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                connections.addResource(connection);
            }
        } catch (SQLException e) {
            log.error("Can't get connection with DB");
        }
    }


    private void loadProperties() {
        Properties properties = new Properties();
        try {
            properties.load(ConnectionPool.class.getResourceAsStream("/db.properties"));
            url = properties.getProperty("url");
            type = properties.getProperty("type");
            user = properties.getProperty("username");
            password = properties.getProperty("password");
            driver = properties.getProperty("driverClassName");
            maxWait = Integer.parseInt(properties.getProperty("maxWait"));
            maxActive = Integer.parseInt(properties.getProperty("maxActive"));
        } catch (IOException e) {
            log.error("Not found properties file with connecting settings", e);
        }
    }


    public Connection getConnection() throws ConnectionException {
        try {
            log.debug("Free connection size when take : {}", connections.size());
            return connections.takeResource();
        } catch (ResourcesException e) {
            log.error("Error in a resources , don't available connect", e);
            throw new ConnectionException("Error in a resources , don't available connect", e);
        }
    }

    /**
     * Returns the connection to the connection pool list.
     */
    public void returnConnection(Connection connection) {
        connections.returnResource(connection);
        log.debug("Connection size when return connect : {}", connections.size());

    }


    public static synchronized ConnectionPool getInstance() {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
        }
        return connectionPool;
    }


    public void closeAllConnections() {
        log.info("Close all connection in connection pool");
        for (Connection connection : connections.getResources()) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("Can't close all connection in connection pool", e);
            }
        }
    }

    public String getType() {
        return type;
    }

    public int size() {
        return connections.size();
    }
}
