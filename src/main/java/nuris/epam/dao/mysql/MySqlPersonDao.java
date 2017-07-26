package nuris.epam.dao.mysql;

import nuris.epam.dao.BaseDao;
import nuris.epam.dao.PersonDao;
import nuris.epam.dao.exception.DaoException;
import nuris.epam.entity.Customer;
import nuris.epam.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Kalenov Nurislam
 */
public class MySqlPersonDao extends BaseDao implements PersonDao {
    private static final Logger log = LoggerFactory.getLogger(MySqlPersonDao.class);

    private static final String FIND_BY_ID = "SELECT * FROM person WHERE id_person = ?";
    private static final String INSERT = "INSERT INTO person VALUES (id_person,?,?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE person SET first_name = ?,last_name = ?,middle_name = ?,phone = ?,birthday = ?,address = ?,id_city = ? WHERE id_person = ?";
    private static final String DELETE = "DELETE FROM person WHERE id_person = ?";
    private static final String FIND_BY_CUSTOMER = "SELECT person.id_person,person.first_name,person.last_name,person.middle_name,person.phone,person.birthday,person.address FROM person JOIN customer ON customer.id_person  = person.id_person WHERE customer.id_customer = ?";

    @Override
    public Person insert(Person item) throws DaoException {
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
                statementPerson(statement, item).executeUpdate();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    resultSet.next();
                    item.setId(resultSet.getInt(1));
                }
            }
            log.debug("Create the person entity where id = {}", item.getId());
        } catch (SQLException e) {
            log.warn("Can't insert {} where value equals : {} ", this.getClass().getSimpleName(), item, e);
            throw new DaoException("can't insert " + item, e);
        }
        return item;
    }

    @Override
    public Person findById(int id) throws DaoException {
        Person person = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        person = itemPerson(person, resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Can't find the person entity where id equals : {} ", id, e);
            throw new DaoException("can't find by id ", e);
        }
        return person;
    }

    @Override
    public void update(Person item) throws DaoException {
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(UPDATE)) {
                statementPerson(statement, item);
                statement.setInt(8, item.getId());
                statement.executeUpdate();
            }
            log.debug("Update the person entity where id = {} : ", item.getId());
        } catch (SQLException e) {
            log.warn("Can't update the person entity where id equals : {}", item.getId());
            throw new DaoException("can't update " + item, e);
        }
    }

    @Override
    public void delete(Person item) throws DaoException {
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(DELETE)) {
                statement.setInt(1, item.getId());
                statement.executeUpdate();
            }
            log.debug("Delete the person entity where id = {} : ", item.getId());
        } catch (SQLException e) {
            log.warn("Can't delete the person entity where id equals : {} ", item.getId(), e);
            throw new DaoException("can't delete  " + item, e);
        }
    }

    @Override
    public Person findByCustomer(Customer customer) throws DaoException {
        Person person = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_CUSTOMER)) {
                statement.setInt(1, customer.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        person = itemPerson(person, resultSet);

                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Can't find person entity by customer where id equals : {} ", customer.getId(), e);
            throw new DaoException("can't find by customer ", e);
        }
        return person;
    }

    private PreparedStatement statementPerson(PreparedStatement statement, Person item) throws SQLException {
        statement.setString(1, item.getFirstName());
        statement.setString(2, item.getLastName());
        statement.setString(3, item.getMiddleName());
        statement.setString(4, item.getPhone());
        statement.setDate(5, item.getBirthday());
        statement.setString(6, item.getAddress());
        statement.setInt(7, item.getCity().getId());
        return statement;
    }

    private Person itemPerson(Person person, ResultSet resultSet) throws SQLException {
        person = new Person();
        person.setId(resultSet.getInt(1));
        person.setFirstName(resultSet.getString(2));
        person.setLastName(resultSet.getString(3));
        person.setMiddleName(resultSet.getString(4));
        person.setPhone(resultSet.getString(5));
        person.setBirthday(resultSet.getDate(6));
        person.setAddress(resultSet.getNString(7));
        return person;
    }
}
