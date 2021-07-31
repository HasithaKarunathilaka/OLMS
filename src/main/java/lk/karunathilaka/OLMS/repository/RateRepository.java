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
        int rateValue = rateBean.getRate();
        int rs = 0;

        try{
            conn = DBConnectionPool.getInstance().getConnection();
            if(rateValue > 0){
                ps = conn.prepareStatement("UPDATE rate SET rate = ? WHERE bookIDRate = ? AND memberIDRate = ?");

                ps.setInt(1, rateBean.getRate());
                ps.setString(2, rateBean.getBookIDRate());
                ps.setString(3, rateBean.getMemberIDRate());

            }else {
                ps = conn.prepareStatement("UPDATE rate SET time = ?, page = ? WHERE bookIDRate = ? AND memberIDRate = ?");

                ps.setLong(1, rateBean.getTime());
                ps.setInt(2, rateBean.getPage());
                ps.setString(3, rateBean.getBookIDRate());
                ps.setString(4, rateBean.getMemberIDRate());

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

    public static Double getAllRateOfBook(RateBean rateBean){
        Double avgRate = 0.0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
//        boolean result = false;

        try{
            conn = DBConnectionPool.getInstance().getConnection();
            ps = conn.prepareStatement("SELECT rate FROM rate WHERE bookIDRate = ?");

            ps.setString(1, rateBean.getBookIDRate());
            rs = ps.executeQuery();

            while(rs.next()){
                avgRate = (avgRate + rs.getInt("rate")) / 2;
            }

        }catch (SQLException e){
            e.printStackTrace();

        }finally{
            DBConnectionPool.getInstance().close(rs);
            DBConnectionPool.getInstance().close(ps);
            DBConnectionPool.getInstance().close(conn);
        }
        return avgRate;
    }

    public static boolean getMemberStat(RateBean rateBean){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean result = false;

        try{
            conn = DBConnectionPool.getInstance().getConnection();
            ps = conn.prepareStatement("SELECT SUM(time) AS totalTime, SUM(page) AS totalPages FROM rate WHERE memberIDRate = ?");

            ps.setString(1, rateBean.getMemberIDRate());
            rs = ps.executeQuery();

            while(rs.next()){
                rateBean.setTime(rs.getLong("totalTime"));
                rateBean.setPage(rs.getInt("totalPages"));
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

    public static int totalReadBookCount(String memberID){
        int totalBookCount = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = DBConnectionPool.getInstance().getConnection();
            ps = conn.prepareStatement("SELECT COUNT(memberIDRate) AS totalBookCount FROM rate WHERE memberIDRate = ?");

            ps.setString(1, memberID);

            rs = ps.executeQuery();

            while(rs.next()){
                totalBookCount = rs.getInt("totalBookCount");
                System.out.println("while loop");
            }
            System.out.println("end member repo");
        }catch (SQLException e){
            e.printStackTrace();

        }finally{
            DBConnectionPool.getInstance().close(rs);
            DBConnectionPool.getInstance().close(ps);
            DBConnectionPool.getInstance().close(conn);
        }
        return totalBookCount;

    }
}
