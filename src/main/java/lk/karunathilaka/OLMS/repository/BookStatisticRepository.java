package lk.karunathilaka.OLMS.repository;

import lk.karunathilaka.OLMS.bean.BookStatisticBean;
import lk.karunathilaka.OLMS.db.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookStatisticRepository {
    public static boolean setRead(BookStatisticBean readBean){
        boolean result = false;
        Connection conn = null;
        PreparedStatement ps = null;
        int rs = 0;

        try{
            conn = DBConnectionPool.getInstance().getConnection();
            ps = conn.prepareStatement("INSERT INTO bookStatistic (bookIDRead, totReadpages, avgTimeForPage, totNumberOfViews, maxPage, maxTime) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, readBean.getBookIDRead());
            ps.setInt(2, readBean.getTotReadPages());
            ps.setLong(3, readBean.getAvgTimeForPage());
            ps.setInt(4, readBean.getTotNumberOfViews());
            ps.setInt(5, readBean.getMaxPage());
            ps.setLong(6, readBean.getMaxTime());

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

    public static boolean getBookStatistic(BookStatisticBean bookStatisticBean){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean result = false;

        try{
            conn = DBConnectionPool.getInstance().getConnection();
            ps = conn.prepareStatement("SELECT * FROM bookStatistic WHERE bookIDRead = ?");
            ps.setString(1, bookStatisticBean.getBookIDRead());
            rs = ps.executeQuery();

            while(rs.next()){
                bookStatisticBean.setAvgTimeForPage(rs.getInt("avgTimeForPage"));
                bookStatisticBean.setTotNumberOfViews(rs.getInt("totNumberOfViews"));
                bookStatisticBean.setTotReadPages(rs.getInt("totReadPages"));
                bookStatisticBean.setMaxPage(rs.getInt("maxPage"));
                bookStatisticBean.setMaxTime(rs.getInt("maxTime"));
                result = true;
            }

        }catch (SQLException e){
            e.printStackTrace();

        }finally{
            DBConnectionPool.getInstance().close(rs);
            DBConnectionPool.getInstance().close(ps);
            DBConnectionPool.getInstance().close(conn);
        }
        return result;
    }

    public static boolean updateBookStatistic(BookStatisticBean bookStatisticBean){
        boolean result = false;
        Connection conn = null;
        PreparedStatement ps = null;
        int rs = 0;

        try{
            conn = DBConnectionPool.getInstance().getConnection();

            ps = conn.prepareStatement("UPDATE bookStatistic SET totReadPages = ?, avgTimeForPage = ?, totNumberOfViews = ?, maxPage = ?, maxTime = ? WHERE bookIDRead = ?");

            ps.setInt(1, bookStatisticBean.getTotReadPages());
            ps.setLong(2, bookStatisticBean.getAvgTimeForPage());
            ps.setInt(3, bookStatisticBean.getTotNumberOfViews());
            ps.setInt(4, bookStatisticBean.getMaxPage());
            ps.setLong(5, bookStatisticBean.getMaxTime());
            ps.setString(6, bookStatisticBean.getBookIDRead());

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
