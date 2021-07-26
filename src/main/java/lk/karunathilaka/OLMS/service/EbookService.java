package lk.karunathilaka.OLMS.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lk.karunathilaka.OLMS.bean.ApprovalBean;
import lk.karunathilaka.OLMS.bean.BookStatisticBean;
import lk.karunathilaka.OLMS.bean.EbookBeen;
import lk.karunathilaka.OLMS.bean.RateBean;
import lk.karunathilaka.OLMS.repository.ApprovalRepository;
import lk.karunathilaka.OLMS.repository.BookStatisticRepository;
import lk.karunathilaka.OLMS.repository.EbookRepository;
import lk.karunathilaka.OLMS.repository.RateRepository;

import javax.servlet.http.Part;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EbookService {
    public String setEbook(Part filePart, Part filePartImage, EbookBeen ebookBeen){
        FileService fileService = new FileService();
        boolean resultUploadEbook = fileService.uploadEbook(filePart, ebookBeen);

        if(resultUploadEbook){
            boolean resultUploadImage = fileService.uploadImage(filePartImage, ebookBeen);

            if(resultUploadImage){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar calendar = Calendar.getInstance();
                ebookBeen.setPublishedDate(simpleDateFormat.format(calendar.getTime()));
                ebookBeen.setAvgRate(0);
                boolean result = EbookRepository.setEbook(ebookBeen);
                if(result){
                    return "success";

                }else{
                    return "error while set ebook repo";
                }

            }else{
                return "error while uploading image";

            }

        }else{
            return "error while uploading pdf";

        }

    }

    public String approveEbook(ApprovalBean approvalBean){
        String result = "Error";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        approvalBean.setApprovedDate(simpleDateFormat.format(calendar.getTime()));

        boolean resultApprove = ApprovalRepository.setApproval(approvalBean);
        if(resultApprove){
            EbookBeen ebookBeen = new EbookBeen();
            ebookBeen.setBookID(approvalBean.getItemID());
            ebookBeen.setAvailability("available");
            boolean resultUpdateEbook = EbookRepository.updateEbook(ebookBeen, "approval");

            if(resultUpdateEbook){
                BookStatisticBean readBean = new BookStatisticBean(approvalBean.getItemID(), 0, 0, 0, 0, 0);
                boolean resultSetRead = BookStatisticRepository.setRead(readBean);

                if(resultSetRead){
                    result = "success";
                }else {
                    result ="Error! \nError while Inserting Read table";
                }

            }else{
                result ="Error! \nError while updating Ebook Availability";

            }

        }else{
            result ="Error! \nError while updating Approval";

        }

        return result;

    }

    public JsonArray searchEbook(EbookBeen ebookBeen){
        String title = ebookBeen.getTitle();
        String author = ebookBeen.getAuthor();
        title = "%" + title + "%";
        author = "%" + author + "%";
        ebookBeen.setTitle(title);
        ebookBeen.setAuthor(author);
        ebookBeen.setPublisherID("");

        JsonArray result = EbookRepository.searchEbook(ebookBeen);
        System.out.println(result);

        return result;

    }

    public JsonArray getPdf(RateBean rateBean){
        JsonArray resultBooks = new JsonArray();
        EbookBeen ebookBeen = new EbookBeen();
        ebookBeen.setBookID(rateBean.getBookIDRate());

        boolean resultSelectPdfPath = EbookRepository.getPdfPath(ebookBeen);

        if(resultSelectPdfPath){
//            String pdfPath = ebookBeen.getPdfPath();
//            ebookBeen.setPdfPath(pdfPath + File.separator + pageNo + ".pdf");

            BookStatisticBean bookStatisticBean = new BookStatisticBean();
            bookStatisticBean.setBookIDRead(rateBean.getBookIDRate());
            boolean resultGetBookStatistic = BookStatisticRepository.getBookStatistic(bookStatisticBean);
            if(resultGetBookStatistic){
                int totNumberOfViews = bookStatisticBean.getTotNumberOfViews();
                totNumberOfViews++;
                bookStatisticBean.setTotNumberOfViews(totNumberOfViews);

                boolean resultUpdateBookStatistic = BookStatisticRepository.updateBookStatistic(bookStatisticBean);

                JsonObject bookDetails = new JsonObject();
                if(resultUpdateBookStatistic){
                    bookDetails.addProperty("state", "success");
                    bookDetails.addProperty("pdfLink", ebookBeen.getPdfPath());
                    bookDetails.addProperty("pageCount", ebookBeen.getPages());
                    System.out.println(ebookBeen.getPages());

                }else{
                    bookDetails.addProperty("state", "Error");
                    bookDetails.addProperty("pdfLink", "Error while Updating Book Statistic");

                }
                resultBooks.add(bookDetails);

            }else {
                JsonObject bookDetails = new JsonObject();
                bookDetails.addProperty("state", "Error");
                bookDetails.addProperty("pdfLink", "Error while Getting Book Statistic");
                resultBooks.add(bookDetails);

            }

        }else {
            JsonObject bookDetails = new JsonObject();
            bookDetails.addProperty("state", "Error");
            bookDetails.addProperty("pdfLink", "Error while Selecting PDF");
            resultBooks.add(bookDetails);

        }



        return resultBooks;
    }

    public String setPageStatistics(RateBean rateBean, long pageTime){
        String result = "Read time not Enough!";
        boolean resultUpdateBookStatistics = false;
        boolean resultSetRate = false;
        boolean resultUpdateRate = false;
        BookStatisticBean bookStatisticBean = new BookStatisticBean();
        bookStatisticBean.setBookIDRead(rateBean.getBookIDRate());

        boolean resultGetBookStatistics = BookStatisticRepository.getBookStatistic(bookStatisticBean);
        if(resultGetBookStatistics && pageTime > 7){
            if(pageTime > bookStatisticBean.getMaxTime()){
                bookStatisticBean.setMaxTime(pageTime);
                bookStatisticBean.setMaxPage(rateBean.getPage());
            }
            long newAvgTimeForPage = Math.round((bookStatisticBean.getAvgTimeForPage() + pageTime) / 2);
            System.out.println(newAvgTimeForPage);
            bookStatisticBean.setAvgTimeForPage(newAvgTimeForPage);
            resultUpdateBookStatistics = BookStatisticRepository.updateBookStatistic(bookStatisticBean);

        }else {
            result = "Error \nPage Read Time less than 7 seconds or \nError while Selecting BookStatistic repo";

        }

        if(rateBean.getTime() > 7){
            String[] resultGetRate = RateRepository.getRate(rateBean);
            if(resultGetRate[0].equals("0")){
                resultSetRate = RateRepository.setRate(rateBean);
            }else{
                long newTime = rateBean.getTime() + Long.parseLong(resultGetRate[0]);
                int newPage = rateBean.getPage() + Integer.parseInt(resultGetRate[1]);
                rateBean.setTime(newTime);
                rateBean.setPage(newPage);

                resultUpdateRate = RateRepository.updateRate(rateBean);
            }


        }

        if(resultUpdateBookStatistics || resultSetRate || resultUpdateRate){
            result = "success";

        }

        return result;
    }

}
