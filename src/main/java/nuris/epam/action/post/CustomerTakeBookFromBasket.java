package nuris.epam.action.post;

import nuris.epam.action.get.AbstractBasket;
import nuris.epam.action.manager.Action;
import nuris.epam.action.manager.ActionResult;
import nuris.epam.entity.Basket;
import nuris.epam.entity.BookInfo;
import nuris.epam.entity.Customer;
import nuris.epam.entity.Transaction;
import nuris.epam.services.BookService;
import nuris.epam.services.TransactionService;
import nuris.epam.services.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static nuris.epam.action.constants.Constants.*;

/**
 * Action class , allows customers take a book from basket
 *
 * @author Kalenov Nurislam
 */
public class CustomerTakeBookFromBasket extends AbstractBasket implements Action {
    private static final Logger log = LoggerFactory.getLogger(CustomerTakeBookFromBasket.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        TransactionService transactionService = new TransactionService();
        BookService bookService = new BookService();
        Transaction transaction = new Transaction();
        Customer customer = new Customer();
        BookInfo bookInfo;

        HttpSession session = req.getSession();
        Basket basket = getBasket(session);
        int id = (int) session.getAttribute(ATT_CUSTOMER_ID);
        int bookInfoId = Integer.parseInt(req.getParameter(ATT_BOOK_INFO_ID));
        int bookId = Integer.parseInt(req.getParameter(BOOK_ID));
        try {
            bookInfo = bookService.findBookInfoByBookId(bookId);
            customer.setId(id);
            transaction.setCustomer(customer);
            transaction.setBookInfo(bookInfo);

            if (transactionService.isBookInTransactionAlreadyTaken(transaction) || transactionService.countActiveTransaction(transaction) > MAX_COUNT_BOOKS) {
                req.setAttribute(ATT_TAKE_ERROR, true);
                log.debug("Can't past checking for isAlready taken {} and max count book{}", transactionService.isBookInTransactionAlreadyTaken(transaction), transactionService.countActiveTransaction(transaction) > MAX_COUNT_BOOKS);
                return new ActionResult(BASKET);
            } else {
                basket.delete(bookInfoId);
                transactionService.customerTakeBook(transaction);
                log.debug("Book deleted from basket and take book where customer id = {}", customer.getId());
            }
        } catch (ServiceException e) {
            log.warn("Can't book delete from basket and take book where customer id = {}", customer.getId(), e);
        }
        return new ActionResult(BASKET, true);

    }
}
