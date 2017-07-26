package nuris.epam.dao;

import nuris.epam.dao.exception.DaoException;
import nuris.epam.entity.Author;
import nuris.epam.entity.Book;

/**
 * Interface, describes additional queries for the author table in the database.
 *
 * @author Kalenov Nurislam
 */
public interface AuthorDao extends Dao<Author>{

    /**
     * The method searches with the Book entity and returns the Author entity.
     *
     * @param book - entity.
     * @return Returns a concrete entity.
     */
    Author findByBook(Book book) throws DaoException;


}