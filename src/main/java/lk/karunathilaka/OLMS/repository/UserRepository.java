package lk.karunathilaka.OLMS.repository;

import lk.karunathilaka.OLMS.bean.UserBean;
import lk.karunathilaka.OLMS.db.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    public static boolean setUser(UserBean userBean){
        boolean result = false;
        Connection conn = null;
        PreparedStatement ps = null;
        int rs = 0;

        try{
            conn = DBConnectionPool.getInstance().getConnection();
            ps = conn.prepareStatement("INSERT INTO user (id, password, type) VALUES (?, ?, ?)");
            ps.setString(1, userBean.getId());
            ps.setString(2, userBean.getPassword());
            ps.setString(3, userBean.getType());

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

    public static boolean getUserLogin(UserBean userBean){
        boolean result = false;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = DBConnectionPool.getInstance().getConnection();
            ps = conn.prepareStatement("SELECT * FROM user WHERE id = ? and password = ?");
            ps.setString(1, userBean.getId());
            ps.setString(2, userBean.getPassword());

            rs = ps.executeQuery();
            if(rs.next()){
                result = true;
                userBean.setType(rs.getString("type"));
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
