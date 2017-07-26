package nuris.epam.dao.mysql;

import nuris.epam.dao.BaseDao;
import nuris.epam.dao.CustomerRoleDao;
import nuris.epam.dao.exception.DaoException;
import nuris.epam.entity.Customer;
import nuris.epam.entity.CustomerRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Kalenov Nurislam
 */
public class MySqlCustomerRoleDao extends BaseDao implements CustomerRoleDao {
    private static final Logger log = LoggerFactory.getLogger(MySqlCustomerRoleDao.class);

    private static final String FIND_BY_CUSTOMER = "SELECT role.id_role,role.name FROM role JOIN customer ON customer.id_role  = role.id_role WHERE customer.id_customer = ?";
    private static final String FIND_BY_NAME_ROLE = "SELECT * FROM role  WHERE name = ?";


    @Override
    public CustomerRole findByCustomer(Customer customer) throws DaoException {
        CustomerRole customerRole = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_CUSTOMER)) {
                statement.setInt(1, customer.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        customerRole = itemRole(customerRole, resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Can't find customerRole entity by customer where customer id equals : {}", customer.getId() ,e);
            throw new DaoException("can't find by customer ", e);
        }
        return customerRole;
    }

    @Override
    public CustomerRole findRoleByName(String nameRole) throws DaoException {
        CustomerRole customerRole = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_NAME_ROLE)) {
                statement.setString(1, nameRole);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        customerRole = itemRole(customerRole, resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Can't find customerRole entity by name where name equals : {} ", nameRole ,e);
            throw new DaoException("can't find by role ", e);
        }
        return customerRole;
    }

    private CustomerRole itemRole(CustomerRole customerRole, ResultSet resultSet) throws SQLException {
        customerRole = new CustomerRole();
        customerRole.setId(resultSet.getInt(1));
        customerRole.setName(resultSet.getString(2));
        return customerRole;
    }

    @Override
    public CustomerRole insert(CustomerRole item) throws DaoException {
        return null;
    }

    @Override
    public CustomerRole findById(int id) throws DaoException {
        return null;
    }

    @Override
    public void update(CustomerRole item) throws DaoException {

    }

    @Override
    public void delete(CustomerRole item) throws DaoException {

    }

}
