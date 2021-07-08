package lk.karunathilaka.OLMS.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lk.karunathilaka.OLMS.bean.BookBean;
import lk.karunathilaka.OLMS.bean.BorrowBean;
import lk.karunathilaka.OLMS.service.BookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

@WebServlet(name = "StaffServlet")
public class StaffServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        String accessType = req.getParameter("type");

        if(accessType.equals("addBook")){
            BookBean bookBean = new BookBean(req.getParameter("bookID"), req.getParameter("isbn"), req.getParameter("title"), req.getParameter("author"), req.getParameter("category"), req.getParameter("addedBy"), new Date(new java.util.Date().getTime()).toString(), req.getParameter("availability"));
            BookService bookService = new BookService();
            String result = bookService.setBook(bookBean);

            resp.setContentType("application/json");
            PrintWriter printWriter = resp.getWriter();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Response", result);
            printWriter.print(jsonObject.toString());

        }else if(accessType.equals("borrowBook")){
            BorrowBean borrowBean = new BorrowBean(req.getParameter("bookID"), req.getParameter("memberID"), req.getParameter("issuedBy"));
            BookService bookService = new BookService();
            String result = bookService.borrowBook(borrowBean);

            resp.setContentType("application/json");
            PrintWriter printWriter = resp.getWriter();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Response", result);
            printWriter.print(jsonObject.toString());

        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPut(req, resp);
        String accessType = req.getParameter("type");

        if(accessType.equals("updateBook")){
            BookBean bookBean = new BookBean(req.getParameter("bookID"), req.getParameter("isbn"), req.getParameter("title"), req.getParameter("author"), req.getParameter("category"), req.getParameter("addedBy"), new Date(new java.util.Date().getTime()).toString(), req.getParameter("availability"));
            BookService bookService = new BookService();
            String result = bookService.updateBook(bookBean);

            resp.setContentType("application/json");
            PrintWriter printWriter = resp.getWriter();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Response", result);
            printWriter.print(jsonObject.toString());

        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doDelete(req, resp);
        String accessType = req.getParameter("type");

        if(accessType.equals("deleteBook")){
            BookBean bookBean = new BookBean(req.getParameter("bookID"), null, null, null, null, req.getParameter("addedBy"), new Date(new java.util.Date().getTime()).toString(), "delete");
            BookService bookService = new BookService();
            String result = bookService.deleteBook(bookBean);

            resp.setContentType("application/json");
            PrintWriter printWriter = resp.getWriter();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Response", result);
            printWriter.print(jsonObject.toString());

        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        String accessType = req.getParameter("type");

        if(accessType.equals("searchBook")){
            BookBean bookBean = new BookBean(null, req.getParameter("isbn"), req.getParameter("title"), req.getParameter("author"), req.getParameter("category"), null, null, req.getParameter("availability"));
            BookService bookService = new BookService();
            Object result = bookService.searchBook(bookBean);

            resp.setContentType("application/json");
            PrintWriter printWriter = resp.getWriter();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Response", String.valueOf(result));
            printWriter.print(jsonObject.toString());

        }
    }
}
