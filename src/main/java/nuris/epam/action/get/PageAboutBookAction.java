package nuris.epam.action.get;

import nuris.epam.action.manager.Action;
import nuris.epam.action.manager.ActionResult;
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
 * Action class, provide data about book
 *
 * @author Kalenov Nurislam
 */
public class PageAboutBookAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(PageAboutBookAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {

        TransactionService transactionService = new TransactionService();
        BookService bookService = new BookService();
        Transaction transaction = new Transaction();
        Customer customer = new Customer();
        BookInfo bookInfo = null;

        HttpSession session = req.getSession();
        int customerId = (int) session.getAttribute(ATT_CUSTOMER_ID);
        String id = req.getParameter(BOOK_ID);

        customer.setId(customerId);
        transaction.setCustomer(customer);

        try {
            bookInfo = bookService.findBookInfoByBookId(Integer.parseInt(id));
            transaction.setBookInfo(bookInfo);
            log.debug("Book by id = {} opened with customer id ={}", id, customerId);
        } catch (ServiceException e) {
            log.debug("Book by id = {} can't open with customer id ={}", id, customerId, e);
            return new ActionResult(BOOKS , true);
        }


        checkBookStatus(req, transactionService, transaction, bookInfo);
        req.setAttribute(BOOK_INFO, bookInfo);
        return new ActionResult(ABOUT_BOOK);
    }

    /**
     * Checks the status of the book for valid values
     */
    private void checkBookStatus(HttpServletRequest req, TransactionService transactionService, Transaction transaction, BookInfo bookInfo) {
        if (transactionService.countActiveTransaction(transaction) > MAX_COUNT_BOOKS) {
            req.setAttribute(ATT_COUNT_ERROR, true);
        }
        if (bookInfo.getAmount() <= MIN_COUNT_BOOKS) {
            req.setAttribute(ATT_OVER_ERROR, true);
        }
        if (transactionService.isBookInTransactionAlreadyTaken(transaction)) {
            req.setAttribute(ATT_TAKE_ERROR, true);
        }
    }
}
