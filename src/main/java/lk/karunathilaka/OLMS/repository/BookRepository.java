package lk.karunathilaka.OLMS.repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lk.karunathilaka.OLMS.bean.BookBean;
import lk.karunathilaka.OLMS.db.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRepository {
    public static boolean setBook(BookBean bookBean){
        boolean result = false;
        Connection conn = null;
        PreparedStatement ps = null;
        int rs = 0;

        try{
            conn = DBConnectionPool.getInstance().getConnection();
            ps = conn.prepareStatement("INSERT INTO book (bookID, isbn, title, author, category, addedBy, addedDate, availability) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, bookBean.getBookID());
            ps.setString(2, bookBean.getIsbn());
            ps.setString(3, bookBean.getTitle());
            ps.setString(4, bookBean.getAuthor());
            ps.setString(5, bookBean.getCategory());
            ps.setString(6, bookBean.getAddedBy());
            ps.setString(7, bookBean.getAddedDate());
            ps.setString(8, bookBean.getAvailability());

            rs = ps.executeUpdate();
            if(rs > 0){
                result = true;
            }

        }catch (SQLException e){
            e.printStackTrace();

        }finally{
//            DBConnectionPool.getInstance().close(rs);
            DBConnectionPool.getInstance().close(ps);
            DBConnectionPool.getInstance().close(conn);
        }
        return result;
    }

    public static boolean updateBook(BookBean bookBean){
        boolean result = false;
        Connection conn = null;
        PreparedStatement ps = null;
        int rs = 0;

        try{
            conn = DBConnectionPool.getInstance().getConnection();

            if(bookBean.getIsbn().equals("borrowBook")){
                ps = conn.prepareStatement("UPDATE book SET availability = ? WHERE bookID = ?");

                ps.setString(1, bookBean.getAvailability());
                ps.setString(2, bookBean.getBookID());

            }else if(bookBean.getIsbn().equals("returning")){
                ps = conn.prepareStatement("UPDATE book SET availability = ? WHERE bookID = ?");

                ps.setString(1, bookBean.getAvailability());
                ps.setString(2, bookBean.getBookID());

            }else{
                ps = conn.prepareStatement("UPDATE book SET isbn = ?, title = ?, author = ?, category = ?, addedBy = ?, addedDate = ?, availability = ? WHERE bookID = ?");

                ps.setString(1, bookBean.getIsbn());
                ps.setString(2, bookBean.getTitle());
                ps.setString(3, bookBean.getAuthor());
                ps.setString(4, bookBean.getCategory());
                ps.setString(5, bookBean.getAddedBy());
                ps.setString(6, bookBean.getAddedDate());
                ps.setString(7, bookBean.getAvailability());
                ps.setString(8, bookBean.getBookID());

            }

            rs = ps.executeUpdate();
            if(rs > 0){
                result = true;
            }

        }catch (SQLException e){
            e.printStackTrace();

        }finally{
//            DBConnectionPool.getInstance().close(rs);
            DBConnectionPool.getInstance().close(ps);
            DBConnectionPool.getInstance().close(conn);
        }
        return result;
    }

    public static boolean deleteBook(BookBean bookBean){
        boolean result = false;
        Connection conn = null;
        PreparedStatement ps = null;
        int rs = 0;

        try{
            conn = DBConnectionPool.getInstance().getConnection();
            ps = conn.prepareStatement("UPDATE book SET addedBy = ?, addedDate = ?, availability = ? WHERE bookID = ?");

            ps.setString(1, bookBean.getAddedBy());
            ps.setString(2, bookBean.getAddedDate());
            ps.setString(3, bookBean.getAvailability());
            ps.setString(4, bookBean.getBookID());

            rs = ps.executeUpdate();
            if(rs > 0){
                result = true;
            }

        }catch (SQLException e){
            e.printStackTrace();

        }finally{
//            DBConnectionPool.getInstance().close(rs);
            DBConnectionPool.getInstance().close(ps);
            DBConnectionPool.getInstance().close(conn);
        }
        return result;
    }

    public static JsonArray searchBook(BookBean bookBean){
//        System.out.println("repo start");
        JsonArray searchResultBooks = new JsonArray();
        boolean result = false;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = DBConnectionPool.getInstance().getConnection();

            if(bookBean.getCategory().equals("all")){
                if(bookBean.getAvailability() == "" && bookBean.getIsbn() == ""){
//                    System.out.println("1");
                    ps = conn.prepareStatement("SELECT * FROM book WHERE title LIKE ? AND author LIKE ? ");
                    ps.setString(1, bookBean.getTitle());
                    ps.setString(2, bookBean.getAuthor());

                }else if(bookBean.getAvailability() == ""){
//                    System.out.println("2");
                    ps = conn.prepareStatement("SELECT * FROM book WHERE title LIKE ? AND author LIKE ? AND (isbn = ? OR bookID = ?)");
                    ps.setString(1, bookBean.getTitle());
                    ps.setString(2, bookBean.getAuthor());
                    ps.setString(3, bookBean.getIsbn());
                    ps.setString(4, bookBean.getIsbn());

                }else if(bookBean.getIsbn() == ""){
//                    System.out.println("3");
                    ps = conn.prepareStatement("SELECT * FROM book WHERE title LIKE ? AND author LIKE ? AND availability = ?");
                    ps.setString(1, bookBean.getTitle());
                    ps.setString(2, bookBean.getAuthor());
                    ps.setString(3, bookBean.getAvailability());

                }else{
                    ps = conn.prepareStatement("SELECT * FROM book WHERE title LIKE ? AND author LIKE ? AND availability = ? AND (isbn = ? OR bookID = ?)");
                    ps.setString(1, bookBean.getTitle());
                    ps.setString(2, bookBean.getAuthor());
                    ps.setString(3, bookBean.getAvailability());
                    ps.setString(4, bookBean.getIsbn());
                    ps.setString(5, bookBean.getIsbn());

                }

            }else{
                if(bookBean.getAvailability() == "" && bookBean.getIsbn() == ""){
//                    System.out.println("4");
                    ps = conn.prepareStatement("SELECT * FROM book WHERE title LIKE ? AND author LIKE ? AND category = ?");
                    ps.setString(1, bookBean.getTitle());
                    ps.setString(2, bookBean.getAuthor());
                    ps.setString(3, bookBean.getCategory());

                }else if(bookBean.getAvailability() == ""){
//                    System.out.println("6");
                    ps = conn.prepareStatement("SELECT * FROM book WHERE title LIKE ? AND author LIKE ? AND category = ? AND (isbn = ? OR bookID = ?)");
                    ps.setString(1, bookBean.getTitle());
                    ps.setString(2, bookBean.getAuthor());
                    ps.setString(3, bookBean.getCategory());
                    ps.setString(4, bookBean.getIsbn());
                    ps.setString(5, bookBean.getIsbn());

                }else if(bookBean.getIsbn() == ""){
//                    System.out.println("7");
                    ps = conn.prepareStatement("SELECT * FROM book WHERE title LIKE ? AND author LIKE ? AND availability = ? AND category = ?");
                    ps.setString(1, bookBean.getTitle());
                    ps.setString(2, bookBean.getAuthor());
                    ps.setString(3, bookBean.getAvailability());
                    ps.setString(4, bookBean.getCategory());

                }else{
//                    System.out.println("8");
                    ps = conn.prepareStatement("SELECT * FROM book WHERE title LIKE ? AND author LIKE ? AND availability = ? AND category = ? AND (isbn = ? OR bookID = ?)");
                    ps.setString(1, bookBean.getTitle());
                    ps.setString(2, bookBean.getAuthor());
                    ps.setString(3, bookBean.getAvailability());
                    ps.setString(4, bookBean.getCategory());
                    ps.setString(5, bookBean.getIsbn());
                    ps.setString(6, bookBean.getIsbn());

                }

            }

            rs = ps.executeQuery();

            while (rs.next()){
                JsonObject bookDetails = new JsonObject();
                bookDetails.addProperty("bookID", rs.getString("bookID"));
                bookDetails.addProperty("isbn", rs.getString("isbn"));
                bookDetails.addProperty("title", rs.getString("title"));
                bookDetails.addProperty("author", rs.getString("author"));
                bookDetails.addProperty("category", rs.getString("category"));
                bookDetails.addProperty("addedBy", rs.getString("addedBy"));
                bookDetails.addProperty("addedDate", rs.getString("addedDate"));
                bookDetails.addProperty("availability", rs.getString("availability"));
                searchResultBooks.add(bookDetails);
            }

        }catch (SQLException e){
            e.printStackTrace();

        }finally{
            DBConnectionPool.getInstance().close(rs);
            DBConnectionPool.getInstance().close(ps);
            DBConnectionPool.getInstance().close(conn);
        }
        return searchResultBooks;
    }
}
