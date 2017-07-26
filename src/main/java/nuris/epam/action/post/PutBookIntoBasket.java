package nuris.epam.action.post;

import nuris.epam.action.get.AbstractBasket;
import nuris.epam.action.manager.Action;
import nuris.epam.action.manager.ActionResult;
import nuris.epam.entity.Basket;
import nuris.epam.entity.BookInfo;
import nuris.epam.services.BookService;
import nuris.epam.services.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static nuris.epam.action.constants.Constants.*;

/**
 * Action class , allows  customers to put books into basket.
 *
 * @author Kalenov Nurislam
 */
public class PutBookIntoBasket extends AbstractBasket implements Action {
    private static final Logger log = LoggerFactory.getLogger(PutBookIntoBasket.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp){
        BookService bookService = new BookService();
        HttpSession session = req.getSession();
        Basket basket = getBasket(session);
        int id = (int) session.getAttribute(ATT_CUSTOMER_ID);

        try {
            int bookId = Integer.parseInt(req.getParameter(BOOK_ID));
            BookInfo book = (bookService.findBookInfoByBookId(bookId));

            for(BookInfo bookInfo : basket.getBooks()){
                if(bookInfo.getId() == book.getId()) {
                    return new ActionResult(BOOKS, true);
                }
            }
            basket.add(book);
            session.setAttribute(BASKET, basket);
            log.debug("Customer by id = {} put book into basket" , id);

        } catch (ServiceException e) {
            log.warn("Customer by id = {} can't put book into basket" , id);

        }

        return new ActionResult(BOOKS, true);
    }
}
