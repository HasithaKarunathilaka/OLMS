package lk.karunathilaka.OLMS.repository;

import lk.karunathilaka.OLMS.bean.ApprovalBean;
import lk.karunathilaka.OLMS.bean.BookBean;
import lk.karunathilaka.OLMS.db.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ApprovalRepository {
    public static boolean setApproval(ApprovalBean approvalBean){
        boolean result = false;
        Connection conn = null;
        PreparedStatement ps = null;
        int rs = 0;

        try{
            conn = DBConnectionPool.getInstance().getConnection();
            ps = conn.prepareStatement("INSERT INTO approval (itemID, approvedBy, approvedDate) VALUES (?, ?, ?)");
            ps.setString(1, approvalBean.getItemID());
            ps.setString(2, approvalBean.getApprovedBy());
            ps.setString(3, approvalBean.getApprovedDate());

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
