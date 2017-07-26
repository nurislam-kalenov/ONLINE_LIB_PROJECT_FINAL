package nuris.epam.action.post;

import nuris.epam.action.manager.Action;
import nuris.epam.action.manager.ActionResult;
import nuris.epam.entity.City;
import nuris.epam.entity.Customer;
import nuris.epam.entity.Person;
import nuris.epam.services.CustomerService;
import nuris.epam.services.exceptions.ServiceException;
import nuris.epam.utils.Encoder;
import nuris.epam.utils.SqlDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static nuris.epam.action.constants.Constants.*;

/**
 * Action class , allows a customer to register in system.
 *
 * @author Kalenov Nurislam
 */
public class RegisterAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(RegisterAction.class);

    private String email;
    private String password;
    private String passwordConfirm;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
    private String birthday;
    private String address;
    private String cityName;

    private boolean wrong = false;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {

        CustomerService customerService = new CustomerService();
        Properties properties = new Properties();
        Customer customer = new Customer();
        Person person = new Person();
        City city = new City();

        try {
            properties.load(RegisterAction.class.getClassLoader().getResourceAsStream(VALIDATION_PROPERTIES));
        } catch (IOException e) {
            log.error("Can't load validation properties.", e);
        }

        try {
            req.setAttribute(CITY_LIST, customerService.getAllCity());
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        initValues(req);

        try {
            if (!customerService.isCustomerLoginAvailable(email)) {
                req.setAttribute(EMAIL_ERROR, true);
                wrong = true;
            } else {
                checkParamValid(EMAIL, email, properties.getProperty(EMAIL_VALID), req);
            }
        } catch (ServiceException e) {
            new ActionResult(WELCOME);
            log.info("Can't check login available", e);
        }

        if (!password.equals(passwordConfirm)) {
            wrong = true;
            req.setAttribute(PASSWORD_ERROR, true);
        } else {
            checkParamValid(PASSWORD, password, properties.getProperty(PASSWORD_VALID), req);
        }

        paramValidation(properties, req);

        city.setId(Integer.parseInt(cityName));
        person.setCity(city);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setMiddleName(middleName);
        person.setBirthday(SqlDate.stringToDate(birthday));
        person.setPhone(phone);
        person.setAddress(address);
        customer.setPerson(person);
        customer.setEmail(email);
        customer.setPassword(Encoder.toEncode(password));

        if (wrong) {
            wrong = false;
            log.debug("Can't register new customer and referred {} page", REGISTER);
            return new ActionResult(REGISTER);
        } else {
            try {
                log.info("Customer by login {} registered in system", customer.getEmail());
                customerService.registerCustomer(customer);
            } catch (ServiceException e) {
                log.warn("Customer by login {} can't registered in system", customer.getEmail());
            }
        }
        return new ActionResult(WELCOME, true);
    }

    private void initValues(HttpServletRequest req) {
        email = req.getParameter(EMAIL);
        password = req.getParameter(PASSWORD);
        passwordConfirm = req.getParameter(PASSWORD_CONFIRM);
        firstName = req.getParameter(FIRST_NAME);
        lastName = req.getParameter(LAST_NAME);
        middleName = req.getParameter(MIDDLE_NAME);
        phone = req.getParameter(PHONE);
        birthday = req.getParameter(BIRTHDAY);
        address = req.getParameter(ADDRESS);
        cityName = req.getParameter(CITY);
    }

    private void checkParamValid(String paramName, String paramValue, String validator, HttpServletRequest request) {
        Pattern pattern = Pattern.compile(validator);
        Matcher matcher = pattern.matcher(paramValue);
        if (!matcher.matches()) {
            request.setAttribute(paramName + ERROR, true);
            wrong = true;
        }
    }

    private void paramValidation(Properties properties, HttpServletRequest req) {
        checkParamValid(FIRST_NAME, firstName, properties.getProperty(NAME_VALID), req);
        checkParamValid(LAST_NAME, lastName, properties.getProperty(NAME_VALID), req);
        checkParamValid(MIDDLE_NAME, middleName, properties.getProperty(NAME_VALID), req);
        checkParamValid(PHONE, phone, properties.getProperty(LIMIT_NUMBER_VALID), req);
        checkParamValid(BIRTHDAY, birthday, properties.getProperty(DATE_VALID), req);
        checkParamValid(ADDRESS, address, properties.getProperty(ADDRESS_VALID), req);
    }

}
