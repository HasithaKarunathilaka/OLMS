package lk.karunathilaka.OLMS.repository;

import lk.karunathilaka.OLMS.bean.BorrowBean;
import lk.karunathilaka.OLMS.db.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BorrowRepository {
    public static boolean setBorrowDetails(BorrowBean borrowBean){
        boolean result = false;
        Connection conn = null;
        PreparedStatement ps = null;
        int rs = 0;

        try{
            conn = DBConnectionPool.getInstance().getConnection();
            ps = conn.prepareStatement("INSERT INTO borrow (bookIDBorrow, memberIDBorrow, borrowedDate, duedate, issuedBy) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, borrowBean.getBookIDBorrow());
            ps.setString(2, borrowBean.getMemberIDBorrow());
            ps.setString(3, borrowBean.getBorrowedDate());
            ps.setString(4, borrowBean.getDueDate());
            ps.setString(5, borrowBean.getIssuedBy());

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

    public static boolean getBorrowDetail(BorrowBean borrowBean, String type){
        boolean result = false;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = DBConnectionPool.getInstance().getConnection();

            if(type.equals("returning")){
                ps = conn.prepareStatement("SELECT * FROM borrow WHERE bookIDBorrow = ? and returnedDate IS NULL ");
                ps.setString(1, borrowBean.getBookIDBorrow());

            }

            rs = ps.executeQuery();
            if(rs.next()){
                result = true;
                borrowBean.setBookIDBorrow(rs.getString("bookIDBorrow"));
                borrowBean.setMemberIDBorrow(rs.getString("memberIDBorrow"));
                borrowBean.setBorrowedDate(rs.getString("borrowedDate"));
                borrowBean.setDueDate(rs.getString("dueDate"));
                borrowBean.setIssuedBy(rs.getString("issuedBy"));
                borrowBean.setReturnedDate(rs.getString("returnedDate"));
                borrowBean.setFine(Integer.parseInt(rs.getString("fine")));
//                borrowBean.setAcceptedBy(rs.getString("acceptedBy"));
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

    public static boolean updateBorrowDetails(BorrowBean borrowBean, String type){
        boolean result = false;
        Connection conn = null;
        PreparedStatement ps = null;
        int rs = 0;

        try{
            conn = DBConnectionPool.getInstance().getConnection();

            if(type.equals("bookReturn")){
                ps = conn.prepareStatement("UPDATE borrow SET returnedDate = ?, fine = ?, acceptedBy = ? WHERE bookIDBorrow = ? AND returnedDate IS NULL ");

                ps.setString(1, borrowBean.getReturnedDate());
                ps.setInt(2, borrowBean.getFine());
                ps.setString(3, borrowBean.getAcceptedBy());
                ps.setString(4, borrowBean.getBookIDBorrow());

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

    public static int dueTodayCount(String date){
        int dueTodayCount = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = DBConnectionPool.getInstance().getConnection();
            ps = conn.prepareStatement("SELECT COUNT(bookIDBorrow) AS dueToday FROM borrow WHERE dueDate = ? AND returnedDate IS NULL ");

            ps.setString(1, date);

            rs = ps.executeQuery();

            while(rs.next()){
                dueTodayCount = rs.getInt("dueToday");
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
        return dueTodayCount;

    }

    public static int dueBookCount(String date){
        int dueBookCount = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = DBConnectionPool.getInstance().getConnection();
            ps = conn.prepareStatement("SELECT COUNT(bookIDBorrow) AS dueBook FROM borrow WHERE dueDate <> ? AND returnedDate IS NULL ");

            ps.setString(1, date);

            rs = ps.executeQuery();

            while(rs.next()){
                dueBookCount = rs.getInt("dueBook");
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
        return dueBookCount;

    }
}
