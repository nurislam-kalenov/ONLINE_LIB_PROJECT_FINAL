package nuris.epam.entity;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author Kalenov Nurislam
 */
public class Transaction extends BaseEntity {

    private Date startDate;

    private Timestamp endDate;

    private BookInfo bookInfo;

    private Customer customer;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public BookInfo getBookInfo() {
        return bookInfo;
    }

    public void setBookInfo(BookInfo bookInfo) {
        this.bookInfo = bookInfo;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


}
