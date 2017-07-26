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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static nuris.epam.action.constants.Constants.*;

/**
 * Action class , allows customer and admin edit email.
 *
 * @author Kalenov Nurislam
 */
public class EmailEditAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(EmailEditAction.class);

    private boolean wrong = false;

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {

        CustomerService customerService = new CustomerService();
        Customer customer = new Customer();
        Properties properties = new Properties();

        HttpSession session = request.getSession();
        int id = (int) session.getAttribute(ATT_CUSTOMER_ID);

        try {
            properties.load(RegisterAction.class.getClassLoader().getResourceAsStream(VALIDATION_PROPERTIES));
        } catch (IOException e) {
            log.error("Can't load validation properties.", e);
        }

        String email = request.getParameter(EMAIL);
        try {
            if (availableParam(EMAIL, request)) {
                customer = customerService.findCustomerById(id);
            }
            if (!customerService.isCustomerLoginAvailable(email)) {
                log.debug("Parameters value {} in {} doest'n past validation/customer id = {}", this.getClass().getSimpleName(), email, id);
                wrong = true;
                request.setAttribute(EMAIL_ERROR, true);
            } else {
                checkParamValid(EMAIL, email, properties.getProperty(EMAIL_VALID), request);
            }
        } catch (ServiceException e) {
            log.warn("Can't find customer by id = {}", id);
        }

        customer.setEmail(email);

        if (wrong) {
            wrong = false;
            log.debug("Wrong! Referring back again {} page | customer id = {}", PROFILE_EDIT, customer.getId());
            return new ActionResult(PROFILE_EDIT);
        } else {
            try {
                customerService.updateCustomer(customer);
            } catch (ServiceException e) {
                log.info("Can't update customer email {}", e);
            }
        }
        return new ActionResult(ACCOUNT, true);
    }


    private void checkParamValid(String paramName, String paramValue, String validator, HttpServletRequest request) {
        Pattern pattern = Pattern.compile(validator);
        Matcher matcher = pattern.matcher(paramValue);
        if (!matcher.matches()) {
            request.setAttribute(paramName + ERROR, true);
            wrong = true;
        }
    }

    private boolean availableParam(String param, HttpServletRequest request) {
        return request.getParameter(param) != null && !request.getParameter(param).isEmpty();
    }


}
