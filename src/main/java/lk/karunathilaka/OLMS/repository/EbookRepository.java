package lk.karunathilaka.OLMS.repository;

import lk.karunathilaka.OLMS.bean.BookBean;
import lk.karunathilaka.OLMS.bean.EbookBeen;
import lk.karunathilaka.OLMS.db.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EbookRepository {
    public static boolean setEbook(EbookBeen ebookBeen){
        boolean result = false;
        Connection conn = null;
        PreparedStatement ps = null;
        int rs = 0;

        try{
            conn = DBConnectionPool.getInstance().getConnection();
            ps = conn.prepareStatement("INSERT INTO ebook (bookID, isbn, title, author, category, pages, availability, pdfPath, imagePath) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, ebookBeen.getBookID());
            ps.setString(2, ebookBeen.getIsbn());
            ps.setString(3, ebookBeen.getTitle());
            ps.setString(4, ebookBeen.getAuthor());
            ps.setString(5, ebookBeen.getCategory());
            ps.setString(6, ebookBeen.getPages());
            ps.setString(7, ebookBeen.getAvailability());
            ps.setString(8, ebookBeen.getPdfPath());
            ps.setString(9, ebookBeen.getImagePath());

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
}
