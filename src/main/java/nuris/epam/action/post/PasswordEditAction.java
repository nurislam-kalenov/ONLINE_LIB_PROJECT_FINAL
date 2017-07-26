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
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static nuris.epam.action.constants.Constants.*;

/**
 * Action class , allows customer and admin edit password.
 *
 * @author Kalenov Nurislam
 */
public class PasswordEditAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(PasswordEditAction.class);

    private boolean wrong = false;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {

        CustomerService customerService = new CustomerService();
        Customer customer = new Customer();
        Properties properties = new Properties();

        HttpSession session = req.getSession();
        int id = (int) session.getAttribute(ATT_CUSTOMER_ID);

        try {
            properties.load(RegisterAction.class.getClassLoader().getResourceAsStream(VALIDATION_PROPERTIES));
        } catch (IOException e) {
            log.error("Can't load validation properties.", e);
        }

        String oldPassword = req.getParameter(OLD_PASSWORD);
        String password = req.getParameter(PASSWORD);
        String passwordConfirm = req.getParameter(PASSWORD_CONFIRM);

        try {
            customer = customerService.findCustomerById(id);

            if (!customer.getPassword().equals(Encoder.toEncode(oldPassword))) {
                wrong = true;
                log.debug("Customer by id = {} can't change oldPassword", customer.getId());
                req.setAttribute(OLD_PASSWORD_ERROR, true);
            } else {
                if (customer.getPassword().equals(Encoder.toEncode(password))) {
                    wrong = true;
                    log.debug("Customer by id = {} can't confirm new password", customer.getId());
                    req.setAttribute(MATCH_PASSWORD_ERROR, true);
                }
            }

            if (!password.equals(passwordConfirm)) {
                log.debug("Customer by id = {} can't confirm password", customer.getId());
                wrong = true;
                req.setAttribute(PASSWORD_ERROR, true);
            } else {
                checkParamValid(PASSWORD, password, properties.getProperty(PASSWORD_VALID), req);
            }

            customer.setPassword(Encoder.toEncode(password));
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        if (wrong) {
            wrong = false;
            log.debug("Wrong! Referring back again {} page | customer id = {}", PROFILE_EDIT, customer.getId());
            return new ActionResult(PROFILE_EDIT);
        } else {
            try {
                customerService.updateCustomer(customer);
            } catch (ServiceException e) {
                log.info("Can't update customer password {}", e);
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
}
