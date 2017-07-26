package nuris.epam.action.post;

import nuris.epam.action.manager.Action;
import nuris.epam.action.manager.ActionResult;
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
 * Action class , allows you to edit data about a bookAllows you to register a book
 *
 * @author Kalenov Nurislam
 */
public class CustomerReturnBookAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(CustomerReturnBookAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp){

        TransactionService transactionService = new TransactionService();
        Transaction transaction = new Transaction();
        Customer customer = new Customer();

        HttpSession session = req.getSession();
        int id = (int) session.getAttribute(ATT_CUSTOMER_ID);
        String transactionId = req.getParameter(RETURN_BOOK);

        transaction.setId(Integer.parseInt(transactionId));
        customer.setId(id);

        try {
            transactionService.customerReturnBook(transaction, customer);
            log.debug("Transaction(return) book where customer id ={} , transaction id = {}",customer.getId() , transaction.getId() );
        } catch (ServiceException e) {
            log.warn("Can't transaction(return) book where customer id ={} , transaction id = {} ",customer.getId() , transaction.getId(),e);
            return new ActionResult(BOOKS, true);
        }

        return new ActionResult(DEPT_CUSTOMER_BOOK, true);
    }
}
