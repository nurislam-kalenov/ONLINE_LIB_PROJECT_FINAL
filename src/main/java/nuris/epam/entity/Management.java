package nuris.epam.entity;

import java.sql.Timestamp;

/**
 * @author Kalenov Nurislam
 */
public class Management extends BaseEntity {
    private Transaction transaction;
    private Timestamp returnDate;

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Timestamp getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Timestamp returnDate) {
        this.returnDate = returnDate;
    }

}
