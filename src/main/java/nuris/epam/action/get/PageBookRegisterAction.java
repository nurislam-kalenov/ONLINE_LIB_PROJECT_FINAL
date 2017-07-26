package nuris.epam.action.get;

import nuris.epam.action.manager.Action;
import nuris.epam.action.manager.ActionResult;
import nuris.epam.services.BookService;
import nuris.epam.services.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static nuris.epam.action.constants.Constants.GENRE_LIST;
import static nuris.epam.action.constants.Constants.REGISTER_BOOK;

/**
 * Action class, provide data for register new book customer
 *
 * @author Kalenov Nurislam
 */
public class PageBookRegisterAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(PageBookRegisterAction.class);

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        BookService bookService = new BookService();
        try {
            request.setAttribute(GENRE_LIST, bookService.getAllGenre());
            log.debug("Transfer book registration information");
        } catch (ServiceException e) {
            log.warn("Can't transfer book registration information", e);
        }

        return new ActionResult(REGISTER_BOOK);
    }
}
