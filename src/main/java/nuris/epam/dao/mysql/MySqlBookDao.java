package nuris.epam.dao.mysql;

import nuris.epam.dao.BaseDao;
import nuris.epam.dao.BookDao;
import nuris.epam.dao.exception.DaoException;
import nuris.epam.entity.Book;
import nuris.epam.entity.BookInfo;
import nuris.epam.entity.Genre;
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
public class MySqlBookDao extends BaseDao implements BookDao {
    private static final Logger log = LoggerFactory.getLogger(MySqlBookDao.class);

    private static final String FIND_BY_ID = "SELECT * FROM book WHERE id_book = ?";
    private static final String INSERT = "INSERT INTO book VALUES (id_book,?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE book SET name = ?,year = ?,isbn = ?,description = ?,id_genre = ?,id_author = ? WHERE id_book = ?";
    private static final String DELETE = "DELETE FROM book WHERE id_book = ?";
    private static final String COUNT_BOOK_BY_GENRE = "SELECT COUNT(*) FROM book WHERE id_genre = ?";
    private static final String LIMIT_BOOK_BY_GENRE = "select * from book  where id_genre = ? limit ?,? ";
    private static final String FIND_BY_NAME = "SELECT * FROM book  WHERE name = ?";
    private static final String FIND_BY_BOOK = "SELECT book.id_book,book.name,book.year,book.isbn,book.description,book.id_genre ,book.id_author FROM book JOIN book_info ON book_info.id_book  = book.id_book WHERE book_info.id_book_info = ?";
    private static final String FIND_BY_ISBN = "SELECT * FROM book WHERE isbn = ?";

    @Override
    public Book insert(Book item) throws DaoException {
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
                statementBook(statement, item);
                statement.executeUpdate();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    resultSet.next();
                    item.setId(resultSet.getInt(1));
                }
            }
            log.debug("Create the book entity where id = {}", item.getId());
        } catch (SQLException e) {
            log.warn("Can't insert where value equals : {}", item, e);
            throw new DaoException("can't insert " + item, e);
        }
        return item;
    }

    @Override
    public Book findById(int id) throws DaoException {
        Book book = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        book = itemBook(book, resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Can't find the book entity where id equals : {}", id, e);
            throw new DaoException("can't find by id", e);
        }
        return book;
    }

    @Override
    public void update(Book item) throws DaoException {
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(UPDATE)) {
                statementBook(statement, item);
                statement.setInt(7, item.getId());
                statement.executeUpdate();
            }
            log.debug("Update the book entity where id = {} : ", item.getId());
        } catch (SQLException e) {
            log.warn("Can't update the book entity where id equals : {}", item.getId(), e);
            throw new DaoException("can't update " + item, e);
        }
    }

    @Override
    public void delete(Book item) throws DaoException {
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(DELETE)) {
                statement.setInt(1, item.getId());
                statement.executeUpdate();
            }
            log.debug("Delete the book entity where id = {} : ", item.getId());
        } catch (SQLException e) {
            log.warn("Can't delete the book entity where id equals : {}", item.getId(), e);
            throw new DaoException("can't delete book " + item, e);
        }
    }

    @Override
    public int getBookCountByGenre(Genre genre) throws DaoException {
        int count = 0;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(COUNT_BOOK_BY_GENRE)) {
                statement.setInt(1, genre.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        count = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Can't get book count by genre where genre id equals : {} ", genre.getId(), e);
            throw new DaoException("can't count by genre ", e);
        }
        return count;
    }

    @Override
    public List<Book> getLimitBookByGenre(Genre genre, int start, int count) throws DaoException {
        List<Book> list = new ArrayList<>();
        Book book = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(LIMIT_BOOK_BY_GENRE)) {
                statement.setInt(1, genre.getId());
                statement.setInt(2, ((start - 1) * count));
                statement.setInt(3, count);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        book = itemBook(book, resultSet);
                        list.add(book);
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Can't get book list by range {} to {} where book genre equals : {} ", start, count, genre, e);
            throw new DaoException("can't get list of book by genre ", e);
        }
        return list;
    }

    @Override
    public List<Book> findByName(String name) throws DaoException {
        List<Book> list = new ArrayList<>();
        Book book = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_NAME)) {
                statement.setString(1, name);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        book = itemBook(book, resultSet);
                        list.add(book);
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Can't find the book entity by book where book name equals : {} ", name, e);
            throw new DaoException("Can't find by name ", e);
        }
        return list;
    }

    @Override
    public Book findByBookInfo(BookInfo bookInfo) throws DaoException {
        Book book = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_BOOK)) {
                statement.setInt(1, bookInfo.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        book = itemBook(book, resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Can't find the book entity by bookInfo where bookInfo id equals : {} ", bookInfo.getId());
            throw new DaoException("can't find by bookInfo ", e);
        }
        return book;
    }

    @Override
    public Book findByIsbn(String isbn) throws DaoException {
        Book book = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ISBN)) {
                statement.setString(1, isbn);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        book = itemBook(book, resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Can't find the book entity by book where book isbn equals : {} ", isbn, e);
            throw new DaoException("can't find by isbn ", e);
        }
        return book;
    }

    private Book itemBook(Book book, ResultSet resultSet) throws SQLException {
        book = new Book();
        book.setId(resultSet.getInt(1));
        book.setName(resultSet.getString(2));
        book.setDate(resultSet.getDate(3));
        book.setIsbn(resultSet.getString(4));
        book.setDescription(resultSet.getString(5));
        return book;
    }

    private PreparedStatement statementBook(PreparedStatement statement, Book item) throws SQLException {
        statement.setString(1, item.getName());
        statement.setDate(2, item.getDate());
        statement.setString(3, item.getIsbn());
        statement.setString(4, item.getDescription());
        statement.setInt(5, item.getGenre().getId());
        statement.setInt(6, item.getAuthor().getId());
        return statement;
    }

}

