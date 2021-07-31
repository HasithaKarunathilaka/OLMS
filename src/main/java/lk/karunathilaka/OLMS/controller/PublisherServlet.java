package lk.karunathilaka.OLMS.controller;

import com.google.gson.JsonArray;
import lk.karunathilaka.OLMS.bean.EbookBeen;
import lk.karunathilaka.OLMS.repository.EbookRepository;
import lk.karunathilaka.OLMS.service.EbookService;

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
            ebookBeen.setIsbn(req.getParameter("isbn"));
            ebookBeen.setTitle(req.getParameter("title"));
            ebookBeen.setAuthor(req.getParameter("author"));
            ebookBeen.setCategory(req.getParameter("category"));
            ebookBeen.setPages(req.getParameter("pages"));
            ebookBeen.setAvailability("pending");
            ebookBeen.setPdfPath(req.getServletContext().getRealPath(""));
            ebookBeen.setImagePath(req.getServletContext().getRealPath(""));
            Part filePart = req.getPart("pdf");
            Part filePartImage = req.getPart("image");
            ebookBeen.setPublisherID(req.getParameter("publisherID"));

            EbookService ebookService = new EbookService();
            String result = ebookService.setEbook(filePart, filePartImage, ebookBeen);
            System.out.println("ready to red23");

//            resp.setContentType("application/json");
            PrintWriter printWriter = resp.getWriter();
//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("Response", result);
            printWriter.print(result);

        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);

        String accessType = req.getParameter("type");

        if(accessType.equals("searchEbook")){
            EbookBeen ebookBeen =new EbookBeen();
            ebookBeen.setPublisherID(req.getParameter("publisherID"));
//            ebookBeen.setPdfPath(req.getServletContext().getRealPath(""));

//            EbookService ebookService = new EbookService();
            JsonArray result = EbookRepository.getPublishersBook(ebookBeen);

            resp.setContentType("application/json");
            PrintWriter printWriter = resp.getWriter();
//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("Response", String.valueOf(result));
            printWriter.print(result.toString());

        }
    }
}
