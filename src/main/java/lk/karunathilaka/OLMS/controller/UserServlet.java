package lk.karunathilaka.OLMS.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import lk.karunathilaka.OLMS.bean.MemberBean;
import lk.karunathilaka.OLMS.bean.PublisherBean;
import lk.karunathilaka.OLMS.bean.UserBean;
import lk.karunathilaka.OLMS.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

@WebServlet(name = "UserServlet")
public class UserServlet extends HttpServlet {
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doOptions(req, resp);
        setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);

    }

    private void setAccessControlHeaders(HttpServletResponse resp){
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:63342");
        resp.setHeader("Access-Control-Allow-Methods", "*");
        resp.setHeader("Access-Control-Max-Age", "36000");
        resp.setHeader("Access-Control-Allow-Headers", "content-type, x-requested-with");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        setAccessControlHeaders(resp);
        String accessType = req.getParameter("type");
        System.out.println(accessType+"----------------------------------");
        if(accessType.equals("registerMember")){
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            System.out.println(email);
            System.out.println(password);

            MemberBean memberBean = new MemberBean("",req.getParameter("fName"),req.getParameter("lName"),req.getParameter("gender"),req.getParameter("dob"),req.getParameter("telephone"),email,req.getParameter("addLine1"),req.getParameter("addLine2"),req.getParameter("addLine3"),null,null,"pending");
            UserBean userBean = new UserBean(email,password,"member");

            UserService userService = new UserService();
            String result = userService.registerMember(memberBean, userBean);

            resp.setContentType("application/json");
            PrintWriter printWriter = resp.getWriter();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Response", result);
            printWriter.print(jsonObject.toString());

        }else if(accessType.equals("registerPublisher")){
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            PublisherBean publisherBean = new PublisherBean("",req.getParameter("name"),req.getParameter("telephone"),email,"pending");
            UserBean userBean = new UserBean(email,password,"publisher");

            UserService userService = new UserService();
            String result = userService.registerPublisher(publisherBean, userBean);

            resp.setContentType("application/json");
            PrintWriter printWriter = resp.getWriter();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Response", result);
            printWriter.print(jsonObject.toString());

        }else if(accessType.equals("login")){
            System.out.println("Start");
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            UserBean userBean = new UserBean(username,password,null);
            UserService userService = new UserService();
            JsonArray result = userService.userLogin(userBean);
            System.out.println(result.toString());

            resp.setContentType("application/json");
            PrintWriter printWriter = resp.getWriter();
//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("Response", result.toString());
            printWriter.println(result.toString());

        }

    }
}
