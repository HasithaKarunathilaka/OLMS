package lk.karunathilaka.OLMS.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lk.karunathilaka.OLMS.bean.ApprovalBean;
import lk.karunathilaka.OLMS.bean.BookBean;
import lk.karunathilaka.OLMS.bean.BorrowBean;
import lk.karunathilaka.OLMS.bean.EbookBeen;
import lk.karunathilaka.OLMS.service.BookService;
import lk.karunathilaka.OLMS.service.EbookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.io.File;

@WebServlet(name = "StaffServlet")
public class StaffServlet extends HttpServlet {
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
            System.out.println("start");
            System.out.println(req.getParameter("bookID"));
            System.out.println(req.getParameter("memberID"));
            System.out.println(req.getParameter("issuedBy"));
            BorrowBean borrowBean = new BorrowBean(req.getParameter("bookID"), req.getParameter("memberID"), req.getParameter("issuedBy"));
            BookService bookService = new BookService();
            String result = bookService.borrowBook(borrowBean);
//            String result = "result";
            System.out.println(result);

//            resp.setContentType("application/json");
            PrintWriter printWriter = resp.getWriter();
//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("Response", result);
            printWriter.print(result);

//        }else if(accessType.equals("creatFolder")){
//            String fileName = req.getParameter("fileName");
//            String realPath = getServletContext().getRealPath("");
//            File file = new File(realPath+File.separator+fileName);
////            file.mkdirs();
//            System.out.println(realPath);
//            boolean result = file.mkdirs();
//
//            resp.setContentType("application/json");
//            PrintWriter printWriter = resp.getWriter();
//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("Response", result);
//            printWriter.print(jsonObject.toString());

        }else if(accessType.equals("returnBook")){
            BorrowBean borrowBean = new BorrowBean(req.getParameter("bookID"), req.getParameter("acceptedBy"));
            BookService bookService = new BookService();
            JsonArray result = bookService.returnBook(borrowBean);

            resp.setContentType("application/json");
            PrintWriter printWriter = resp.getWriter();
//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("Response", String.valueOf(result));
            printWriter.print(result.toString());

        }
        else if(accessType.equals("approveEbook")){
            ApprovalBean approvalBean = new ApprovalBean();
            approvalBean.setItemID(req.getParameter("bookID"));
            approvalBean.setApprovedBy(req.getParameter("staffID"));
            EbookService ebookService = new EbookService();
            String result = ebookService.approveEbook(approvalBean);
            System.out.println(result);

//            JsonArray result = bookService.returnBook(borrowBean);

//            resp.setContentType("application/json");
            PrintWriter printWriter = resp.getWriter();
//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("Response", String.valueOf(result));
            printWriter.print(result);

        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPut(req, resp);
        setAccessControlHeaders(resp);
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
        setAccessControlHeaders(resp);
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
        setAccessControlHeaders(resp);
        String accessType = req.getParameter("type");

        if(accessType.equals("searchBook")){
            BookBean bookBean = new BookBean(null, req.getParameter("isbn"), req.getParameter("title"), req.getParameter("author"), req.getParameter("category"), null, null, req.getParameter("availability"));
            BookService bookService = new BookService();
            JsonArray result = bookService.searchBook(bookBean);
            System.out.println(result.toString());

            resp.setContentType("application/json");
            PrintWriter printWriter = resp.getWriter();
//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("Response", String.valueOf(result));
            printWriter.print(result.toString());

        }
    }
}
