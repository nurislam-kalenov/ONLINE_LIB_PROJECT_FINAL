package nuris.epam.action.get;

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
import java.util.List;

import static nuris.epam.action.constants.Constants.*;

/**
 * Action class, provide data for customer about his dept and dept story
 *
 * @author Kalenov Nurislam
 */
public class PageReturnCustomerBookAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(PageReturnCustomerBookAction.class);

    private boolean isActive = false;
    private boolean isActiveState = false;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        int page = 1;
        int recordPerPage = 3;

        if (req.getParameter(PAGE) != null) {
            page = Integer.parseInt(req.getParameter(PAGE));
        }

        if (req.getParameter(ACTIVE) != null) {
            isActive = Boolean.valueOf(req.getParameter(ACTIVE));
            isActiveState = isActive;
        }

        HttpSession session = req.getSession();
        int id = (int) session.getAttribute(ATT_CUSTOMER_ID);

        TransactionService transactionService = new TransactionService();
        Transaction transaction = new Transaction();
        Customer customer = new Customer();
        customer.setId(id);
        transaction.setCustomer(customer);

        try {
            List<Transaction> list = transactionService.getListTransaction(transaction, page, recordPerPage, isActiveState);
            int noOfRecords = transactionService.getTransactionCountByCustomer(transaction, isActiveState);
            int noOfPages = (int) Math.ceil((double) noOfRecords / recordPerPage);

            req.setAttribute(ATT_NO_PAGES, noOfPages);
            req.setAttribute(ATT_CURRENT_PAGE, page);
            req.setAttribute(ATT_CUSTOMER_BOOK, list);
            req.setAttribute(ATT_IS_ACTIVE_STATE, isActiveState);
            log.debug("Transfer book list with information for returning book");
        } catch (ServiceException e) {
            log.warn("Can't transfer book list with information for returning book", e);
        }
        return new ActionResult(CUSTOMER_BOOK);
    }

}
