package nuris.epam.services;

import nuris.epam.dao.*;
import nuris.epam.dao.exception.DaoException;
import nuris.epam.dao.manager.DaoFactory;
import nuris.epam.entity.*;
import nuris.epam.services.exceptions.ServiceException;
import nuris.epam.utils.SqlDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Class - service, perform all manipulations and transactions associated with the customer.
 *
 * @author Kalenov Nurislam
 */
public class CustomerService {
    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);
    /**
     * Default role for customers who will register
     */
    private static final String USER_ROLE = "user";

    /**
     * Method, user registration
     *
     * @param customer - entity, with user data
     */
    public void registerCustomer(Customer customer) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                log.debug("Start register a customer where customer id equals :{}", customer.getId());

                PersonDao personDao = (PersonDao) daoFactory.getDao(daoFactory.typeDao().getPersonDao());
                CustomerDao customerDao = (CustomerDao) daoFactory.getDao(daoFactory.typeDao().getCustomerDao());
                CustomerRoleDao customerRoleDao = (CustomerRoleDao) daoFactory.getDao(daoFactory.typeDao().getCustomerRoleDao());
                CustomerRole customerRole = customerRoleDao.findRoleByName(USER_ROLE);

                daoFactory.startTransaction();
                personDao.insert(customer.getPerson());
                customer.setCustomerRole(customerRole);
                customer.setRegisterDate(SqlDate.currentDateAndTime());
                customerDao.insert(customer);
                daoFactory.commitTransaction();

                log.info("Register a customer where customer id equals :{}", customer.getId());
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                log.warn("Can't register customer where customer id equals: {} ", customer.getId(), e);
                throw new ServiceException("can't register customer", e);
            }
        }
    }

    /**
     * Method, search customer by id
     *
     * @param id - customer id
     * @return - specific customer
     */
    public Customer findCustomerById(int id) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Customer customer;
            try {
                log.debug("Find customer by id where id equals: {} ", id);

                CustomerDao customerDao = (CustomerDao) daoFactory.getDao(daoFactory.typeDao().getCustomerDao());
                customer = customerDao.findById(id);
                if(customer!=null){
                    fillCustomer(customer);
                }else {
                    throw new ServiceException("can't find by customer id customer");
                }
                return customer;
            } catch (DaoException e) {
                log.warn("Can't find customer by id where id equals: {} ", id, e);
                throw new ServiceException("can't find by customer id customer", e);
            }
        }
    }

    /**
     * Method, search customer by login
     *
     * @param login - customer login
     * @return - specific customer
     */
    private Customer findCustomerByLogin(String login) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Customer customer;
            try {
                log.debug("Start find customer by login where login equals {}", login);

                CustomerDao customerDao = (CustomerDao) daoFactory.getDao(daoFactory.typeDao().getCustomerDao());
                customer = customerDao.getCustomer(login);
                fillCustomer(customer);

                log.info("Find customer by login where login equals {}", login);
                return customer;
            } catch (DaoException e) {
                log.warn("Can't find customer by login where login equals: {} ", login, e);
                throw new ServiceException("can't find by login customer", e);
            }
        }
    }

    /**
     * Method, search customer by login and password
     *
     * @param login    - customer login
     * @param password - customer password
     * @return - specific customer
     */
    public Customer findCustomerByLoginAndPassword(String login, String password) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Customer customer;
            try {
                CustomerDao customerDao = (CustomerDao) daoFactory.getDao(daoFactory.typeDao().getCustomerDao());
                customer = customerDao.getCustomer(login, password);
                fillCustomer(customer);

                log.info("Find customer by login and password where login/password equals: {} ****", login);
                return customer;
            } catch (DaoException e) {
                log.warn("Can't find customer by login and password where login/password equals: {} ****", login, e);
                throw new ServiceException("can't find by login and password customer", e);
            }
        }
    }

    /**
     * Method, update customer data (Which were acquired as a result of registrations)
     *
     * @param customer - customer id
     */
    public void updateCustomer(Customer customer) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                PersonDao personDao = (PersonDao) daoFactory.getDao(daoFactory.typeDao().getPersonDao());
                CustomerDao customerDao = (CustomerDao) daoFactory.getDao(daoFactory.typeDao().getCustomerDao());
                Person person = personDao.findByCustomer(customer);
                customer.setPerson(person);
                customerDao.update(customer);
                log.debug("Update customer where customer id equals: {}", customer.getId());
            } catch (DaoException e) {
                log.warn("Can't update customer where customer id equals: {}", customer.getId(), e);
                throw new ServiceException("can't update customer ", e);
            }
        }
    }

    /**
     * Method,update customer data (Passport data)
     *
     * @param customer - should contain person entity with data
     */
    public void updatePerson(Customer customer) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                PersonDao personDao = (PersonDao) daoFactory.getDao(daoFactory.typeDao().getPersonDao());
                personDao.update(customer.getPerson());
                log.debug("Update person where person id equals: {}", customer.getPerson().getId());
            } catch (DaoException e) {
                log.warn("Can't update person where person id equals: {}", customer.getPerson().getId(), e);
                throw new ServiceException("can't update person", e);
            }
        }
    }

    public void deleteCustomer(Customer customer) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                PersonDao personDao = (PersonDao) daoFactory.getDao(daoFactory.typeDao().getPersonDao());
                CustomerDao customerDao = (CustomerDao) daoFactory.getDao(daoFactory.typeDao().getCustomerDao());
                Person person = personDao.findByCustomer(customer);

                daoFactory.startTransaction();
                customerDao.delete(customer);
                personDao.delete(person);
                daoFactory.commitTransaction();
                log.info("Delete customer where customer id equals: {}", customer.getId());

            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                log.warn("Can't rollback transaction", e);
                throw new ServiceException("can't rollback transaction", e);
            }
            log.warn("Can't delete customer where customer id equals: {}", customer.getId());
        }
    }


    /**
     * Method, provides the full list of city
     *
     * @return Return a list of cities
     */
    public List<City> getAllCity() throws ServiceException {
        List<City> list;
        try {
            try (DaoFactory daoFactory = new DaoFactory()) {
                CityDao cityDao = (CityDao) daoFactory.getDao(daoFactory.typeDao().getCityDao());
                list = cityDao.getAllCity();
                return list;
            }
        } catch (DaoException e) {
            log.warn("Can't get all city from CustomerService", e);
            throw new ServiceException("Can't get all city", e);
        }
    }

    public int getCustomerCount() throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                CustomerDao customerDao = (CustomerDao) daoFactory.getDao(daoFactory.typeDao().getCustomerDao());
                return customerDao.getCustomerCount();
            } catch (DaoException e) {
                log.warn("Can't get count customer from CustomerService", e);
                throw new ServiceException("can't get count customer", e);
            }
        }
    }

    /**
     * Method,provides a list of customer in a specific range
     *
     * @param start - the row from which you must begin
     * @param end   - the row from which you must finish
     * @return list of customer
     */
    public List<Customer> getListCustomers(int start, int end) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                CustomerDao customerDao = (CustomerDao) daoFactory.getDao(daoFactory.typeDao().getCustomerDao());
                List<Customer> list = customerDao.getLimitCustomers(start, end);
                for (Customer customer : list) {
                    fillCustomer(customer);
                }
                log.debug("Get customer list by range {} to {}", start, end);
                return list;
            } catch (DaoException e) {
                log.warn("can't get list of customer ", e);
                throw new ServiceException("can't get list of customer ", e);
            }
        }
    }

    /**
     * Method, checks for login availability
     *
     * @param login - customer login
     * @return Return accessibility or inaccessibility of the book
     */
    public boolean isCustomerLoginAvailable(String login) throws ServiceException {
        return findCustomerByLogin(login) == null;
    }

    /**
     * Searches for customer  by entity management
     *
     * @param management - entity
     * @return - specific customer
     */
    public Customer findCustomerByManagement(Management management) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Customer customer;
            try {
                CustomerDao customerDao = (CustomerDao) daoFactory.getDao(daoFactory.typeDao().getCustomerDao());
                customer = customerDao.findByManagement(management);
                fillCustomer(customer);
                return customer;
            } catch (DaoException e) {
                log.warn("Can't find customer by management id where id equals {} ", management.getId(), e);
                throw new ServiceException("Can't find customer by management id", e);
            }
        }
    }

    /**
     * Method, fill the customer entity of the data associated with customer
     *
     * @param customer - entity
     */
    private void fillCustomer(Customer customer) throws ServiceException {
        try {
            if (customer != null) {
                log.debug("Fill customer with information");

                try (DaoFactory daoFactory = new DaoFactory()) {
                    PersonDao personDao = (PersonDao) daoFactory.getDao(daoFactory.typeDao().getPersonDao());
                    CustomerRoleDao customerRoleDao = (CustomerRoleDao) daoFactory.getDao(daoFactory.typeDao().getCustomerRoleDao());
                    CityDao cityDao = (CityDao) daoFactory.getDao(daoFactory.typeDao().getCityDao());

                    Person person = personDao.findByCustomer(customer);
                    person.setCity(cityDao.findByPerson(person));
                    customer.setPerson(person);
                    customer.setCustomerRole(customerRoleDao.findByCustomer(customer));
                }
            }
        } catch (DaoException e) {
            log.warn("Can't fill customer with information ", e);
            throw new ServiceException("Can't fill customer ", e);
        }
    }
}
