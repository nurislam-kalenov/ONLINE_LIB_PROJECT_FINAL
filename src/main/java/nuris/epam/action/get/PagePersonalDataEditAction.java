package nuris.epam.action.get;

import nuris.epam.action.manager.Action;
import nuris.epam.action.manager.ActionResult;
import nuris.epam.entity.Customer;
import nuris.epam.entity.Person;
import nuris.epam.services.CustomerService;
import nuris.epam.services.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static nuris.epam.action.constants.Constants.*;

/**
 * Action class, provide data for personal date editin
 *
 * @author Kalenov Nurislam
 */
public class PagePersonalDataEditAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(PagePersonalDataEditAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        CustomerService customerService = new CustomerService();

        HttpSession session = req.getSession();
        int id = (int) session.getAttribute(ATT_CUSTOMER_ID);

        try {
            Customer customer = customerService.findCustomerById(id);
            Person person = customer.getPerson();

            setPersonAttribute(req, person);

            req.setAttribute(CITY_LIST, customerService.getAllCity());
            log.debug("Transfer information about customer for edit personal data where customer id = {} ", id);
        } catch (ServiceException e) {
            log.warn("Can't transfer information about customer for edit personal data where customer id = {} ", id);
        }
        return new ActionResult(PERSONAL_DATA_EDIT);
    }

    /**
     * Inserting a value into an attribute
     * */
    private void setPersonAttribute(HttpServletRequest req, Person person) {
        req.setAttribute(FIRST_NAME, person.getFirstName());
        req.setAttribute(LAST_NAME, person.getLastName());
        req.setAttribute(MIDDLE_NAME, person.getMiddleName());
        req.setAttribute(PHONE, person.getPhone());
        req.setAttribute(BIRTHDAY, person.getBirthday());
        req.setAttribute(ADDRESS, person.getAddress());
    }
}
