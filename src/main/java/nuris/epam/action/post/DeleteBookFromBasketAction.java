package nuris.epam.action.post;

import nuris.epam.action.get.AbstractBasket;
import nuris.epam.action.manager.Action;
import nuris.epam.action.manager.ActionResult;
import nuris.epam.entity.Basket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static nuris.epam.action.constants.Constants.*;

/**
 * Action class , allows admin delete a book from basket.
 *
 * @author Kalenov Nurislam
 */

public class DeleteBookFromBasketAction extends AbstractBasket implements Action {
    private static final Logger log = LoggerFactory.getLogger(DeleteBookFromBasketAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp){
        HttpSession session = req.getSession();
        Basket basket = getBasket(session);
        int id = (int) session.getAttribute(ATT_CUSTOMER_ID);

        if (req.getParameter(ATT_DELETE_BOOK_ID) != null) {
            int bookId = Integer.parseInt(req.getParameter(ATT_DELETE_BOOK_ID));
            log.info("Customer by id = {} deleted  book from basket", id);
            basket.delete(bookId);
        }
        if (req.getParameter(ATT_ALL_DELETE_BOOK_ID) != null) {
            log.info("Customer by id = {} deleted all book from basket", id);
            basket.deleteAll();
        }

        return new ActionResult(BASKET, true);
    }
}
