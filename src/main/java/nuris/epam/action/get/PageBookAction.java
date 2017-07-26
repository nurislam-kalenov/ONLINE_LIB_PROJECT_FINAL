package nuris.epam.action.get;

import nuris.epam.action.manager.Action;
import nuris.epam.action.manager.ActionResult;
import nuris.epam.entity.Book;
import nuris.epam.entity.Genre;
import nuris.epam.services.BookService;
import nuris.epam.services.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static nuris.epam.action.constants.Constants.*;

/**
 * Action class, provide data about books
 *
 * @author Kalenov Nurislam
 */
public class PageBookAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(PageBookAction.class);

    private int genreId = 0;
    private int genreState = 1;

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response){
        BookService bookService = new BookService();
        Genre genre = new Genre();

        String name = null;
        int page = 1;
        int recordPerPage = 3;

        try {
            if (request.getParameter(SEARCH) != null) {
                name = request.getParameter(SEARCH);
                log.debug("Search book by name {}" ,name);
            }
            if (request.getParameter(PAGE) != null) {
                page = Integer.parseInt(request.getParameter(PAGE));
            }
            if (request.getParameter(GENRE_ID) != null) {
                genreId = Integer.parseInt(request.getParameter(GENRE_ID));
                genre.setId(genreId);
                genreState = genreId;
            } else {
                genre.setId(genreState);
            }

            List<Book> books = bookService.getListBook(genre, page, recordPerPage);
            List<Genre> genres = bookService.getAllGenre();
            List<Book> book = bookService.getBookByName(name);

            int noOfRecords = bookService.getBookCountByGenre(genre);
            int noOfPages = (int) Math.ceil((double) noOfRecords / recordPerPage);

            if (book.size() > MIN_COUNT_BOOKS) {
                request.setAttribute(ATT_BOOKS, book);
                request.setAttribute(ATT_GENRES, genres);
            } else {
                request.setAttribute(ATT_BOOKS, books);
                request.setAttribute(ATT_GENRES, genres);
                request.setAttribute(ATT_NO_PAGES, noOfPages);
                request.setAttribute(ATT_CURRENT_PAGE, page);
            }

            log.debug("Transfer book list page {} to {}" , noOfPages , noOfRecords);

        } catch (ServiceException e) {
            log.warn("Can't display list of book " , e);
        }
        return new ActionResult(BOOKS);
    }


}
