package lk.karunathilaka.OLMS.service;

import com.google.gson.JsonArray;
import lk.karunathilaka.OLMS.bean.BookBean;
import lk.karunathilaka.OLMS.bean.BorrowBean;
import lk.karunathilaka.OLMS.repository.BookRepository;
import lk.karunathilaka.OLMS.repository.BorrowRepository;

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

    public JsonArray searchBook(BookBean bookBean){
//        System.out.println("Service start");
        String title = bookBean.getTitle();
        String author = bookBean.getAuthor();
        title = "%" + title + "%";
        author = "%" + author + "%";
        bookBean.setTitle(title);
        bookBean.setAuthor(author);

        if(bookBean.getAvailability().equals("all")){
            bookBean.setAvailability("");
        }

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

        JsonArray bookDetail = BookRepository.searchBook(bookBean);

        if(bookDetail.size() == 0){
            return "ERROR! \nInvalid Book ID";
        }
//        JsonParser jsonParser = new JsonParser();
//        JsonObject bookDetails = bookDetail.get(0).getAsJsonObject();
        String availability = bookDetail.get(0).getAsJsonObject().get("availability").getAsString();

//        String availability = "test";
//        String availability = String.valueOf(bookDetails.get(6));
//        System.out.println(availability);
//        String availability = String.valueOf(bookDetails.get("availability"));

//        try {
//            JsonObject bookDetails = (JsonObject) jsonParser.parse(String.valueOf(bookDetail.get(0)));
//            availability = (bookDetails.get("availability").toString());
//
//        }catch (JsonParseException e){
//            e.printStackTrace();
//        }

        if(availability.equals("available")){
            boolean resultBorrowRepo = BorrowRepository.setBorrowDetails(borrowBean);

            if(resultBorrowRepo) {
//            bookBean.setBookID(borrowBean.getBookIDBorrow());
                bookBean.setAvailability("issued");
                bookBean.setIsbn("borrowBook");
                boolean updateResult = BookRepository.updateBook(bookBean);

                if(updateResult){
                    return "success";

                }else{
                    return "ERROR! \nerror while updating Book Repo";
                }

            }else {
                return "ERROR! \nInvalid Member ID";

            }

        }else {
            return "ERROR! \nerror book is " + availability;

        }
    }

    public JsonArray returnBook(BorrowBean borrowBean){
        System.out.println("start service");
//        JsonArray finalResult = new JsonArray();
        String[] finalResult = new String[2];
        boolean selectBorrowBook = BorrowRepository.getBorrowDetail(borrowBean, "returning");

        if(selectBorrowBook){
            System.out.println("if select borrow book");
            String resultDateCheck = null;

//            ------  Get Today Date  ------
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            borrowBean.setReturnedDate(simpleDateFormat.format(calendar.getTime()));

            CalculationService calculationService = new CalculationService();

            try {
                System.out.println("try");
                resultDateCheck = calculationService.checkDateBefore(borrowBean.getDueDate(), borrowBean.getReturnedDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(resultDateCheck.equals("before")){
                System.out.println("date check");
                int resultNumberOfDays = 0;

                try {
                    System.out.println("try cal daTE DIF");
                    resultNumberOfDays = calculationService.calculateDateDifference(borrowBean.getDueDate(), borrowBean.getReturnedDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int fineAmount = calculationService.calculateFine(resultNumberOfDays);
                borrowBean.setFine(fineAmount);
                System.out.println("cal fine");

            }else{
                System.out.println("if 1 else");
                borrowBean.setFine(0);
            }

            boolean returnBorrowBook = BorrowRepository.updateBorrowDetails(borrowBean, "bookReturn");

            if(returnBorrowBook){
                BookBean bookBean = new BookBean(borrowBean.getBookIDBorrow(), "available");
                bookBean.setIsbn("returning");
                boolean resultBookRepoAvailability = BookRepository.updateBook(bookBean);

                if(resultBookRepoAvailability){
//                    finalResult.add("success");
//                    finalResult.add(borrowBean.getFine());
                    finalResult[0] = "success";
                    finalResult[1] = String.valueOf(borrowBean.getFine());

                }else{
//                    finalResult.add("error");
//                    finalResult.add("error while updating book repo availability");
                    finalResult[0] = "Error!";
                    finalResult[1] = "error while updating book repo availability";

                }

            }else{
//                finalResult.add("error");
//                finalResult.add("error while updating returned book");
                finalResult[0] = "Error!";
                finalResult[1] = "error while updating returned book";

            }

        }else {
//            finalResult.add("error");
//            finalResult.add("error while selecting borrowed book");
            finalResult[0] = "Error!";
            finalResult[1] = borrowBean.getBookIDBorrow() + " is Not Borrowed or Invalid Book ID";

        }
        JsonArray test = new JsonArray();
        test.add(finalResult[0]);
        test.add(finalResult[1]);
        return test;
    }
}
