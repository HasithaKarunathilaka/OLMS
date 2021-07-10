package lk.karunathilaka.OLMS.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonParseException;
import lk.karunathilaka.OLMS.bean.BookBean;
import lk.karunathilaka.OLMS.bean.BorrowBean;
import lk.karunathilaka.OLMS.repository.BookRepository;
import lk.karunathilaka.OLMS.repository.BorrowRepository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
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

//        ------- Set Current Date -------

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        borrowBean.setBorrowedDate(simpleDateFormat.format(calendar.getTime()));

//        ------ Calculate 14 days ------

        calendar.add(Calendar.DAY_OF_MONTH,14);
        borrowBean.setDueDate(simpleDateFormat.format(calendar.getTime()));

        BookBean bookBean = new BookBean();
        bookBean.setBookID(borrowBean.getBookIDBorrow());
        bookBean.setIsbn(borrowBean.getBookIDBorrow());
        bookBean.setTitle("%%");
        bookBean.setAuthor("%%");
        bookBean.setCategory("all");
        bookBean.setAvailability("");

        JsonArray boookDetial = BookRepository.searchBook(bookBean);
        JsonParser jsonParser = new JsonParser();
        String availability = "unavailable";

        try {
            JsonObject bookDetails = (JsonObject) jsonParser.parse(String.valueOf(boookDetial.get(0)));
            availability = (bookDetails.get("availability").toString());

        }catch (JsonParseException e){
            e.printStackTrace();
        }

        if(availability.equals("\"available\"")){
            boolean resultBorrowRepo = BorrowRepository.setBorrowDetails(borrowBean);

            if(resultBorrowRepo) {
//            bookBean.setBookID(borrowBean.getBookIDBorrow());
                bookBean.setAvailability("Issued");
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

        }else {
            return "error book is " + availability;

        }

    }
}
