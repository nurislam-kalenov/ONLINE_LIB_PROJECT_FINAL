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
 * Action class, provide data for profile editing
 *
 * @author Kalenov Nurislam
 */
public class PageProfileEditAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(PagePersonalDataEditAction.class);

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response){
        CustomerService customerService = new CustomerService();
        HttpSession session = request.getSession();
        int id = (int) session.getAttribute(ATT_CUSTOMER_ID);
        try {
            Customer customer = customerService.findCustomerById(id);
            request.setAttribute(EMAIL , customer.getEmail());
            log.debug("Transfer information about customer for edit profile data where customer id = {} ", id);
        } catch (ServiceException e) {
            log.warn("Can't transfer information about customer for edit profile data where customer id = {} ", id);
        }
        return new ActionResult(PROFILE_EDIT);
    }
}
