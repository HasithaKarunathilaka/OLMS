package lk.karunathilaka.OLMS.repository;

import lk.karunathilaka.OLMS.bean.BookBean;
import lk.karunathilaka.OLMS.bean.ReadBean;
import lk.karunathilaka.OLMS.db.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReadRepository {
    public static boolean setRead(ReadBean readBean){
        boolean result = false;
        Connection conn = null;
        PreparedStatement ps = null;
        int rs = 0;

        try{
            conn = DBConnectionPool.getInstance().getConnection();
            ps = conn.prepareStatement("INSERT INTO read (bookIDRead, totReadpages, avgTimeForPage, totNumberOfViews, maxPage, maxTime) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, readBean.getBookIDRead());
            ps.setInt(2, readBean.getTotReadPages());
            ps.setInt(3, readBean.getAvgTimeForPage());
            ps.setInt(4, readBean.getTotNumberOfViews());
            ps.setInt(5, readBean.getMaxPage());
            ps.setInt(6, readBean.getMaxTime());

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
