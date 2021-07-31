package lk.karunathilaka.OLMS.repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lk.karunathilaka.OLMS.bean.PublisherBean;
import lk.karunathilaka.OLMS.bean.UserBean;
import lk.karunathilaka.OLMS.db.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PublisherRepository {
    public static boolean setPublisher(PublisherBean publisherBean){
        boolean result = false;
        Connection conn = null;
        PreparedStatement ps = null;
        int rs = 0;

        try{
            conn = DBConnectionPool.getInstance().getConnection();
            ps = conn.prepareStatement("INSERT INTO publisher (publisherID, name, telephone, email, state) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, publisherBean.getPublisherID());
            ps.setString(2, publisherBean.getName());
            ps.setString(3, publisherBean.getTelephone());
            ps.setString(4, publisherBean.getEmail());
            ps.setString(5, publisherBean.getState());

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

    public static int rowCount(){
        int publisherCount = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = DBConnectionPool.getInstance().getConnection();
            ps = conn.prepareStatement("SELECT COUNT(publisherID) AS publisherCount FROM publisher");

            rs = ps.executeQuery();

            while(rs.next()){
                publisherCount = rs.getInt("publisherCount");
            }

        }catch (SQLException e){
            e.printStackTrace();

        }finally{
            DBConnectionPool.getInstance().close(rs);
            DBConnectionPool.getInstance().close(ps);
            DBConnectionPool.getInstance().close(conn);
        }
        return publisherCount;

    }

    public static PublisherBean getPublisherLogin(UserBean userBean){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        PublisherBean publisherBean = new PublisherBean(null,null,null,null,null);

        try{
            conn = DBConnectionPool.getInstance().getConnection();
            ps = conn.prepareStatement("SELECT * FROM publisher WHERE email = ?");
            ps.setString(1, userBean.getId());

            rs = ps.executeQuery();
            if(rs.next()){
                publisherBean.setPublisherID(rs.getString("publisherID"));
                publisherBean.setName(rs.getString("name"));
                publisherBean.setState(rs.getString("state"));
            }

        }catch (SQLException e){
            e.printStackTrace();

        }finally{
            DBConnectionPool.getInstance().close(rs);
            DBConnectionPool.getInstance().close(ps);
            DBConnectionPool.getInstance().close(conn);
        }
        return publisherBean;
    }

    public static int stateCount(String state){
        int stateCount = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = DBConnectionPool.getInstance().getConnection();
            ps = conn.prepareStatement("SELECT COUNT(publisherID) AS stateCount FROM publisher WHERE state = ?");

            ps.setString(1, state);

            rs = ps.executeQuery();

            while(rs.next()){
                stateCount = rs.getInt("stateCount");
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
        return stateCount;

    }

    public static JsonArray getStatePublisher(PublisherBean publisherBean){
        JsonArray result = new JsonArray();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
//        boolean result = false;

        try{
            conn = DBConnectionPool.getInstance().getConnection();
            ps = conn.prepareStatement("SELECT * FROM publisher WHERE state = ?");
            ps.setString(1, publisherBean.getState());

            rs = ps.executeQuery();

            while(rs.next()){
                JsonObject publisherDetail = new JsonObject();
                publisherDetail.addProperty("publisherID", rs.getString("publisherID"));
                publisherDetail.addProperty("name", rs.getString("name"));
                result.add(publisherDetail);
//                result = true;
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

    public static boolean updatePublisher(PublisherBean publisherBean){
        boolean result = false;
        Connection conn = null;
        PreparedStatement ps = null;
        int rs = 0;

        try{
            conn = DBConnectionPool.getInstance().getConnection();

            ps = conn.prepareStatement("UPDATE publisher SET state = ? WHERE publisherID = ?");

            ps.setString(1, publisherBean.getState());
            ps.setString(2, publisherBean.getPublisherID());

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
