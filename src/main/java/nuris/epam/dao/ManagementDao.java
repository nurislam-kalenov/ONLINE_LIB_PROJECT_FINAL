package nuris.epam.dao;

import nuris.epam.dao.exception.DaoException;
import nuris.epam.entity.Management;

import java.util.List;

/**
 * Interface, describes additional queries for the management table in the database.
 *
 * @author Kalenov Nurislam
 */
public interface ManagementDao extends Dao<Management> {

    /**
     * Method, returns the nth number of the Management entity, taking into account its state (active, inactive).
     *
     * @param start    - start the field in the table in the database
     * @param count    - the number of fields in the database to load.
     * @param isActive - State of entities, may be active or inactive
     * @return Returns a specific number of books
     */
     List<Management> getListManagement(int start, int count, boolean isActive) throws DaoException;


    /**
     * Метод, прелоставляет общее количество сущностей Management c учетом их состаяние(активный, неактивный).
     *
     * @param isActive - State of entity Management
     * @return Returns the entity list Management
     */
     int getManagementCount(boolean isActive) throws DaoException;
}
