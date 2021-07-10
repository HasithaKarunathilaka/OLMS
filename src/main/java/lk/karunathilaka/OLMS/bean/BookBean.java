package lk.karunathilaka.OLMS.bean;

import java.sql.Date;

public class BookBean {
    private String bookID;
    private String isbn;
    private String title;
    private String author;
    private String category;
    private String addedBy;
    private String addedDate;
    private String availability;

    public BookBean(String bookID, String isbn, String title, String author, String category, String addedBy, String addedDate, String availability) {
        this.bookID = bookID;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.category = category;
        this.addedBy = addedBy;
        this.addedDate = addedDate;
        this.availability = availability;
    }

    public BookBean(String bookID, String availability) {
        this.bookID = bookID;
        this.availability = availability;
    }

    public BookBean(String isbn, String category, String availability) {
        this.isbn = isbn;
        this.category = category;
        this.availability = availability;
    }

    public BookBean() {
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
