package nuris.epam.dao.mysql;

import nuris.epam.dao.BaseDao;
import nuris.epam.dao.CityDao;
import nuris.epam.dao.exception.DaoException;
import nuris.epam.entity.City;
import nuris.epam.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kalenov Nurislam
 */
public class MySqlCityDao extends BaseDao implements CityDao {
    private static final Logger log = LoggerFactory.getLogger(MySqlCityDao.class);

    private static final String SELECT_ALL = "SELECT * FROM city";
    private static final String FIND_BY_PERSON = "SELECT city.id_city ,city.name FROM city JOIN person ON person.id_city  = city.id_city WHERE person.id_person = ?";

    @Override
    public City insert(City item) throws DaoException {
        return null;
    }

    @Override
    public void update(City item) throws DaoException {

    }

    @Override
    public void delete(City item) throws DaoException {

    }

    @Override
    public City findById(int id) throws DaoException {
        return null;
    }

    @Override
    public List<City> getAllCity() throws DaoException {
        List<City> list = new ArrayList<>();
        City city = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(SELECT_ALL)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        city = itemCity(city, resultSet);
                        list.add(city);
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Can't get all city list {}", e);
            throw new DaoException("can't get all city " + this.getClass().getSimpleName(), e);
        }
        return list;
    }

    @Override
    public City findByPerson(Person person) throws DaoException {
        City city = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_PERSON)) {
                statement.setInt(1, person.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        city = itemCity(city, resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Can't find the city entity by person where person id equals : {}", person.getId(), e);
            throw new DaoException("can't find city by person ", e);
        }
        return city;
    }

    private City itemCity(City city, ResultSet resultSet) throws SQLException {
        city = new City();
        city.setId(resultSet.getInt(1));
        city.setName(resultSet.getString(2));
        return city;
    }
}
