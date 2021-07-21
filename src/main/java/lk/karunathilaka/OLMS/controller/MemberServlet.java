package lk.karunathilaka.OLMS.controller;

import com.google.gson.JsonArray;
import lk.karunathilaka.OLMS.bean.EbookBeen;
import lk.karunathilaka.OLMS.service.EbookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "MemberServlet")
public class MemberServlet extends HttpServlet {
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        String type = req.getParameter("type");
        if(type.equals("ebookSearch")){
            EbookBeen ebookBeen =new EbookBeen();
            ebookBeen.setBookID(req.getParameter("bookID"));
            ebookBeen.setTitle(req.getParameter("title"));
            ebookBeen.setAuthor(req.getParameter("author"));
            ebookBeen.setCategory(req.getParameter("category"));
            EbookService ebookService = new EbookService();
            JsonArray result = ebookService.searchEbook(ebookBeen);

        }
    }
}
