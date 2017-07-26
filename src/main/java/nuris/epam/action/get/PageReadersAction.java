package nuris.epam.action.get;

import nuris.epam.action.manager.Action;
import nuris.epam.action.manager.ActionResult;
import nuris.epam.entity.Customer;
import nuris.epam.services.CustomerService;
import nuris.epam.services.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static nuris.epam.action.constants.Constants.*;

/**
 * Action class, provide data for admin about readers
 *
 * @author Kalenov Nurislam
 */
public class PageReadersAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(PageReadersAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        CustomerService customerService = new CustomerService();
        int page = 1;
        int recordPerPage = 10;

        if (req.getParameter(PAGE) != null) {
            page = Integer.parseInt(req.getParameter(PAGE));
        }
        try {
            List<Customer> readers = customerService.getListCustomers(page, recordPerPage);

            int noOfRecords = customerService.getCustomerCount();
            int noOfPages = (int) Math.ceil((double) noOfRecords / recordPerPage);

            req.setAttribute(ATT_READERS, readers);
            req.setAttribute(ATT_NO_PAGES, noOfPages);
            req.setAttribute(ATT_CURRENT_PAGE, page);
            log.debug("Transfer customer list page {} to {}", noOfPages, noOfRecords);
        } catch (ServiceException e) {
            log.warn("Can't transfer customer list page");
        }

        return new ActionResult(READERS);
    }
}
