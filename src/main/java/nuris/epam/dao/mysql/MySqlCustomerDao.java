package nuris.epam.dao.mysql;

import nuris.epam.dao.BaseDao;
import nuris.epam.dao.CustomerDao;
import nuris.epam.dao.exception.DaoException;
import nuris.epam.entity.Customer;
import nuris.epam.entity.Management;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kalenov Nurislam
 */
public class MySqlCustomerDao extends BaseDao implements CustomerDao {
    private static final Logger log = LoggerFactory.getLogger(MySqlCustomerDao.class);

    private static final String FIND_BY_ID = "SELECT * FROM customer WHERE id_customer = ?";
    private static final String INSERT = "INSERT INTO customer VALUES (id_customer,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE customer SET register_date = ?,password = ?,email = ?,id_person = ?,id_role = ? WHERE id_customer = ?";
    private static final String DELETE = "DELETE FROM customer  WHERE id_customer = ?";
    private static final String COUNT_CUSTOMER = "SELECT COUNT(*) FROM customer";
    private static final String FIND_BY_LOGIN = "SELECT * FROM customer  WHERE email = ?";
    private static final String FIND_BY_LOGIN_PASSWORD = "SELECT * FROM customer  WHERE email = ?  AND password = ?";
    private static final String FIND_BY_MANAGEMENT = "SELECT customer.id_customer ,customer.email FROM customer JOIN transaction ON customer.id_customer  = transaction.id_customer JOIN management ON management.id_transaction  = transaction.id_transaction WHERE management.id_management = ?";
    private static final String LIMIT_CUSTOMER = "SELECT * FROM customer limit ?,?";

    @Override
    public Customer insert(Customer item) throws DaoException {
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
                statementCustomer(statement, item);
                statement.executeUpdate();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    resultSet.next();
                    item.setId(resultSet.getInt(1));
                }
            }
            log.debug("Create the customer entity where id = {} ", item.getId());
        } catch (SQLException e) {
            log.warn("Can't insert {} where value equals : {}", item, e);
            throw new DaoException("can't insert customer = {}" + item, e);
        }
        return item;
    }

    @Override
    public Customer findById(int id) throws DaoException {
        Customer customer = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        customer = itemCustomer(customer, resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Can't find the customer entity where id equals : {} ", id, e);
            throw new DaoException("can't find by id ", e);
        }
        return customer;
    }

    @Override
    public void update(Customer item) throws DaoException {
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(UPDATE)) {
                statementCustomer(statement, item);
                statement.setInt(6, item.getId());
                statement.executeUpdate();
            }
            log.debug("Update the customer entity where id = {} : ", item.getId());
        } catch (SQLException e) {
            log.warn("Can't update the customer entity where id equals : {}", item.getId(), e);
            throw new DaoException("can't update customer = {} " + item, e);
        }
    }

    @Override
    public void delete(Customer item) throws DaoException {
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(DELETE)) {
                statement.setInt(1, item.getId());
                statement.executeUpdate();
            }
            log.debug("Delete the customer entity where id = {} : ", item.getId());
        } catch (SQLException e) {
            log.warn("Can't delete the customer entity where id equals : {}", item.getId(), e);
            throw new DaoException("can't delete customer = {}" + item, e);
        }
    }

    @Override
    public int getCustomerCount() throws DaoException {
        int count = 0;
        try (Statement statement = getConnection().createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(COUNT_CUSTOMER)) {
                while (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            log.warn("Can't get customer count {} " + this.getClass().getSimpleName(), e);
            throw new DaoException("can't get count customer ", e);
        }
        return count;
    }


    @Override
    public Customer getCustomer(String login) throws DaoException {
        Customer customer = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_LOGIN)) {
                statement.setString(1, login);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        customer = itemCustomer(customer, resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Can't find the customer entity by login {} ", login, e);
            throw new DaoException("can't get by login ", e);
        }
        return customer;
    }

    @Override
    public Customer getCustomer(String login, String password) throws DaoException {
        Customer customer = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_LOGIN_PASSWORD)) {
                statement.setString(1, login);
                statement.setString(2, password);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        customer = itemCustomer(customer, resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Can't find the customer entity by login{} and password **** ", login, e);
            throw new DaoException("can't get by login and password ", e);
        }
        return customer;
    }

    @Override
    public Customer findByManagement(Management management) throws DaoException {
        Customer customer = new Customer();
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_MANAGEMENT)) {
                statement.setInt(1, management.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        customer.setId(resultSet.getInt(1));
                        customer.setEmail(resultSet.getString(2));
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Can't find the customer entity by management where management id equals : {} ", management.getId(), e);
            throw new DaoException("can't find by management ", e);
        }
        return customer;
    }

    @Override
    public List<Customer> getLimitCustomers(int start, int count) throws DaoException {
        List<Customer> list = new ArrayList<>();
        Customer customer = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(LIMIT_CUSTOMER)) {
                statement.setInt(1, ((start - 1) * count));
                statement.setInt(2, count);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        customer = itemCustomer(customer, resultSet);
                        list.add(customer);
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Can't get customer list by range {} to {} ", start, count, e);
            throw new DaoException("can't get list of customer ", e);
        }
        return list;
    }

    private PreparedStatement statementCustomer(PreparedStatement statement, Customer item) throws SQLException {
        statement.setDate(1, item.getRegisterDate());
        statement.setString(2, item.getPassword());
        statement.setString(3, item.getEmail());
        statement.setInt(4, item.getPerson().getId());
        statement.setInt(5, item.getCustomerRole().getId());
        return statement;
    }

    private Customer itemCustomer(Customer customer, ResultSet resultSet) throws SQLException {
        customer = new Customer();
        customer.setId(resultSet.getInt(1));
        customer.setRegisterDate(resultSet.getDate(2));
        customer.setPassword(resultSet.getString(3));
        customer.setEmail(resultSet.getString(4));
        return customer;
    }
}
