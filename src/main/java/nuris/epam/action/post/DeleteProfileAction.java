package nuris.epam.action.post;

import nuris.epam.action.manager.Action;
import nuris.epam.action.manager.ActionResult;
import nuris.epam.entity.Customer;
import nuris.epam.services.CustomerService;
import nuris.epam.services.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static nuris.epam.action.constants.Constants.*;

/**
 * Action class , allows admin delete a profile.
 *
 * @author Kalenov Nurislam
 */
public class DeleteProfileAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(DeleteProfileAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        CustomerService customerService = new CustomerService();
        Customer customer;
        Integer id = Integer.valueOf(req.getParameter(DELETE_ID));

        try {
            customer = customerService.findCustomerById(id);
            customerService.deleteCustomer(customer);
            log.debug("Delete customer by id = {}", id);
        } catch (ServiceException e) {
            log.warn("Can't delete customer by id = {}", id, e);
            return new ActionResult(DELETE_PROFILE_ERROR, true);
        }

        if (customer.getCustomerRole().getName().equals(ADMIN)) {
            return new ActionResult(WELCOME);
        }

        return new ActionResult(READERS , true);

    }
}
