package nuris.epam.action.get;

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
 * Action class, provide data about book ,with the loaded fields
 *
 * @author Kalenov Nurislam
 */
public class PageBookEditAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(PageBookEditAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter(BOOK_ID);
        BookService bookService = new BookService();
        try {
            BookInfo bookInfo = bookService.findBookInfoByBookId(Integer.parseInt(id));
            req.setAttribute(BOOK_INFO, bookInfo);
            req.setAttribute(GENRE_LIST, bookService.getAllGenre());

        } catch (ServiceException e) {
            log.warn("Can't edit book where id = {}",id , e);
        }
        return new ActionResult(BOOK_EDIT);
    }
}
