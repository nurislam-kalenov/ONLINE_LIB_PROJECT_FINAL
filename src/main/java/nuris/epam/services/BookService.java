package nuris.epam.services;

import nuris.epam.dao.AuthorDao;
import nuris.epam.dao.BookDao;
import nuris.epam.dao.BookInfoDao;
import nuris.epam.dao.GenreDao;
import nuris.epam.dao.exception.DaoException;
import nuris.epam.dao.manager.DaoFactory;
import nuris.epam.entity.Author;
import nuris.epam.entity.Book;
import nuris.epam.entity.BookInfo;
import nuris.epam.entity.Genre;
import nuris.epam.services.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Class - service, perform all manipulations and transactions associated with the book.
 *
 * @author Kalenov Nurislam
 */
public class BookService {
    private static final Logger log = LoggerFactory.getLogger(BookService.class);

    /**
     * Method, performs book registration.
     *
     * @param bookInfo - book , witch need to register.
     */
    public void registerBook(BookInfo bookInfo) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                AuthorDao authorDao = (AuthorDao) daoFactory.getDao(daoFactory.typeDao().getAuthorDao());
                BookDao bookDao = (BookDao) daoFactory.getDao(daoFactory.typeDao().getBookDao());
                BookInfoDao bookInfoDao = (BookInfoDao) daoFactory.getDao(daoFactory.typeDao().getBookInfoDao());

