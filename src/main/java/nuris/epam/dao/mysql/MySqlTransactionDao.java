package nuris.epam.dao.mysql;

import nuris.epam.dao.BaseDao;
import nuris.epam.dao.TransactionDao;
import nuris.epam.dao.exception.DaoException;
import nuris.epam.entity.Management;
import nuris.epam.entity.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kalenov Nurislam
 */
public class MySqlTransactionDao extends BaseDao implements TransactionDao {
    private static final Logger log = LoggerFactory.getLogger(MySqlTransactionDao.class);

    private static final String FIND_BY_ID = "SELECT * FROM transaction WHERE id_transaction = ?";
    private static final String INSERT = "INSERT INTO transaction (id_book_info,id_customer) VALUES(?,?)";
    private static final String UPDATE = "UPDATE transaction SET end_date = ?,id_book_info = ?,id_customer = ? WHERE id_transaction = ? ";
    private static final String DELETE = "DELETE FROM transaction WHERE id_transaction = ?";
    private static final String FIND_BY_CUSTOMER = "SELECT * FROM transaction WHERE id_customer = ?";
    private static final String FIND_BY_MANAGEMENT = "SELECT transaction.id_transaction ,transaction.start_date ,transaction.end_date FROM transaction JOIN management ON management.id_transaction = transaction.id_transaction WHERE management.id_management = ?";
    private static final String ACTIVE_CUSTOMER = "SELECT transaction.id_transaction ,transaction.start_date ,transaction.end_date ,transaction.id_book_info ,transaction.id_customer FROM transaction JOIN management ON transaction.id_transaction  = management.id_transaction WHERE management.return_date IS NOT NULL AND transaction.id_customer = ? limit ?,?";
    private static final String INACTIVE_CUSTOMER = "SELECT transaction.id_transaction ,transaction.start_date ,transaction.end_date ,transaction.id_book_info ,transaction.id_customer FROM transaction JOIN management ON transaction.id_transaction  = management.id_transaction WHERE management.return_date IS NULL AND transaction.id_customer = ? limit ?,?";

    private static final String TRANSACTION_ACTIVE_COUNT = "SELECT COUNT(*) FROM management JOIN transaction ON transaction.id_transaction  = management.id_transaction WHERE management.return_date IS NOT NULL AND transaction.id_customer = ?";
    private static final String TRANSACTION_INACTIVE_COUNT = "SELECT COUNT(*) FROM management JOIN transaction ON transaction.id_transaction  = management.id_transaction WHERE management.return_date IS NULL AND transaction.id_customer = ?";

    @Override
    public Transaction insert(Transaction item) throws DaoException {
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, item.getBookInfo().getId());
                statement.setInt(2, item.getCustomer().getId());
                statement.executeUpdate();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    resultSet.next();
                    item.setId(resultSet.getInt(1));
                }
            }
            log.debug("Create the transaction entity where id = {}", item.getId());
        } catch (SQLException e) {
            log.warn("Can't insert {} where value equals : {}", this.getClass().getSimpleName(), item);
            throw new DaoException("can't insert " + item, e);
        }
        return item;
    }

    @Override
    public Transaction findById(int id) throws DaoException {
        Transaction transaction = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        transaction = itemTransaction(transaction, resultSet);
                    }
                }
            }

        } catch (SQLException e) {
            log.warn("Can't find the transaction entity where id equals : {} ", id, e);
            throw new DaoException("can't find by id ", e);
        }
        return transaction;
    }

    @Override
    public void update(Transaction item) throws DaoException {
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(UPDATE)) {
                statementTransaction(statement, item);
                statement.setInt(4, item.getId());
                statement.executeUpdate();
            }
            log.debug("Update the transaction entity where id = {} : ", item.getId());
        } catch (SQLException e) {
            log.warn("Can't update the transaction entity where id equals : {} ", item.getId(), e);
            throw new DaoException("can't update" + item, e);
        }
    }

    @Override
    public void delete(Transaction item) throws DaoException {
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(DELETE)) {
                statement.setInt(1, item.getId());
                statement.executeUpdate();
            }
            log.debug("Delete the transaction entity where id = {} : ", item.getId());
        } catch (SQLException e) {
            log.warn("Can't delete the transaction entity where id equals : {} ", item.getId(), e);
            throw new DaoException("can't delete " + item, e);
        }
    }

    @Override
    public List<Transaction> findByCustomer(Transaction transaction) throws DaoException {
        List<Transaction> list = new ArrayList<>();
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_CUSTOMER)) {
                statement.setInt(1, transaction.getCustomer().getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        transaction = itemTransaction(transaction, resultSet);
                        list.add(transaction);
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Can't find transaction entity by customer where customer id equals : {} ", transaction.getCustomer().getId(), e);
            throw new DaoException("can't find by customer", e);
        }
        return list;
    }

    @Override
    public List<Transaction> getListTransactionByCustomer(Transaction transaction, int start, int count, boolean isActive) throws DaoException {
        List<Transaction> list = new ArrayList<>();
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(isActive ? ACTIVE_CUSTOMER : INACTIVE_CUSTOMER)) {
                statement.setInt(1, transaction.getCustomer().getId());
                statement.setInt(2, ((start - 1) * count));
                statement.setInt(3, count);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        transaction = itemTransaction(transaction, resultSet);
                        list.add(transaction);
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Can't get transaction list by range {} to {} where book transaction id equals : {} ", start, count, transaction.getId(), e);
            throw new DaoException("can't get list of transaction by customer ", e);
        }
        return list;
    }

    @Override
    public Transaction findByManagement(Management management) throws DaoException {
        Transaction transaction = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_MANAGEMENT)) {
                statement.setInt(1, management.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        transaction = itemTransaction(transaction, resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Can't find transaction entity by management where id equals : {} ", management.getId(), e);
            throw new DaoException("can't find by management ", e);
        }
        return transaction;
    }

    @Override
    public int getTransactionCountByCustomer(Transaction transaction, boolean isActive) throws DaoException {
        int count = 0;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(isActive ? TRANSACTION_ACTIVE_COUNT : TRANSACTION_INACTIVE_COUNT)) {
                statement.setInt(1, transaction.getCustomer().getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        count = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Can't get transaction entity count by customer where id equals : {} ", transaction.getCustomer().getId(), e);
            throw new DaoException("can't count by active/inactive ", e);
        }
        return count;
    }

    private Transaction itemTransaction(Transaction transaction, ResultSet resultSet) throws SQLException {
        transaction = new Transaction();
        transaction.setId(resultSet.getInt(1));
        transaction.setStartDate(resultSet.getDate(2));
        transaction.setEndDate(resultSet.getTimestamp(3));
        return transaction;
    }

    private PreparedStatement statementTransaction(PreparedStatement statement, Transaction item) throws SQLException {
        if (item.getEndDate() == null) {
            statement.setNull(1, Types.DATE);
        } else {
            statement.setTimestamp(1, item.getEndDate());
        }
        statement.setInt(2, item.getBookInfo().getId());
        statement.setInt(3, item.getCustomer().getId());
        return statement;
    }


}
