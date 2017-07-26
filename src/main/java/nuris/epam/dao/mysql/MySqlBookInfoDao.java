package nuris.epam.dao.mysql;

import nuris.epam.dao.BaseDao;
import nuris.epam.dao.BookInfoDao;
import nuris.epam.dao.exception.DaoException;
import nuris.epam.entity.BookInfo;
import nuris.epam.entity.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Kalenov Nurislam
 */
public class MySqlBookInfoDao extends BaseDao implements BookInfoDao {
    private static final Logger log = LoggerFactory.getLogger(MySqlBookInfoDao.class);

    private static final String FIND_BY_ID = "SELECT * FROM book_info WHERE id_book_info = ?";
    private static final String INSERT = "INSERT INTO book_info VALUES (id_book_info,?,?,?)";
    private static final String UPDATE = "UPDATE book_info SET amount = ?,price = ?,id_book = ? WHERE id_book_info = ?";
    private static final String DELETE = "DELETE FROM book_info WHERE id_book_info = ?";
    private static final String FIND_BY_BOOK = "SELECT book_info.id_book_info,book_info.amount,book_info.price FROM book_info JOIN book ON book.id_book  = book_info.id_book WHERE book.id_book = ?";
    private static final String FIND_BY_TRANSACTION = "SELECT book_info.id_book_info,book_info.amount,book_info.price FROM book_info JOIN transaction ON transaction.id_book_info  = book_info.id_book_info WHERE transaction.id_transaction = ?";

    @Override
    public BookInfo insert(BookInfo item) throws DaoException {
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
                statementBookInfo(statement, item);
                statement.executeUpdate();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    resultSet.next();
                    item.setId(resultSet.getInt(1));
                }
            }
            log.debug("Create the bookInfo entity where id = {}", item.getId());
        } catch (SQLException e) {
            log.warn("Can't insert where value equals : {}", item,e);
            throw new DaoException("can't insert " + item, e);
        }
        return item;
    }

    @Override
    public BookInfo findById(int id) throws DaoException {
        BookInfo bookInfo = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        bookInfo = itemBookInfo(bookInfo, resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Can't find the bookInfo entity where id equals : {}", id,e);
            throw new DaoException("can't find by id ", e);
        }
        return bookInfo;
    }

    @Override
    public void update(BookInfo item) throws DaoException {
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(UPDATE)) {
                statementBookInfo(statement, item);
                statement.setInt(4, item.getId());
                statement.executeUpdate();
            }
            log.debug("Update the bookInfo entity where id = {} : ", item.getId());
        } catch (SQLException e) {
            log.warn("Can't update the bookInfo entity where id equals : {}", item.getId(),e);
            throw new DaoException("can't update bookInfo" + item, e);
        }
    }

    @Override
    public void delete(BookInfo item) throws DaoException {
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(DELETE)) {
                statement.setInt(1, item.getId());
                statement.executeUpdate();
            }
            log.debug("Delete the bookInfo entity where id = {} : ", item.getId());
        } catch (SQLException e) {
            log.warn("Can't delete the bookInfo entity where id equals : {}", item.getId(),e);
            throw new DaoException("can't delete bookInfo" + item, e);
        }
    }

    @Override
    public BookInfo findByBook(int id) throws DaoException {
        BookInfo bookInfo = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_BOOK)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        bookInfo = itemBookInfo(bookInfo, resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Can't find the bookInfo entity by book where book id equals : {}", id,e);
            throw new DaoException("can't find by book ", e);
        }
        return bookInfo;
    }

    @Override
    public BookInfo findByTransaction(Transaction transaction) throws DaoException {
        BookInfo bookInfo = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_TRANSACTION)) {
                statement.setInt(1, transaction.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        bookInfo = itemBookInfo(bookInfo, resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Can't find the bookInfo entity by transaction where transaction id equals : {}", transaction.getId(),e);
            throw new DaoException("can't find by transaction " , e);
        }
        return bookInfo;
    }

    private BookInfo itemBookInfo(BookInfo bookInfo, ResultSet resultSet) throws SQLException {
        bookInfo = new BookInfo();
        bookInfo.setId(resultSet.getInt(1));
        bookInfo.setAmount(resultSet.getInt(2));
        bookInfo.setPrice(resultSet.getInt(3));
        return bookInfo;
    }

    private PreparedStatement statementBookInfo(PreparedStatement statement, BookInfo item) throws SQLException {
        statement.setInt(1, item.getAmount());
        statement.setInt(2, item.getPrice());
        statement.setInt(3, item.getBook().getId());
        return statement;
    }
}
