package lk.karunathilaka.OLMS.repository;

import lk.karunathilaka.OLMS.bean.BookBean;
import lk.karunathilaka.OLMS.bean.RateBean;
import lk.karunathilaka.OLMS.db.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RateRepository {
    public static boolean setRate(RateBean rateBean){
        boolean result = false;
        Connection conn = null;
        PreparedStatement ps = null;
        int rs = 0;

        try{
            conn = DBConnectionPool.getInstance().getConnection();
            ps = conn.prepareStatement("INSERT INTO rate (bookIDRate, memberIDRate, rate, time, page) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, rateBean.getBookIDRate());
            ps.setString(2, rateBean.getMemberIDRate());
            ps.setInt(3, rateBean.getRate());
            ps.setTime(4, rateBean.getTime());
            ps.setInt(5, rateBean.getPage());

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
