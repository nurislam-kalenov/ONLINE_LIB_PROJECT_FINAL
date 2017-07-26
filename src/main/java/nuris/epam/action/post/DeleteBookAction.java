package nuris.epam.action.post;

import nuris.epam.action.manager.Action;
import nuris.epam.action.manager.ActionResult;
import nuris.epam.entity.BookInfo;
import nuris.epam.services.BookService;
import nuris.epam.services.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static nuris.epam.action.constants.Constants.*;

/**
 * Action class , allows admin delete a book.
 *
 * @author Kalenov Nurislam
 */
public class DeleteBookAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(DeleteBookAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp){
        BookService bookService = new BookService();
        BookInfo bookInfo = new BookInfo();
        String id = req.getParameter(DELETE_ID);
        bookInfo.setId(Integer.parseInt(id));

        try {
            bookService.deleteBook(bookInfo);
            log.info("Delete book by id = {}", id);
        } catch (ServiceException e) {
            log.warn("Can't delete book by id = {}", id ,e);
            return new ActionResult(DELETE_BOOK_ERROR, true);
        }

        return new ActionResult(BOOKS, true);
    }
}
