package nuris.epam.action.get;

import nuris.epam.action.manager.Action;
import nuris.epam.action.manager.ActionResult;
import nuris.epam.entity.Customer;
import nuris.epam.entity.Transaction;
import nuris.epam.services.CustomerService;
import nuris.epam.services.TransactionService;
import nuris.epam.services.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static nuris.epam.action.constants.Constants.*;

/**
 * Action class, provide data about specific reader
 *
 * @author Kalenov Nurislam
 */
public class PageAboutReaderAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(PageAboutReaderAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp){
        CustomerService customerService = new CustomerService();
        TransactionService transactionService = new TransactionService();
        List<Transaction> addTransaction = new ArrayList<>();
        Transaction transaction = new Transaction();
        Customer customer = null;
        String id = req.getParameter(READER_ID);

        try {
            customer = customerService.findCustomerById(Integer.parseInt(id));
            transaction.setCustomer(customer);
            List<Transaction> transactions = transactionService.findTransactionsByCustomer(transaction);
            for (Transaction tran : transactions) {
                if (tran.getEndDate() == null) {
                    addTransaction.add(tran);
                }
            }
            log.debug("Take information about customer by id = {}", id);
        } catch (ServiceException e) {
            log.warn("Can't take information about customer by id = {}", id);
            return new ActionResult(READERS,true);
        }
        req.setAttribute(TRANSACTIONS, addTransaction);
        req.setAttribute(ATT_CUSTOMER_INFO, customer);
        return new ActionResult(ABOUT_READER);
    }
}

