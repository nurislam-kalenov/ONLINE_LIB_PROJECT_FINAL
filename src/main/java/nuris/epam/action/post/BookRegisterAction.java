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
 * Action class , allows you to edit data about a bookAllows you to register a book
 *
 * @author Kalenov Nurislam
 */
public class BookRegisterAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(BookRegisterAction.class);

    private String firstName;
    private String lastName;
    private String middleName;
    private String isbn;
    private String description;
    private String name;
    private String year;
    private String genreName;
    private String amount;
    private String price;

    private boolean wrong = false;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {

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
        } catch (ServiceException e) {
            new ActionResult(WELCOME);
            log.error("Can't transfer genre list by attribute ", e);
        }

        initValues(req);

        try {
            if (bookService.isBookIsbnAvailable(isbn)) {
                req.setAttribute(ISBN_ERROR, true);
                wrong = true;
            } else {
                checkParamValid(ISBN, isbn, properties.getProperty(ISBN_VALID), req);
            }
        } catch (ServiceException e) {
            log.warn("Can't check on available a book", e);
        }

        paramValidation(properties, req);

        genre.setId(Integer.parseInt(genreName));
        author.setFirstName(firstName);
        author.setLastName(lastName);
        author.setMiddleName(middleName);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setIsbn(isbn);
        book.setDate(SqlDate.stringToDate(year));
        book.setDescription(description);
        book.setName(name);
        bookInfo.setBook(book);

        if (!amount.isEmpty() && !price.isEmpty()) {
            try {
                bookInfo.setAmount(Integer.valueOf(amount));
                bookInfo.setPrice(Integer.valueOf(price));
            } catch (Exception e) {
                log.debug("Parse error from String to Int/ value strings = {} ,{}", amount, price, e);
                return new ActionResult(REGISTER_BOOK);
            }
        }

        if (wrong) {
            wrong = false;
            log.info("Book by name {}don't registered in system", book.getName());
            return new ActionResult(REGISTER_BOOK);
        } else {
            try {
                log.info("Book by name {} registered in system", book.getName());
                bookService.registerBook(bookInfo);
            } catch (ServiceException e) {
                log.warn("Book by name {} can't registered in system", book.getName());
            }
        }
        return new ActionResult(BOOKS, true);
    }

    private void initValues(HttpServletRequest req){
        firstName = req.getParameter(FIRST_NAME);
        lastName = req.getParameter(LAST_NAME);
        middleName = req.getParameter(MIDDLE_NAME);
        isbn = req.getParameter(ISBN);
        description = req.getParameter(DESCRIPTION);
        name = req.getParameter(BOOK_NAME);
        year = req.getParameter(YEAR);
        genreName = req.getParameter(GENRE_NAME);
        amount = req.getParameter(BOOK_AMOUNT);
        price = req.getParameter(BOOK_PRICE);
    }

    private void checkParamValid(String paramName, String paramValue, String validator, HttpServletRequest request) {
        Pattern pattern = Pattern.compile(validator);
        Matcher matcher = pattern.matcher(paramValue);
        if (!matcher.matches()) {
            request.setAttribute(paramName + ERROR, true);
            wrong = true;
        }
    }

    private void paramValidation(Properties properties, HttpServletRequest req) {
        checkParamValid(FIRST_NAME, firstName, properties.getProperty(NAME_VALID), req);
        checkParamValid(LAST_NAME, lastName, properties.getProperty(NAME_VALID), req);
        checkParamValid(MIDDLE_NAME, middleName, properties.getProperty(NAME_VALID), req);
        checkParamValid(DESCRIPTION, description, properties.getProperty(DESCRIPTION_VALID), req);
        checkParamValid(BOOK_NAME, name, properties.getProperty(BOOK_NAME_VALID), req);
        checkParamValid(YEAR, year, properties.getProperty(DATE_VALID), req);
        checkParamValid(BOOK_AMOUNT, amount, properties.getProperty(BOOK_COUNT_VALID), req);
        checkParamValid(BOOK_PRICE, price, properties.getProperty(BOOK_PRICE_VALID), req);
    }

}
