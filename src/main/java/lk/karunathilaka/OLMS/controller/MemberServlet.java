package lk.karunathilaka.OLMS.controller;

import com.google.gson.JsonArray;
import lk.karunathilaka.OLMS.bean.EbookBeen;
import lk.karunathilaka.OLMS.bean.RateBean;
import lk.karunathilaka.OLMS.service.EbookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
        System.out.println(req.getServletContext().getRealPath(""));

        if(type.equals("ebookSearch")){
//            System.out.println("start");
            EbookBeen ebookBeen =new EbookBeen();
            ebookBeen.setBookID(req.getParameter("bookID"));
            ebookBeen.setTitle(req.getParameter("title"));
            ebookBeen.setAuthor(req.getParameter("author"));
            ebookBeen.setCategory(req.getParameter("category"));
            ebookBeen.setAvailability("available");
//            ebookBeen.setPdfPath(req.getServletContext().getRealPath(""));

            EbookService ebookService = new EbookService();
            JsonArray result = ebookService.searchEbook(ebookBeen);

            resp.setContentType("application/json");
            PrintWriter printWriter = resp.getWriter();
//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("Response", String.valueOf(result));
            printWriter.print(result.toString());

        }else if(type.equals("readBook")){
//            System.out.println("start");
            RateBean rateBean = new RateBean();
//            String pageNo = req.getParameter("pageNo");
//            rateBean.setMemberIDRate(req.getParameter("memberID"));
            rateBean.setBookIDRate(req.getParameter("bookID"));

            EbookService ebookService = new EbookService();
            JsonArray result = ebookService.getPdf(rateBean);
            System.out.println(result);

            resp.setContentType("application/json");
            PrintWriter printWriter = resp.getWriter();
//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("Response", String.valueOf(result));
            printWriter.print(result.toString());

        }else if(type.equals("readPage")){
//            System.out.println("start");
            RateBean rateBean = new RateBean();
            rateBean.setBookIDRate(req.getParameter("bookID"));
            rateBean.setMemberIDRate(req.getParameter("memberID"));
            rateBean.setPage(Integer.parseInt(req.getParameter("pageNo")));
            rateBean.setRate(0);
            long pageTime = Long.parseLong(req.getParameter("pageTime"));
            rateBean.setTime(Long.parseLong(req.getParameter("totalTime")));

            EbookService ebookService = new EbookService();
            String result = ebookService.setPageStatistics(rateBean, pageTime);
            System.out.println(result);

//            resp.setContentType("application/json");
            PrintWriter printWriter = resp.getWriter();
//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("Response", String.valueOf(result));
            printWriter.print(result);

        }else if(type.equals("rateBook")){
//            System.out.println("start");
            RateBean rateBean = new RateBean();
            rateBean.setBookIDRate(req.getParameter("bookID"));
            System.out.println(rateBean.getBookIDRate());
            rateBean.setMemberIDRate(req.getParameter("memberID"));
            rateBean.setRate(Integer.parseInt(req.getParameter("rate")));

            EbookService ebookService = new EbookService();
            String result = ebookService.setRate(rateBean);
            System.out.println(result);

//            resp.setContentType("application/json");
            PrintWriter printWriter = resp.getWriter();
//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("Response", String.valueOf(result));
            printWriter.print(result);

        }
    }
}
