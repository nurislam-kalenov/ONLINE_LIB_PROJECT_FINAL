package nuris.epam.dao;

import nuris.epam.dao.exception.DaoException;
import nuris.epam.entity.Customer;
import nuris.epam.entity.Management;

import java.util.List;

/**
 * Interface, describes additional queries for the customer table in the database.
 *
 * @author Kalenov Nurislam
 */
public interface CustomerDao extends Dao<Customer> {

    /**
     * Method, determines the number of users in the table.
     *
     * @return Returns a specific number of users
     */
    int getCustomerCount() throws DaoException;

    /**
     * Method, searches for a user by a unique name (login).
     *
     * @param login - unique name (login) of the user.
     * @return Returns a particular user.
     */
    Customer getCustomer(String login) throws DaoException;

    /**
     * Method, searches for a user by a unique name (login) and password.
     *
     * @param login    - unique name (login) of the user.
     * @param password - user password.
     * @return Returns a specific user.
     */
    Customer getCustomer(String login, String password) throws DaoException;

    /**
     * Method, searches for a user based on the entity Management.
     *
     * @param management - entity
     * @return Returns a specific user.
     */
    Customer findByManagement(Management management) throws DaoException;

    /**
     * The method returns the n-th number of users.
     *
     * @param start - start the field in the table in the database
     * @param count - the number of fields in the database to load.
     * @return Returns a specific number of users
     */
    List<Customer> getLimitCustomers(int start, int count) throws DaoException;


}
