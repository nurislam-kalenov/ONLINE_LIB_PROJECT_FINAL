package nuris.epam.dao;

import nuris.epam.dao.exception.DaoException;
import nuris.epam.entity.Customer;
import nuris.epam.entity.CustomerRole;

/**
 * Interface, describes additional queries for the customerRole table in the database.
 *
 * @author Kalenov Nurislam
 */
public interface CustomerRoleDao extends Dao<CustomerRole> {
    /**
     * The method looks for a role with the Customer entity.
     *
     * @param customer - entity
     * @return Returns a specific role.
     */
    CustomerRole findByCustomer(Customer customer) throws DaoException;

    /**
     * Method, looking for a role by the name of the role.
     *
     * @param nameRole - name of the role.
     * @return Returns a specific role.
     */
    CustomerRole findRoleByName(String nameRole) throws DaoException;

}
