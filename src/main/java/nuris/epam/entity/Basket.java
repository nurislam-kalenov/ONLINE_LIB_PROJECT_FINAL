package nuris.epam.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kalenov Nurislam
 */
public class Basket implements Serializable {

    private List<BookInfo> books = new ArrayList<>();

    public void add(BookInfo book) {
        books.add(book);
    }

    public void delete(int id) {
        BookInfo bookInfo = new BookInfo();
        bookInfo.setId(id);
        if (books.contains(bookInfo)) {
            books.remove(bookInfo);
        }
    }

    public void deleteAll() {
        books.removeAll(books);
    }

    public List<BookInfo> getBooks() {
        return books;
    }
}
