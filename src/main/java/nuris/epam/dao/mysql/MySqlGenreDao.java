package nuris.epam.dao.mysql;

import nuris.epam.dao.BaseDao;
import nuris.epam.dao.GenreDao;
import nuris.epam.dao.exception.DaoException;
import nuris.epam.entity.Book;
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
public class MySqlGenreDao extends BaseDao implements GenreDao {
    private static final Logger log = LoggerFactory.getLogger(MySqlGenreDao.class);

    private static final String FIND_BY_ID = "SELECT * FROM genre WHERE id_genre = ?";
    private static final String SELECT_ALL = "SELECT * FROM genre";
    private static final String FIND_BY_BOOK = "SELECT genre.id_genre,genre.name FROM genre JOIN book ON book.id_genre  = genre.id_genre WHERE book.id_book = ?";

    @Override
    public Genre insert(Genre item) throws DaoException {
        return null;
    }

    @Override
    public Genre findById(int id) throws DaoException {
        Genre genre = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        genre = itemGenre(genre, resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Can't find the genre entity where id equals : {} ", id ,e);
            throw new DaoException("can't find by id ", e);
        }
        return genre;
    }

    @Override
    public void update(Genre item) throws DaoException {

    }

    @Override
    public void delete(Genre item) throws DaoException {

    }


    @Override
    public List<Genre> getAllGenre() throws DaoException {
        List<Genre> list = new ArrayList<>();
        Genre genre = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(SELECT_ALL)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        genre = itemGenre(genre, resultSet);
                        list.add(genre);
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Can't get all genre list ",e);
            throw new DaoException("can't get all list ", e);
        }
        return list;
    }


    @Override
    public Genre findByBook(Book book) throws DaoException {
        Genre genre = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_BOOK)) {
                statement.setInt(1, book.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        genre = itemGenre(genre, resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException("can't find by book ", e);
        }
        return genre;
    }

    private Genre itemGenre(Genre genre, ResultSet resultSet) throws SQLException {
        genre = new Genre();
        genre.setId(resultSet.getInt(1));
        genre.setName(resultSet.getString(2));
        return genre;
    }
}
