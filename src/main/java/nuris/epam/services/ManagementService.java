package nuris.epam.services;

import nuris.epam.dao.BookInfoDao;
import nuris.epam.dao.ManagementDao;
import nuris.epam.dao.TransactionDao;
import nuris.epam.dao.exception.DaoException;
import nuris.epam.dao.manager.DaoFactory;
import nuris.epam.entity.BookInfo;
import nuris.epam.entity.Customer;
import nuris.epam.entity.Management;
import nuris.epam.entity.Transaction;
import nuris.epam.services.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Class - service, perform all manipulations and transactions associated with the management.
 *
 * @author Kalenov Nurislam
 */
public class ManagementService {
    private static final Logger log = LoggerFactory.getLogger(ManagementService.class);
    /**
     * Field - count of book which can increase for one return book transaction
     */
    private static final int ONE_BOOK = 1;

    /**
     * Method, intended to provide return book.
     *
     * @param management - entity with contain transaction id
     */
    public void adminReturnBook(Management management) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                log.debug("Start execute transaction for Return book where management id equals {} ", management.getId());

                BookService bookService = new BookService();

                TransactionDao transactionDao = (TransactionDao) daoFactory.getDao(daoFactory.typeDao().getTransactionDao());
                ManagementDao managementDao = (ManagementDao) daoFactory.getDao(daoFactory.typeDao().getManagementDao());
                BookInfoDao bookInfoDao = (BookInfoDao) daoFactory.getDao(daoFactory.typeDao().getBookInfoDao());

                Transaction transaction = transactionDao.findByManagement(management);
                BookInfo bookInfo = bookInfoDao.findByTransaction(transaction);
                management = managementDao.findById(management.getId());
                bookInfo.setAmount(bookInfo.getAmount() + ONE_BOOK);
                transaction.setBookInfo(bookInfo);
                management.setTransaction(transaction);

                if (management.getReturnDate() == null) {
                    log.debug("Management entity passed checking for execute management(return book) where management id equals {} ", management.getId());
                    management.setReturnDate(Timestamp.valueOf(LocalDateTime.now()));
                    daoFactory.startTransaction();
                    bookService.updateBookInfo(bookInfo);
                    managementDao.update(management);
                    daoFactory.commitTransaction();
                }
                log.info("Execute transaction for Return book where management id equals {} ", management.getId());
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                log.warn("Can't return book  where management id equals {}", management.getId(), e);
                throw new ServiceException("Can't insert transaction ", e);
            }
        }
    }

    /**
     * Method, provides a list of active and inactive managements in the range of rows in the table
     * (Active managements - the admin has not yet returned the book to the bookInfo)
     *
     * @param start    - the row from which you must begin
     * @param end      - the row from which you must finish
     * @param isActive - active/inactive management state
     */
    public List<Management> getListManagement(int start, int end, boolean isActive) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                log.debug("Get list management by active/inactive  range {} to {} ", start, end);
                ManagementDao managementDao = (ManagementDao) daoFactory.getDao(daoFactory.typeDao().getManagementDao());
                List<Management> list = managementDao.getListManagement(start, end, isActive);
                for (Management management : list) {
                    fillManagement(management);
                }
                return list;
            } catch (DaoException e) {
                log.warn("Can't get list management by active/inactive  range {} to {} ", start, end, e);
                throw new ServiceException("Can't get list management", e);
            }
        }
    }

    /**
     * Method, provides the number of  active/inactive managements
     *
     * @param isActive - active/inactive managements
     * @return Return count of active/inactive managements
     */
    public int getManagementCount(boolean isActive) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                ManagementDao managementDao = (ManagementDao) daoFactory.getDao(daoFactory.typeDao().getManagementDao());
                return managementDao.getManagementCount(isActive);
            } catch (DaoException e) {
                log.warn("Can't get count management by activity where activity equals {}", isActive, e);
                throw new ServiceException("can't get count book", e);
            }
        }
    }

    /**
     * Method, fill the transaction entity of the data associated with transaction
     *
     * @param management - entity
     */
    private void fillManagement(Management management) throws ServiceException {
        TransactionService transactionService = new TransactionService();
        CustomerService customerService = new CustomerService();
        Customer customer;
        if (management != null) {
            try (DaoFactory daoFactory = new DaoFactory()) {
                try {
                    log.debug("Fill book with information");

                    TransactionDao transactionDao = (TransactionDao) daoFactory.getDao(daoFactory.typeDao().getTransactionDao());
                    customer = customerService.findCustomerByManagement(management);
                    Transaction transaction = transactionDao.findByManagement(management);
                    transactionService.fillTransaction(transaction);
                    transaction.setCustomer(customer);
                    management.setTransaction(transaction);
                } catch (DaoException e) {
                    log.warn("Can't fill book with information ", e);
                }
            }
        }
    }

}
