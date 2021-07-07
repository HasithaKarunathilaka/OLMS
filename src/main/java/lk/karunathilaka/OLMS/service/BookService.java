package lk.karunathilaka.OLMS.service;

import com.google.gson.JsonArray;
import lk.karunathilaka.OLMS.bean.BookBean;
import lk.karunathilaka.OLMS.repository.BookRepository;

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
        System.out.println("Service start");
        String title = bookBean.getTitle();
        String author = bookBean.getAuthor();
        title = "%" + title + "%";
        author = "%" + author + "%";
        bookBean.setTitle(title);
        bookBean.setAuthor(author);

        JsonArray result = BookRepository.searchBook(bookBean);
        System.out.println("Service end");

        return result;
    }
}