                daoFactory.startTransaction();
                log.debug("Starting registration new bookInfo by id: {}", bookInfo.getId());
                authorDao.insert(bookInfo.getBook().getAuthor());
                bookDao.insert(bookInfo.getBook());
                bookInfoDao.insert(bookInfo);
                daoFactory.commitTransaction();
                log.info("Register a book where bookInfo id equals :{}", bookInfo.getId());
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                log.info("Can't register book where bookInfo id equals: {} ", bookInfo.getId(), e);
                throw new ServiceException("Can't register book", e);
            }
        }

    }

    /**
     * Method, performs search bookInfo by bookInfo id.
     *
     * @param id - bookInfo id
     * @return Specific entity bookInfo
     */
    public BookInfo findBookInfoById(int id) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                log.debug("Find bookInfo by id: {}", id);

                BookInfoDao bookInfoDao = (BookInfoDao) daoFactory.getDao(daoFactory.typeDao().getBookInfoDao());
                return bookInfoDao.findById(id);
            } catch (DaoException e) {
                log.warn("can't find by id book{}", id, e);
                throw new ServiceException("can't find by id book", e);
            }
        }
    }

    /**
     * Method, performs search bookInfo by book id.
     *
     * @param id - book id
     * @return Specific entity bookInfo
     */
    public BookInfo findBookInfoByBookId(int id) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                log.debug("Find bookInfo by book id: {}", id);

                BookInfoDao bookInfoDao = (BookInfoDao) daoFactory.getDao(daoFactory.typeDao().getBookInfoDao());
                BookDao bookDao = (BookDao) daoFactory.getDao(daoFactory.typeDao().getBookDao());

                BookInfo bookInfo = bookInfoDao.findByBook(id);
                Book book = null;

                if(bookInfo!=null) {
                     book = bookDao.findByBookInfo(bookInfo);
                }else{
                    log.warn("Can't find by id book{}", id);
                    throw new ServiceException("Can't find by id book");
                }

                fillBook(book);
                bookInfo.setBook(book);
                return bookInfo;
            } catch (DaoException e) {
                log.warn("Can't find by id book{}", id, e);
                throw new ServiceException("Can't find by id book", e);
            }
        }
    }

    /**
     * Method, performs delete bookInfo by bookInfo id.
     *
     * @param bookInfo - entity must contain an bookInfo id
     */
    public void deleteBook(BookInfo bookInfo) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                log.debug("Start delete a book where bookInfo id equals :{}", bookInfo.getId());

                BookInfoDao bookInfoDao = (BookInfoDao) daoFactory.getDao(daoFactory.typeDao().getBookInfoDao());
                BookDao bookDao = (BookDao) daoFactory.getDao(daoFactory.typeDao().getBookDao());
                AuthorDao authorDao = (AuthorDao) daoFactory.getDao(daoFactory.typeDao().getAuthorDao());
                Book book = bookDao.findByBookInfo(bookInfo);
                Author author = authorDao.findByBook(book);

                daoFactory.startTransaction();
                bookInfoDao.delete(bookInfo);
                bookDao.delete(book);
                authorDao.delete(author);
                daoFactory.commitTransaction();

                log.info("Delete a book where bookInfo id equals :{}", bookInfo.getId());
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                log.warn("Can't delete book where bookInfo id equals: {} ", bookInfo.getId(), e);
                throw new ServiceException("can't delete book", e);
            }
        }
    }

    /**
     * Method, performs update book by book id
     *
     * @param bookInfo - entity must contain an book id
     */
    public void updateBook(BookInfo bookInfo) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                log.debug("Start update a book where bookInfo id equals :{}", bookInfo.getId());

                BookInfoDao bookInfoDao = (BookInfoDao) daoFactory.getDao(daoFactory.typeDao().getBookInfoDao());
                BookDao bookDao = (BookDao) daoFactory.getDao(daoFactory.typeDao().getBookDao());
                AuthorDao authorDao = (AuthorDao) daoFactory.getDao(daoFactory.typeDao().getAuthorDao());

                Author author = bookInfo.getBook().getAuthor();
                Book book = bookInfo.getBook();

                daoFactory.startTransaction();
                authorDao.update(author);
                bookDao.update(book);
                bookInfoDao.update(bookInfo);
                daoFactory.commitTransaction();

                log.info("Update a book where bookInfo id equals :{}", bookInfo.getId());
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                log.warn("Can't update book where bookInfo id equals: {} ", bookInfo.getId(), e);
                throw new ServiceException("Can't update book", e);
            }
        }

    }

    /**
     * Method, performs update bookInfo by bookInfo id
     *
     * @param bookInfo - entity must contain an bookInfo id
     */
    public void updateBookInfo(BookInfo bookInfo) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                log.debug("Start update a bookInfo where bookInfo id equals :{}", bookInfo.getId());

                BookInfoDao bookInfoDao = (BookInfoDao) daoFactory.getDao(daoFactory.typeDao().getBookInfoDao());
                BookDao bookDao = (BookDao) daoFactory.getDao(daoFactory.typeDao().getBookDao());
                Book book = bookDao.findByBookInfo(bookInfo);
                bookInfo.setBook(book);
                bookInfoDao.update(bookInfo);

                log.info("Update a bookInfo where bookInfo id equals :{}", bookInfo.getId());
            } catch (DaoException e) {
                log.warn("Can't update bookInfo  where bookInfo id equals: {} ", bookInfo.getId(), e);
                throw new ServiceException("can't update book", e);
            }
        }
    }

    /**
     * Method, provides the full list of genres
     *
     * @return Return a list of genres
     */
    public List<Genre> getAllGenre() throws ServiceException {
        List<Genre> list;
        try {
            log.debug("Get all genre list transaction from BookService");

            try (DaoFactory daoFactory = new DaoFactory()) {
                GenreDao genreDao = (GenreDao) daoFactory.getDao(daoFactory.typeDao().getGenreDao());
                list = genreDao.getAllGenre();
                return list;
            }

        } catch (DaoException e) {
            log.warn("Can't get all genre list transaction from BookService", e);
            throw new ServiceException("can't get all genre", e);
        }
    }

    /**
     * Method, provides the number of books found for a specific genre
     *
     * @param genre - specific genre
     * @return Return book count
     */
    public int getBookCountByGenre(Genre genre) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                log.debug("get book count by genre where genre id equals {} ", genre.getId());

                BookDao bookDao = (BookDao) daoFactory.getDao(daoFactory.typeDao().getBookDao());
                return bookDao.getBookCountByGenre(genre);
            } catch (DaoException e) {
                log.warn("Can't get book count by genre where genre id equals {} ", genre.getId(), e);
                throw new ServiceException("can't get count book", e);
            }
        }
    }

    /**
     * Method, provides a list of books found by a specific name
     *
     * @param name - specific name
     * @return Return a list of book
     */
    public List<Book> getBookByName(String name) throws ServiceException {
        List<Book> books;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                log.debug("Get book entity by name where book name equals {} ", name);

                BookDao bookDao = (BookDao) daoFactory.getDao(daoFactory.typeDao().getBookDao());
                books = bookDao.findByName(name);
                return books;
            } catch (DaoException e) {
                log.warn("Can't get book entity by name where book name equals {} ", name, e);
                throw new ServiceException("Can't find book by name", e);
            }
        }
    }


    /**
     * Method, provide a list of books for a specific genre that are in a certain range of rows in the table     *
     *
     * @param genre - specific genre
     * @param start - the row from which you must begin
     * @param end   - the row from which you must finish
     * @return Return a list of book
     */
    public List<Book> getListBook(Genre genre, int start, int end) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                BookDao bookDao = (BookDao) daoFactory.getDao(daoFactory.typeDao().getBookDao());
                List<Book> list = bookDao.getLimitBookByGenre(genre, start, end);
                for (Book book : list) {
                    fillBook(book);
                }
                return list;
            } catch (DaoException e) {
                log.warn("Can't get list book by genre id {} where range page {} to {} ", genre.getId(), start, end, e);
                throw new ServiceException("can't get list by genre book", e);
            }
        }
    }

    /**
     * Method, provides a c book found by a specific isbn
     *
     * @param isbn - specific isbn for book
     * @return Specific entity book
     */
    private Book getBookByIsbn(String isbn) throws ServiceException {
        Book book;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                log.debug("Get book entity by isbn where isbn equals {} ", isbn);

                BookDao bookDao = (BookDao) daoFactory.getDao(daoFactory.typeDao().getBookDao());
                book = bookDao.findByIsbn(isbn);
                return book;

            } catch (DaoException e) {
                log.warn("Can't get book entity by isbn where isbn equals {} ", isbn, e);
                throw new ServiceException("can't find book by name", e);
            }
        }
    }

    /**
     * Method, checks for code availability
     *
     * @param isbn - books isbn
     * @return Return accessibility or inaccessibility of the book
     */
    public boolean isBookIsbnAvailable(String isbn) throws ServiceException {
        return getBookByIsbn(isbn) != null;
    }

    /**
     * Method, fill the book entity of the data associated with book
     *
     * @param book - entity
     */
    private void fillBook(Book book) throws ServiceException {
        try {
            if (book != null) {
                log.debug("Fill book with information where book id equals {} ", book.getId());
                try (DaoFactory daoFactory = new DaoFactory()) {
                    AuthorDao authorDao = (AuthorDao) daoFactory.getDao(daoFactory.typeDao().getAuthorDao());
                    GenreDao genreDao = (GenreDao) daoFactory.getDao(daoFactory.typeDao().getGenreDao());
                    book.setAuthor(authorDao.findByBook(book));
                    book.setGenre(genreDao.findByBook(book));
                }
            }
        } catch (DaoException e) {
            log.warn("Can't fill book with information where book id equals {} ", book.getId(), e);
            throw new ServiceException("can't fill book", e);
        }
    }
}
