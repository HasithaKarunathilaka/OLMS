package lk.karunathilaka.OLMS.service;

import com.google.gson.JsonArray;
import lk.karunathilaka.OLMS.bean.BookBean;
import lk.karunathilaka.OLMS.bean.BorrowBean;
import lk.karunathilaka.OLMS.repository.BookRepository;
import lk.karunathilaka.OLMS.repository.BorrowRepository;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BookService {
    public String setBook(BookBean bookBean){
        boolean result = BookRepository.setBook(bookBean);

        if(result) {
            return "success";

        }else {
            return "error";

        }

    }

    public String updateBook(BookBean bookBean){
        boolean result = BookRepository.updateBook(bookBean);

        if(result) {
            return "success";

        }else {
            return "error";

        }

    }

    public String deleteBook(BookBean bookBean){
        boolean result = BookRepository.deleteBook(bookBean);

        if(result) {
            return "success";

        }else {
            return "error";

        }

    }

    public Object searchBook(BookBean bookBean){
//        System.out.println("Service start");
        String title = bookBean.getTitle();
        String author = bookBean.getAuthor();
        title = "%" + title + "%";
        author = "%" + author + "%";
        bookBean.setTitle(title);
        bookBean.setAuthor(author);

        JsonArray result = BookRepository.searchBook(bookBean);
//        System.out.println("Service end");

        return result;
    }

    public String borrowBook(BorrowBean borrowBean){

//        borrowBean.setBorrowedDate(new Date(new java.util.Date().getTime()).toString());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        borrowBean.setBorrowedDate(simpleDateFormat.format(calendar.getTime()));

        calendar.add(Calendar.DAY_OF_MONTH,14);
        borrowBean.setDueDate(simpleDateFormat.format(calendar.getTime()));

        boolean result = BorrowRepository.setBorrowDetails(borrowBean);

        if(result) {
            BookBean bookBean = new BookBean(borrowBean.getBookIDBorrow(), "Issued");
            bookBean.setIsbn("borrowBook");
            boolean updateResult = BookRepository.updateBook(bookBean);

            if(updateResult){
                return "success";

            }else{
                return "error while updating Book Repo";
            }

        }else {
            return "error while inserting Borrow Repo";

        }

    }
}
