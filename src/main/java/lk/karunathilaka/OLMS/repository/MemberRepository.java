package lk.karunathilaka.OLMS.repository;

import lk.karunathilaka.OLMS.bean.MemberBean;
import lk.karunathilaka.OLMS.bean.UserBean;
import lk.karunathilaka.OLMS.db.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRepository {
    public static boolean setMember(MemberBean memberBean){
        boolean result = false;
        Connection conn = null;
        PreparedStatement ps = null;
        int rs = 0;

        try{
            conn = DBConnectionPool.getInstance().getConnection();
            ps = conn.prepareStatement("INSERT INTO member (memberID, fName, lName, gender, dob, telephone, email, addLine1, addLine2, addLine3, membershipDate, expireDate, state) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
            ps.setString(1, memberBean.getMemberID());
            ps.setString(2, memberBean.getfName());
            ps.setString(3, memberBean.getlName());
            ps.setString(4, memberBean.getGender());
            ps.setString(5, memberBean.getDob());
            ps.setString(6, memberBean.getTelephone());
            ps.setString(7, memberBean.getEmail());
            ps.setString(8, memberBean.getAddLine1());
            ps.setString(9, memberBean.getAddLine2());
            ps.setString(10, memberBean.getAddLine3());
            ps.setString(11, memberBean.getMembershipDate());
            ps.setString(12, memberBean.getExpireDate());
            ps.setString(13, memberBean.getState());

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
        int memberCount = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = DBConnectionPool.getInstance().getConnection();
            ps = conn.prepareStatement("SELECT COUNT(memberID) AS memberCount FROM member");

            rs = ps.executeQuery();

            while(rs.next()){
                memberCount = rs.getInt("memberCount");
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
        return memberCount;

    }

    public static MemberBean getMemberLogin(UserBean userBean){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        MemberBean memberBean = new MemberBean(null,null,null,null,null,null,null,null,null,null,null,null,null);

        try{
            conn = DBConnectionPool.getInstance().getConnection();
            ps = conn.prepareStatement("SELECT * FROM member WHERE email = ?");
            ps.setString(1, userBean.getId());
            rs = ps.executeQuery();

            while(rs.next()){
                memberBean.setMemberID(rs.getString("memberID"));
                memberBean.setfName(rs.getString("fName"));
                memberBean.setState(rs.getString("state"));

            }

        }catch (SQLException e){
            e.printStackTrace();

        }finally{
            DBConnectionPool.getInstance().close(rs);
            DBConnectionPool.getInstance().close(ps);
            DBConnectionPool.getInstance().close(conn);
        }
        return memberBean;
    }

    public static int genderCount(String gender){
        int genderCount = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = DBConnectionPool.getInstance().getConnection();
            ps = conn.prepareStatement("SELECT COUNT(memberID) AS genderCount FROM member WHERE gender = ?");

            ps.setString(1, gender);

            rs = ps.executeQuery();

            while(rs.next()){
                genderCount = rs.getInt("genderCount");
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
        return genderCount;

    }

    public static int memberStateCount(String state){
        int stateCount = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = DBConnectionPool.getInstance().getConnection();
            ps = conn.prepareStatement("SELECT COUNT(memberID) AS stateCount FROM member WHERE state = ?");

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
}
