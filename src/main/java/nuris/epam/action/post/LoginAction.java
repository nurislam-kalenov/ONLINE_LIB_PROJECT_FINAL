package nuris.epam.action.post;

import nuris.epam.action.manager.Action;
import nuris.epam.action.manager.ActionResult;
import nuris.epam.entity.Customer;
import nuris.epam.services.CustomerService;
import nuris.epam.services.exceptions.ServiceException;
import nuris.epam.utils.Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static nuris.epam.action.constants.Constants.*;

/**
 * Action class , allows a guest to execute login.
 *
 * @author Kalenov Nurislam
 */
public class LoginAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(LoginAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        CustomerService customerService = new CustomerService();
        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);
        try {
            Customer customer = customerService.findCustomerByLoginAndPassword(login, Encoder.toEncode(password));

            if (customer != null) {
                HttpSession session = req.getSession();
                session.setAttribute(ATT_CUSTOMER_ID, customer.getId());
                session.setAttribute(ATT_ROLE, customer.getCustomerRole().getName());
                session.setAttribute(ATT_ROLE_ID, customer.getCustomerRole().getId());
                session.setAttribute(ATT_NAME, customer.getPerson().getFirstName());
                log.info("Customer by id = {} and role = {} login in system ", customer.getId(), customer.getCustomerRole().getName());
                return new ActionResult(BOOKS, true);
            } else {
                req.setAttribute(LOGIN_ERROR, true);
                return new ActionResult(WELCOME);
            }
        } catch (ServiceException e) {
            log.warn("Can't find customer by login and password" ,e);
        }
        return null;
    }
}
