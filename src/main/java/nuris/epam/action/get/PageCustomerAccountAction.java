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
import javax.servlet.http.HttpSession;

import static nuris.epam.action.constants.Constants.*;

/**
 * Action class, provide data about specific customer
 *
 * @author Kalenov Nurislam
 */
public class PageCustomerAccountAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(PageCustomerAccountAction.class);

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response){
        CustomerService customerService = new CustomerService();
        Customer customer = null;
        HttpSession session = request.getSession();
        int id = (int) session.getAttribute(ATT_CUSTOMER_ID);
        try {
            customer = customerService.findCustomerById(id);
            log.debug("Open customer account by id = {}", id );
        } catch (ServiceException e) {
            log.warn("Can't open customer account by id = {}", id );
        }
        request.setAttribute(ATT_CUSTOMER_INFO, customer);
        return new ActionResult(ACCOUNT);
    }
}
