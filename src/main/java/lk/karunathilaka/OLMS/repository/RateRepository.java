package lk.karunathilaka.OLMS.repository;

import lk.karunathilaka.OLMS.bean.RateBean;
import lk.karunathilaka.OLMS.db.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            ps.setLong(4, rateBean.getTime());
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

    public static boolean updateRate(RateBean rateBean){
        boolean result = false;
        Connection conn = null;
        PreparedStatement ps = null;
        int rs = 0;

        try{
            conn = DBConnectionPool.getInstance().getConnection();

            ps = conn.prepareStatement("UPDATE rate SET rate = ?, time = ?, page = ? WHERE bookIDRate = ? AND memberIDRate = ?");

            ps.setInt(1, rateBean.getRate());
            ps.setLong(2, rateBean.getTime());
            ps.setInt(3, rateBean.getPage());
            ps.setString(4, rateBean.getBookIDRate());
            ps.setString(5, rateBean.getMemberIDRate());

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

    public static String[] getRate(RateBean rateBean){
        String[] result = new String[]{"0", "0"};
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
//        boolean result = false;

        try{
            conn = DBConnectionPool.getInstance().getConnection();
            ps = conn.prepareStatement("SELECT * FROM rate WHERE memberIDRate = ? AND bookIDRate = ?");
            ps.setString(1, rateBean.getMemberIDRate());
            ps.setString(2, rateBean.getBookIDRate());
            rs = ps.executeQuery();

            while(rs.next()){
                result[0] = String.valueOf(rs.getLong("time"));
                result[1] = String.valueOf(rs.getInt("page"));
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
}
