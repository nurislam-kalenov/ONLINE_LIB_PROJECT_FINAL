package nuris.epam.dao.manager;

import nuris.epam.connection.ConnectionPool;
import nuris.epam.dao.mysql.*;

/**
 * The class serves to initiate the objects, depending on the type of database.
 *
 * @author Kalenov Nurislam
 */
public class TypeDao {
    private static TypeDao typeDao;
    /**
     * Property, provides the name (type) of the database.
     */
    private ConnectionPool connectType;
    /**
     * Name (type) of the database.
     */
    private static final String MYSQL = "mysql";

    private TypeDao() {
        connectType = ConnectionPool.getInstance();
    }

    public Class getAuthorDao() {
        if (connectType.getType().equalsIgnoreCase(MYSQL)) {
            return MySqlAuthorDao.class;
        } else {
            return MySqlAuthorDao.class;
        }
    }

    public Class getGenreDao() {
        if (connectType.getType().equalsIgnoreCase(MYSQL)) {
            return MySqlGenreDao.class;
        } else {
            return MySqlGenreDao.class;
        }
    }

    public Class getBookDao() {
        if (connectType.getType().equalsIgnoreCase(MYSQL)) {
            return MySqlBookDao.class;
        } else {
            return MySqlBookDao.class;
        }
    }

    public Class getCityDao() {
        if (connectType.getType().equalsIgnoreCase(MYSQL)) {
            return MySqlCityDao.class;
        } else {
            return MySqlCityDao.class;
        }
    }

    public Class getPersonDao() {
        if (connectType.getType().equalsIgnoreCase(MYSQL)) {
            return MySqlPersonDao.class;
        } else {
            return MySqlPersonDao.class;
        }
    }

    public Class getCustomerRoleDao() {
        if (connectType.getType().equalsIgnoreCase(MYSQL)) {
            return MySqlCustomerRoleDao.class;
        } else {
            return MySqlCustomerRoleDao.class;
        }
    }

    public Class getCustomerDao() {
        if (connectType.getType().equalsIgnoreCase(MYSQL)) {
            return MySqlCustomerDao.class;
        } else {
            return MySqlCustomerDao.class;
        }
    }

    public Class getBookInfoDao() {
        if (connectType.getType().equalsIgnoreCase(MYSQL)) {
            return MySqlBookInfoDao.class;
        } else {
            return MySqlBookInfoDao.class;
        }
    }

    public Class getTransactionDao() {
        if (connectType.getType().equalsIgnoreCase(MYSQL)) {
            return MySqlTransactionDao.class;
        } else {
            return MySqlTransactionDao.class;
        }
    }

    public Class getManagementDao() {
        if (connectType.getType().equalsIgnoreCase(MYSQL)) {
            return MySqlManagementDao.class;
        } else {
            return MySqlManagementDao.class;
        }
    }

    public static TypeDao getInstance() {
        if (typeDao == null) {
            typeDao = new TypeDao();
        }
        return typeDao;
    }
}
