package nuris.epam.services;

import nuris.epam.dao.*;
import nuris.epam.dao.exception.DaoException;
import nuris.epam.dao.manager.DaoFactory;
import nuris.epam.entity.*;
import nuris.epam.services.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class - service, perform all manipulations and transactions associated with the transaction.
 *
 * @author Kalenov Nurislam
 */
public class TransactionService {
    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);
    /**
     * Field - count of book which can increase or decrease for one take/return book transaction
     */
    private static final int ONE_BOOK = 1;
    /**
     * The maximum number of books that can be taken by the client
     */
    public static final int MAX_COUNT_BOOKS = 5;
    /**
     * The minimum number of books that can be taken by the client
     */
    public static final int MIN_COUNT_BOOKS = 0;

    /**
     * Method, intended to provide take book by the customer
     *
     * @param transaction - entity with contain customer and bookInfo id
     * @return Return transaction with id
     */
    public Transaction customerTakeBook(Transaction transaction) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                log.debug("Start customer executed transaction for Take book where customer id equals {} ", transaction.getCustomer().getId());

                BookService bookService = new BookService();
                BookInfo bookInfo = bookService.findBookInfoById(transaction.getBookInfo().getId());
                BookInfoDao bookInfoDao = (BookInfoDao) daoFactory.getDao(daoFactory.typeDao().getBookInfoDao());
                TransactionDao transactionDao = (TransactionDao) daoFactory.getDao(daoFactory.typeDao().getTransactionDao());
                BookDao bookDao = (BookDao) daoFactory.getDao(daoFactory.typeDao().getBookDao());
                Book book = bookDao.findByBookInfo(bookInfo);
                bookInfo.setBook(book);

                if (bookInfo.getAmount() > MIN_COUNT_BOOKS && !isBookInTransactionAlreadyTaken(transaction) && countActiveTransaction(transaction) < MAX_COUNT_BOOKS) {
                    log.debug("Customer passed checking for execute transaction(take book) where customer id equals {} ", transaction.getCustomer().getId());
                    bookInfo.setAmount(bookInfo.getAmount() - ONE_BOOK);
                    daoFactory.startTransaction();
                    bookInfoDao.update(bookInfo);
                    transaction = transactionDao.insert(transaction);
                    daoFactory.commitTransaction();
                }
                log.info("Customer executed transaction for Take book where customer id equals {} ", transaction.getCustomer().getId());
                return transaction;

            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                log.warn("Can't take book  where customer id equals {}", transaction.getCustomer().getId(), e);
                throw new ServiceException("Can't take book ", e);
            }
        }
    }

    /**
     * Method, intended to provide return book by the customer
     *
     * @param transaction - entity with contain bookInfo id
     * @param customer    -  entity with contain customer id
     * @return Return transaction with id
     */
    public Transaction customerReturnBook(Transaction transaction, Customer customer) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                log.debug("Start customer passed checking for execute transaction(return book)");

                Management management = new Management();

                TransactionDao transactionDao = (TransactionDao) daoFactory.getDao(daoFactory.typeDao().getTransactionDao());
                ManagementDao managementDao = (ManagementDao) daoFactory.getDao(daoFactory.typeDao().getManagementDao());
                BookInfoDao bookInfoDao = (BookInfoDao) daoFactory.getDao(daoFactory.typeDao().getBookInfoDao());

                BookInfo bookInfo = bookInfoDao.findByTransaction(transaction);
                transaction = transactionDao.findById(transaction.getId());
                management.setTransaction(transaction);
                transaction.setBookInfo(bookInfo);
                transaction.setCustomer(customer);

                if (transaction.getEndDate() == null) {
                    log.debug("Customer passed checking for execute transaction(return book) where customer id equals {} ", transaction.getCustomer().getId());
                    transaction.setEndDate(Timestamp.valueOf(LocalDateTime.now()));
                    daoFactory.startTransaction();
                    transactionDao.update(transaction);
                    managementDao.insert(management);
                    daoFactory.commitTransaction();
                }
                log.info("Customer executed transaction for Return book where customer id equals {} ", transaction.getCustomer().getId());
                return transaction;
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                log.warn("Can't return book  where customer id equals {}", transaction.getCustomer().getId(), e);
                throw new ServiceException("can't return book ", e);
            }
        }
    }

    /**
     * Method, provides transactions found by the customer
     *
     * @param transaction - need to contain customer entity with id
     * @return Return list of transaction
     */
    public List<Transaction> findTransactionsByCustomer(Transaction transaction) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                TransactionDao transactionDao = (TransactionDao) daoFactory.getDao(daoFactory.typeDao().getTransactionDao());
                List<Transaction> list = transactionDao.findByCustomer(transaction);
                for (Transaction tran : list) {
                    fillTransaction(tran);
                }
                return list;
            } catch (DaoException e) {
                log.warn("Can't find transaction by customer where customer id equals {}", transaction.getCustomer().getId(), e);
                throw new ServiceException("can't get list of customer", e);
            }
        }
    }

    /**
     * Method, provides a list of active transactions of a specific customer
     * (Active transactions - the customer has not yet returned the book to the manager)
     *
     * @param transaction - need to contain customer entity
     * @return Return list of transaction
     */
    public List<Transaction> getActiveCustomerTransaction(Transaction transaction) throws ServiceException {
        List<Transaction> transactions = new ArrayList<>();
        List<Transaction> list = findTransactionsByCustomer(transaction);
        for (Transaction tran : list) {
            if (tran.getEndDate() == null) {
                transactions.add(tran);
            }
        }
        log.debug("Get active customer by transaction");
        return transactions;
    }

    /**
     * Method, provides a list of active and inactive transactions in the range of rows in the table
     * (Active transactions - the customer has not yet returned the book to the manager)
     *
     * @param transaction - need to contain customer entity
     * @param start       - the row from which you must begin
     * @param end         - the row from which you must finish
     * @param isActive    - active/inactive transaction state
     */
    public List<Transaction> getListTransaction(Transaction transaction, int start, int end, boolean isActive) throws ServiceException {
        List<Transaction> list;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                TransactionDao transactionDao = (TransactionDao) daoFactory.getDao(daoFactory.typeDao().getTransactionDao());
                list = transactionDao.getListTransactionByCustomer(transaction, start, end, isActive);
                for (Transaction trans : list) {
                    fillTransaction(trans);
                }
                return list;
            } catch (DaoException e) {
                log.warn("Can't get list transaction by active/inactive where transaction id equals {} and range {} to {} ", transaction.getId(), start, end, e);
                throw new ServiceException("can't get list management", e);
            }
        }
    }

    /**
     * Method, provides the number of active and inactive transactions by customer
     *
     * @param transaction - need to contain customer entity
     * @param isActive    - active/inactive transaction state
     */
    public int getTransactionCountByCustomer(Transaction transaction, boolean isActive) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                TransactionDao transactionDao = (TransactionDao) daoFactory.getDao(daoFactory.typeDao().getTransactionDao());
                return transactionDao.getTransactionCountByCustomer(transaction, isActive);
            } catch (DaoException e) {
                log.warn("Can't get transaction count by customer and active/inactive where customer id equals {} ", transaction.getCustomer().getId(), e);
                throw new ServiceException("can't get count transaction by customer", e);
            }
        }
    }

    /**
     * Checks whether a specific book is on hand or not
     *
     * @param transaction - need to contain customer entity
     * @return Return boolean value - On hands or not (true/false)
     */
    public boolean isBookInTransactionAlreadyTaken(Transaction transaction) {
        List<Transaction> transactions;
        try {
            transactions = getActiveCustomerTransaction(transaction);
            for (Transaction tran : transactions) {
                if (tran.getBookInfo().getId() == transaction.getBookInfo().getId()) {
                    return true;
                }
            }
        } catch (ServiceException e) {
            log.warn("Can't check book for already taken where book id equals {}", transaction.getBookInfo().getId(), e);
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method, provides the number of just active transactions
     *
     * @param transaction - need to contain customer entity
     * @return Return count of active transaction
     */
    public int countActiveTransaction(Transaction transaction) {
        int size = 0;
        try {
            size = getActiveCustomerTransaction(transaction).size();
        } catch (ServiceException e) {
            log.warn("Can't get count active transaction where transaction id equals {}", transaction.getId(), e);
            e.printStackTrace();
        }
        return size;
    }

    /**
     * Method, fill the transaction entity of the data associated with transaction
     *
     * @param transaction - entity
     */
    public void fillTransaction(Transaction transaction) throws ServiceException {
        BookInfo bookInfo;
        Book book;
        try {
            if (transaction != null) {
                log.debug("Fill book with information");

                try (DaoFactory daoFactory = new DaoFactory()) {
                    BookInfoDao bookInfoDao = (BookInfoDao) daoFactory.getDao(daoFactory.typeDao().getBookInfoDao());
                    BookDao bookDao = (BookDao) daoFactory.getDao(daoFactory.typeDao().getBookDao());
                    bookInfo = bookInfoDao.findByTransaction(transaction);
                    book = bookDao.findByBookInfo(bookInfo);
                    bookInfo.setBook(book);
                    transaction.setBookInfo(bookInfo);
                }
            }
        } catch (DaoException e) {
            log.warn("Can't fill book with information ", e);
            throw new ServiceException("Can't fill transaction ", e);
        }
    }

}
