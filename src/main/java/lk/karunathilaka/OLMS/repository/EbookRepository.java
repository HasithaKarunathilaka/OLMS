package lk.karunathilaka.OLMS.repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lk.karunathilaka.OLMS.bean.BookBean;
import lk.karunathilaka.OLMS.bean.EbookBeen;
import lk.karunathilaka.OLMS.db.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EbookRepository {
    public static boolean setEbook(EbookBeen ebookBeen){
        boolean result = false;
        Connection conn = null;
        PreparedStatement ps = null;
        int rs = 0;

        try{
            conn = DBConnectionPool.getInstance().getConnection();
            ps = conn.prepareStatement("INSERT INTO ebook (bookID, isbn, title, author, category, pages, availability, pdfPath, imagePath, publisherID, publishedDate, avgRate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, ebookBeen.getBookID());
            ps.setString(2, ebookBeen.getIsbn());
            ps.setString(3, ebookBeen.getTitle());
            ps.setString(4, ebookBeen.getAuthor());
            ps.setString(5, ebookBeen.getCategory());
            ps.setString(6, ebookBeen.getPages());
            ps.setString(7, ebookBeen.getAvailability());
            ps.setString(8, ebookBeen.getPdfPath());
            ps.setString(9, ebookBeen.getImagePath());
            ps.setString(10, ebookBeen.getPublisherID());
            ps.setString(11, ebookBeen.getPublishedDate());
            ps.setDouble(12, ebookBeen.getAvgRate());

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

    public static boolean updateEbook(EbookBeen ebookBeen, String accessType){
        boolean result = false;
        Connection conn = null;
        PreparedStatement ps = null;
        int rs = 0;

        try{
            conn = DBConnectionPool.getInstance().getConnection();

            if(accessType.equals("approval")){
                ps = conn.prepareStatement("UPDATE ebook SET availability = ? WHERE bookID = ?");

                ps.setString(1, ebookBeen.getAvailability());
                ps.setString(2, ebookBeen.getBookID());
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

    public static JsonArray searchEbook(EbookBeen ebookBeen){
//        System.out.println("repo start");
        JsonArray searchResultBooks = new JsonArray();
        boolean result = false;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = DBConnectionPool.getInstance().getConnection();

            if(ebookBeen.equals("all")){
                if(ebookBeen.getBookID() == ""){
//                    System.out.println("3");
                    ps = conn.prepareStatement("SELECT * FROM ebook WHERE title LIKE ? AND author LIKE ? AND availability = ?");
                    ps.setString(1, ebookBeen.getTitle());
                    ps.setString(2, ebookBeen.getAuthor());
                    ps.setString(3, ebookBeen.getAvailability());

                }else{
                    ps = conn.prepareStatement("SELECT * FROM ebook WHERE title LIKE ? AND author LIKE ? AND availability = ? AND (isbn = ? OR bookID = ?)");
                    ps.setString(1, ebookBeen.getTitle());
                    ps.setString(2, ebookBeen.getAuthor());
                    ps.setString(3, ebookBeen.getAvailability());
                    ps.setString(4, ebookBeen.getIsbn());
                    ps.setString(5, ebookBeen.getBookID());

                }

            }else{
                if(ebookBeen.getBookID() == ""){
//                    System.out.println("7");
                    ps = conn.prepareStatement("SELECT * FROM ebook WHERE title LIKE ? AND author LIKE ? AND availability = ? AND category = ?");
                    ps.setString(1, ebookBeen.getTitle());
                    ps.setString(2, ebookBeen.getAuthor());
                    ps.setString(3, ebookBeen.getAvailability());
                    ps.setString(4, ebookBeen.getCategory());

                }else{
//                    System.out.println("8");
                    ps = conn.prepareStatement("SELECT * FROM book WHERE title LIKE ? AND author LIKE ? AND availability = ? AND category = ? AND (isbn = ? OR bookID = ?)");
                    ps.setString(1, ebookBeen.getTitle());
                    ps.setString(2, ebookBeen.getAuthor());
                    ps.setString(3, ebookBeen.getAvailability());
                    ps.setString(4, ebookBeen.getCategory());
                    ps.setString(5, ebookBeen.getBookID());
                    ps.setString(6, ebookBeen.getBookID());

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
