package lk.karunathilaka.OLMS.repository;

import lk.karunathilaka.OLMS.bean.BorrowBean;
import lk.karunathilaka.OLMS.db.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
}
