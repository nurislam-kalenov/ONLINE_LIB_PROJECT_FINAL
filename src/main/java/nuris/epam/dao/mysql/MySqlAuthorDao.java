package nuris.epam.dao.mysql;

import nuris.epam.dao.AuthorDao;
import nuris.epam.dao.BaseDao;
import nuris.epam.dao.exception.DaoException;
import nuris.epam.entity.Author;
import nuris.epam.entity.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Kalenov Nurislam
 */
public class MySqlAuthorDao extends BaseDao implements AuthorDao {
    private static final Logger log = LoggerFactory.getLogger(MySqlAuthorDao.class);

    private static final String FIND_BY_ID = "SELECT * FROM author WHERE id_author = ?";
    private static final String INSERT = "INSERT INTO author VALUES(id_author,?,?,?)";
    private static final String UPDATE = "UPDATE author SET first_name = ?,last_name = ?,middle_name = ? WHERE id_author = ?";
    private static final String DELETE = "DELETE FROM author WHERE id_author = ?";
    private static final String FIND_BY_BOOK = "SELECT author.id_author,author.first_name,author.last_name,author.middle_name FROM author JOIN book ON book.id_author  = author.id_author WHERE book.id_book = ?";

    @Override
    public Author insert(Author item) throws DaoException {
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
                statement(statement, item).executeUpdate();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    resultSet.next();
                    item.setId(resultSet.getInt(1));
                }
            }
            log.debug("Create the author entity where id = {}", item.getId());
        } catch (SQLException e) {
            log.warn("Can't insert where value equals : {} ", item, e);
            throw new DaoException("Can't insert " + item, e);
        }
        return item;
    }

    @Override
    public Author findById(int id) throws DaoException {
        Author author = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        author = itemAuthor(author, resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Can't find the author entity where id equals : {} ", id, e);
            throw new DaoException("Can't find by id  ", e);
        }
        return author;
    }

    @Override
    public void update(Author item) throws DaoException {
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(UPDATE)) {
                statement(statement, item);
                statement.setInt(4, item.getId());
                statement.executeUpdate();
            }
            log.debug("Update the author entity where id = {} : ", item.getId());
        } catch (SQLException e) {
            log.warn("Can't update the author entity where id equals : {} ", item.getId(), e);
            throw new DaoException("can't update " + item, e);
        }
    }

    @Override
    public void delete(Author item) throws DaoException {
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(DELETE)) {
                statement.setInt(1, item.getId());
                statement.executeUpdate();
            }
            log.debug("Delete the author entity where id = {} : ", item.getId());
        } catch (SQLException e) {
            log.warn("Can't delete the author entity where id equals : {} ", item.getId(), e);
            throw new DaoException("can't delete author " + item, e);
        }
    }

    @Override
    public Author findByBook(Book book) throws DaoException {
        Author author = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_BOOK)) {
                statement.setInt(1, book.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        author = itemAuthor(author, resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Can't find the author entity by book where book id equals : {}", book.getId());
            throw new DaoException("can't find by book ", e);
        }
        return author;
    }

    private PreparedStatement statement(PreparedStatement statement, Author item) throws SQLException {
        statement.setString(1, item.getFirstName());
        statement.setString(2, item.getLastName());
        statement.setString(3, item.getMiddleName());
        return statement;
    }

    private Author itemAuthor(Author author, ResultSet resultSet) throws SQLException {
        author = new Author();
        author.setId(resultSet.getInt(1));
        author.setFirstName(resultSet.getString(2));
        author.setLastName(resultSet.getString(3));
        author.setMiddleName(resultSet.getString(4));
        return author;
    }


}
