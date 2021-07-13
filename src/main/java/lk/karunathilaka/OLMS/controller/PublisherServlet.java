package lk.karunathilaka.OLMS.controller;

import com.google.gson.JsonObject;
import lk.karunathilaka.OLMS.bean.EbookBeen;
import lk.karunathilaka.OLMS.service.EbookService;
import lk.karunathilaka.OLMS.service.FileService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "PublisherServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
        maxFileSize = 1024 * 1024 * 1000, // 1 GB
        maxRequestSize = 1024 * 1024 * 1000)

public class PublisherServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        String accessType = req.getParameter("type");

        if(accessType.equals("uploadEBook")){
            EbookBeen ebookBeen = new EbookBeen();
            ebookBeen.setBookID(req.getParameter("bookID"));
            ebookBeen.setPdfPath(req.getServletContext().getRealPath(""));
            Part filePart = req.getPart("pdf");
            System.out.println(ebookBeen.getPdfPath());

            EbookService ebookService = new EbookService();
            String result = ebookService.setEbook(filePart, ebookBeen);

            resp.setContentType("application/json");
            PrintWriter printWriter = resp.getWriter();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Response", result);
            printWriter.print(jsonObject.toString());

        }
    }
}
