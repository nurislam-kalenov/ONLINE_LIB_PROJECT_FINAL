package nuris.epam.dao;

import nuris.epam.dao.exception.DaoException;
import nuris.epam.entity.City;
import nuris.epam.entity.Person;

import java.util.List;

/**
 * Interface, describes additional queries for the city table in the database.
 *
 * @author Kalenov Nurislam
 */
public interface CityDao extends Dao<City> {
    /**
     * A method for finding a book with Person.
     *
     * @param person - entity
     * @return Returns the concise City entity.
     */
    City findByPerson(Person person) throws DaoException;

    /**
     * Method, provides a list of all cities.
     *
     * @return Returns the list of cities.
     */
    List<City> getAllCity() throws DaoException;

}
