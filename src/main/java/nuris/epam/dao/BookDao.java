package nuris.epam.dao;

import nuris.epam.dao.exception.DaoException;
import nuris.epam.entity.Book;
import nuris.epam.entity.BookInfo;
import nuris.epam.entity.Genre;

import java.util.List;

/**
 * Interface, describes additional queries for the book table in the database.
 *
 * @author Kalenov Nurislam
 */
public interface BookDao extends Dao<Book> {
    /**
     * Method, determines the number of books with the account of the genre in the table.
     *
     * @param genre - entity.
     * @return Returning a specific number of books
     */
    int getBookCountByGenre(Genre genre) throws DaoException;

    /**
     * Method, returns the n-th number taking into account the genre of books.
     *
     * @param genre - entity.
     * @param start - start the field in the table in the database
     * @param count - the number of fields in the database that it is unavailable to upload.
     * @return Returns a specific number of books
     */
    List<Book> getLimitBookByGenre(Genre genre, int start, int count) throws DaoException;

    /**
     * Method, provides a list of books with the appropriate title.
     *
     * @param name - name of the book
     * @return Returns a specific number of books
     */
    List<Book> findByName(String name) throws DaoException;

    /**
     * A method for finding a book with the BookInfo entity.
     *
     * @param bookInfo - entity.
     * @return Returns a specific number of books
     */
    Book findByBookInfo(BookInfo bookInfo) throws DaoException;

    /**
     * A method for finding a book by using a unique book code.
     *
     * @param isbn - unique code book.
     * @return Returns a specific book
     */
    Book findByIsbn(String isbn) throws DaoException;


}
