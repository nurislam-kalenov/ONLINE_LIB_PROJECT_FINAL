package nuris.epam.action.post;

import nuris.epam.action.manager.Action;
import nuris.epam.action.manager.ActionResult;
import nuris.epam.entity.City;
import nuris.epam.entity.Customer;
import nuris.epam.entity.Person;
import nuris.epam.services.CustomerService;
import nuris.epam.services.exceptions.ServiceException;
import nuris.epam.utils.SqlDate;
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
 * Action class , allows  admin edit personal date.
 *
 * @author Kalenov Nurislam
 */
public class PersonalDataEditAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(PersonalDataEditAction.class);

    private boolean wrong = false;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {

        CustomerService customerService = new CustomerService();
        Customer customer = new Customer();
        Person person = new Person();
        City city = new City();

        Properties properties = new Properties();

        HttpSession session = req.getSession();
        int id = (int) session.getAttribute(ATT_CUSTOMER_ID);

        try {
            properties.load(RegisterAction.class.getClassLoader().getResourceAsStream(VALIDATION_PROPERTIES));
        } catch (IOException e) {
            log.error("Can't load validation properties.", e);

        }
        try {
            customer = customerService.findCustomerById(id);
            person = customer.getPerson();
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        setPersonValue(req, properties, person, city);

        if (wrong) {
            wrong = false;
            return new ActionResult(PERSONAL_DATA_EDIT, true);
        } else {
            try {
                customer.setPerson(person);
                customerService.updatePerson(customer);
            } catch (ServiceException e) {
                log.warn("Can't update person date", e);
            }
        }
        return new ActionResult(ACCOUNT, true);
    }

    private void checkParamValid(String paramValue, String validator) {
        Pattern pattern = Pattern.compile(validator);
        Matcher matcher = pattern.matcher(paramValue);
        if (!matcher.matches()) {
            wrong = true;
        }
    }

    private boolean availableParam(String param, HttpServletRequest request) {
        return request.getParameter(param) != null && !request.getParameter(param).isEmpty();
    }

    private void setPersonValue(HttpServletRequest req, Properties properties, Person person, City city) {
        if (availableParam(FIRST_NAME, req)) {
            String firstName = req.getParameter(FIRST_NAME);
            checkParamValid(firstName, properties.getProperty(NAME_VALID));
            person.setFirstName(firstName);
        }
        if (availableParam(LAST_NAME, req)) {
            String lastName = req.getParameter(LAST_NAME);
            checkParamValid(lastName, properties.getProperty(NAME_VALID));
            person.setLastName(lastName);
        }
        if (availableParam(MIDDLE_NAME, req)) {
            String middleName = req.getParameter(MIDDLE_NAME);
            checkParamValid(middleName, properties.getProperty(NAME_VALID));
            person.setMiddleName(middleName);
        }
        if (availableParam(PHONE, req)) {
            String phone = req.getParameter(PHONE);
            checkParamValid(phone, properties.getProperty(LIMIT_NUMBER_VALID));
            person.setPhone(phone);
        }
        if (availableParam(BIRTHDAY, req)) {
            String birthday = req.getParameter(BIRTHDAY);
            checkParamValid(birthday, properties.getProperty(DATE_VALID));
            person.setBirthday(SqlDate.stringToDate(birthday));
        }
        if (availableParam(ADDRESS, req)) {
            String address = req.getParameter(ADDRESS);
            checkParamValid(address, properties.getProperty(ADDRESS_VALID));
            person.setAddress(address);
        }

        String cityName = req.getParameter(CITY);
        city.setId(Integer.parseInt(cityName));
        person.setCity(city);
    }
}
