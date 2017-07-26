package nuris.epam.action.post;

import nuris.epam.action.manager.Action;
import nuris.epam.action.manager.ActionResult;
import nuris.epam.entity.Author;
import nuris.epam.entity.Book;
import nuris.epam.entity.BookInfo;
import nuris.epam.entity.Genre;
import nuris.epam.services.BookService;
import nuris.epam.services.exceptions.ServiceException;
import nuris.epam.utils.SqlDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static nuris.epam.action.constants.Constants.*;


/**
 * Action class , allows you to edit data about a book
 *
 * @author Kalenov Nurislam
 */
public class BookEditAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(BookEditAction.class);

    private boolean wrong = false;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter(BOOK_ID);

        BookService bookService = new BookService();
        Properties properties = new Properties();

        BookInfo bookInfo = new BookInfo();
        Author author = new Author();
        Genre genre = new Genre();
        Book book = new Book();

        try {
            properties.load(RegisterAction.class.getClassLoader().getResourceAsStream(VALIDATION_PROPERTIES));
        } catch (IOException e) {
            log.error("Can't load validation properties.", e);
        }

        try {
            req.setAttribute(GENRE_LIST, bookService.getAllGenre());
            bookInfo = bookService.findBookInfoByBookId(Integer.parseInt(id));
            book = bookInfo.getBook();
            author = book.getAuthor();

        } catch (ServiceException e) {
            log.warn("Can't find bookInfo by id in {}", this.getClass().getSimpleName(), e);
            e.printStackTrace();
        }

        setBookValue(req, properties, bookInfo, book, author, genre);

        if (wrong) {
            wrong = false;
            log.debug("Wrong! Referring back again {} page | book id = {}", REFERER, book.getId());
            return new ActionResult(req.getHeader(REFERER), true);
        } else {
            try {
                book.setGenre(genre);
                book.setAuthor(author);
                bookService.updateBook(bookInfo);
            } catch (ServiceException e) {
                log.info("Can't update book {}", this.getClass().getSimpleName());
                return new ActionResult(BOOKS, true);
            }
        }
        return new ActionResult(req.getHeader(REFERER), true);
    }

    private void checkParamValid(String paramValue, String validator) {
        Pattern pattern = Pattern.compile(validator);
        Matcher matcher = pattern.matcher(paramValue);
        if (!matcher.matches()) {
            wrong = true;
        }
    }

    private boolean availableParam(String param, HttpServletRequest request) {
        return request.getParameter(param) != null && !request.getParameter(param).isEmpty();
    }

    private void setBookValue(HttpServletRequest req, Properties properties, BookInfo bookInfo, Book book, Author author, Genre genre) {
        String genreName = req.getParameter(GENRE_NAME);
        genre.setId(Integer.parseInt(genreName));

        if (availableParam(FIRST_NAME, req)) {
            String firstName = req.getParameter(FIRST_NAME);
            checkParamValid(firstName, properties.getProperty(NAME_VALID));
            author.setFirstName(firstName);
        }
        if (availableParam(LAST_NAME, req)) {
            String lastName = req.getParameter(LAST_NAME);
            checkParamValid(lastName, properties.getProperty(NAME_VALID));
            author.setLastName(lastName);
        }
        if (availableParam(MIDDLE_NAME, req)) {
            String middleName = req.getParameter(MIDDLE_NAME);
            checkParamValid(middleName, properties.getProperty(NAME_VALID));
            author.setMiddleName(middleName);
        }
        if (availableParam(DESCRIPTION, req)) {
            String descript = req.getParameter(DESCRIPTION);
            checkParamValid(descript, properties.getProperty(DESCRIPTION_VALID));
            book.setDescription(descript);
        }
        if (availableParam(BOOK_NAME, req)) {
            String name = req.getParameter(BOOK_NAME);
            checkParamValid(name, properties.getProperty(BOOK_NAME_VALID));
            book.setName(name);
        }
        if (availableParam(ISBN, req)) {
            String name = req.getParameter(ISBN);
            checkParamValid(name, properties.getProperty(ISBN_VALID));
            book.setIsbn(name);
        }
        if (availableParam(YEAR, req)) {
            String year = req.getParameter(YEAR);
            checkParamValid(year, properties.getProperty(DATE_VALID));
            book.setDate(SqlDate.stringToDate(year));
        }
        if (availableParam(BOOK_AMOUNT, req)) {
            String amount = req.getParameter(BOOK_AMOUNT);
            checkParamValid(amount, properties.getProperty(BOOK_COUNT_VALID));
            bookInfo.setAmount(Integer.parseInt(amount));
        }
        if (availableParam(BOOK_PRICE, req)) {
            String price = req.getParameter(BOOK_PRICE);
            checkParamValid(price, properties.getProperty(BOOK_PRICE_VALID));
            bookInfo.setPrice(Integer.parseInt(price));
        }
    }
}
