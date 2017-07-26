package nuris.epam.action.post;

import nuris.epam.action.manager.Action;
import nuris.epam.action.manager.ActionResult;
import nuris.epam.entity.BookInfo;
import nuris.epam.entity.Customer;
import nuris.epam.entity.Transaction;
import nuris.epam.services.TransactionService;
import nuris.epam.services.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static nuris.epam.action.constants.Constants.*;

/**
 * Action class , allows customers to take a book
 *
 * @author Kalenov Nurislam
 */
public class CustomerTakeBookAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(CustomerTakeBookAction.class);
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {

        TransactionService transactionService = new TransactionService();
        Transaction transaction = new Transaction();
        BookInfo bookInfo = new BookInfo();
        Customer customer = new Customer();

        HttpSession session = req.getSession();
        int id = (int) session.getAttribute(ATT_CUSTOMER_ID);
        String bookId = req.getParameter(BOOK_ID);

        customer.setId(id);
        bookInfo.setId(Integer.parseInt(bookId));
        transaction.setCustomer(customer);
        transaction.setBookInfo(bookInfo);

        try {
            transactionService.customerTakeBook(transaction);
            log.debug("Transaction(take) book where customer id ={} , transaction id = {}",customer.getId() , transaction.getId() );
        } catch (ServiceException e) {
            log.warn("Can't transaction(take) book where customer id ={} , transaction id = {} ",customer.getId() , transaction.getId(),e);
        }
        return new ActionResult(BOOKS, true);
    }
}
