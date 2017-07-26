package nuris.epam.dao.mysql;

import nuris.epam.dao.BaseDao;
import nuris.epam.dao.ManagementDao;
import nuris.epam.dao.exception.DaoException;
import nuris.epam.entity.Management;
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
public class MySqlManagementDao extends BaseDao implements ManagementDao {
    private static final Logger log = LoggerFactory.getLogger(MySqlManagementDao.class);

    private static final String FIND_BY_ID = "SELECT * FROM management WHERE id_management = ?";
    private static final String INSERT = "INSERT INTO management VALUES (id_management,?,?)";
    private static final String UPDATE = "UPDATE management SET return_date = ?,id_transaction = ? WHERE id_management = ?";
    private static final String ACTIVE_MANAGEMENT = "SELECT * FROM management WHERE return_date IS NULL limit ?,?";
    private static final String INACTIVE_MANAGEMENT ="SELECT * FROM management WHERE return_date IS NOT NULL limit ?,?";
    private static final String MANAGEMENT_ACTIVE_COUNT = "SELECT COUNT(*) FROM management WHERE return_date IS NULL";
    private static final String MANAGEMENT_INACTIVE_COUNT =  "SELECT COUNT(*) FROM management WHERE return_date IS NOT NULL";

    @Override
    public Management insert(Management item) throws DaoException {
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
                statementManagement(statement, item);
                statement.executeUpdate();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    resultSet.next();
                    item.setId(resultSet.getInt(1));
                }
            }
            log.debug("Create the management entity where id = {}", item.getId());
        } catch (SQLException e) {
            log.warn("Can't insert {} where value equals : {}", item);
            throw new DaoException("can't insert " + item, e);
        }
        return item;
    }

    @Override
    public Management findById(int id) throws DaoException {
        Management management = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        management = itemManagement(management, resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Can't find the management entity where id equals : {} ", id, e);
            throw new DaoException("can find by id " , e);
        }
        return management;
    }

    @Override
    public void update(Management item) throws DaoException {
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(UPDATE)) {
                statementManagement(statement, item);
                statement.setInt(3, item.getId());
                statement.executeUpdate();
            }
            log.debug("Update the management entity where id = {} : ", item.getId());
        } catch (SQLException e) {
            log.warn("Can't update the management entity where id equals : {} ", item.getId(), e);
            throw new DaoException("can't update  " + item, e);
        }
    }

    @Override
    public void delete(Management item) throws DaoException {
    }

    @Override
    public List<Management> getListManagement(int start, int count, boolean isActive) throws DaoException {
        List<Management> list = new ArrayList<>();
        Management management = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(isActive ? INACTIVE_MANAGEMENT : ACTIVE_MANAGEMENT)) {
                statement.setInt(1, ((start - 1) * count));
                statement.setInt(2, count);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        management = itemManagement(management, resultSet);
                        list.add(management);
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Can't get management list by isActivity{} where page range {} to {} ", isActive, start, count, e);
            throw new DaoException("Can't get list of management ", e);
        }
        return list;
    }


    @Override
    public int getManagementCount(boolean isActive) throws DaoException {
        int count = 0;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(isActive ? MANAGEMENT_INACTIVE_COUNT : MANAGEMENT_ACTIVE_COUNT)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        count = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Can't get management count", e);
            throw new DaoException("Can't count by active/inactive " , e);
        }
        return count;
    }

    private Management itemManagement(Management management, ResultSet resultSet) throws SQLException {
        management = new Management();
        management.setId(resultSet.getInt(1));
        management.setReturnDate(resultSet.getTimestamp(2));
        return management;
    }

    private PreparedStatement statementManagement(PreparedStatement statement, Management item) throws SQLException {
        if (item.getReturnDate() == null) {
            statement.setNull(1, Types.DATE);
        } else {
            statement.setTimestamp(1, item.getReturnDate());
        }
        statement.setInt(2, item.getTransaction().getId());
        return statement;
    }
}
