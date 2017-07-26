package nuris.epam.entity;

/**
 * @author Kalenov Nurislam
 */
public class BookInfo extends BaseEntity {

    private int amount;
    private int price;
    private Book book;

    public BookInfo() {
        book = new Book();
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

}
