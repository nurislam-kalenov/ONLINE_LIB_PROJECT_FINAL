package nuris.epam.dao;

import nuris.epam.dao.exception.DaoException;
import nuris.epam.entity.BaseEntity;

/**
 * Interface, describes CRUD operations for SQL queries.
 *
 * @author Kalenov Nurislam
 */
public interface Dao<T extends BaseEntity> {
    /**
     * Request to create a field.
     *
     * @param item - Child Classes BaseEntity
     * @return Returns the child class of the abstract class BaseEntity.
     */
    T insert(T item) throws DaoException;

    /**
     * Request to read the field.
     *
     * @param id - unique field value
     * @return Returns the child class of the abstract class BaseEntity.
     */
    T findById(int id) throws DaoException;

    /**
     * Update request field.
     *
     * @param item - child classes BaseEntity
     */
    void update(T item) throws DaoException;

    /**
     * Request to delete the field.
     *
     * @param item - child classes BaseEntity
     */
    void delete(T item) throws DaoException;

}
