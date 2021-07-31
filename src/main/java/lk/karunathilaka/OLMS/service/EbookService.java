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
        ebookBeen.setPdfPath("F:\\Uni Aca\\Level 4\\Sem 1\\IT 4004 - Advanced Database Systems\\Project\\Backend\\OLMS\\out\\artifacts\\OLMS_war_exploded");
        ebookBeen.setImagePath("F:\\Uni Aca\\Level 4\\Sem 1\\IT 4004 - Advanced Database Systems\\Project\\Backend\\OLMS\\out\\artifacts\\OLMS_war_exploded");
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

    public String approveEbook(ApprovalBean approvalBean, String approvalType){
        String result = "Error";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        approvalBean.setApprovedDate(simpleDateFormat.format(calendar.getTime()));

        boolean resultApprove = ApprovalRepository.setApproval(approvalBean);
        if(resultApprove){
            EbookBeen ebookBeen = new EbookBeen();
            ebookBeen.setBookID(approvalBean.getItemID());

            if(approvalType.equals("approve")){
                ebookBeen.setAvailability("available");
            }else {
                ebookBeen.setAvailability("reject");
            }
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

    public String setRate(RateBean rateBean) {
        String result = "Error \n";
        if(rateBean.getRate() > 0){
            boolean resultSetResult = RateRepository.updateRate(rateBean);
            if(resultSetResult){
                Double resultGetRate = RateRepository.getAllRateOfBook(rateBean);
                System.out.println(resultGetRate);

                if(resultGetRate > 0.0){
                    EbookBeen ebookBeen = new EbookBeen();
                    ebookBeen.setBookID(rateBean.getBookIDRate());
                    ebookBeen.setAvgRate(resultGetRate);

                    boolean resultSetAvgRate = EbookRepository.updateEbook(ebookBeen, "setAvgRate");
                    if(resultSetAvgRate){
                        result = "success";
                    }else{
                        result = "Error \nError while Setting avgRate";
                    }
                }else{
                    result = "Error \nCalculated Average Rate is 0.0";
                }

            }else{
                result = "Error \nError while Updating Rate";
            }

        }else {
            result = "Error \nError Given Rate is 0";
        }
        return result;

    }

    public JsonArray getAvailabilityBookList(EbookBeen ebookBeen){
//        ebookBeen.setPublisherID("");
//        ebookBeen.setBookID("");
//        ebookBeen.setIsbn("");
//        ebookBeen.setCategory("all");
//        ebookBeen.setTitle("%%");
//        ebookBeen.setAuthor("%%");

//        JsonArray result = new JsonArray();
        JsonArray result = EbookRepository.getAvailabilityBook(ebookBeen);

        if(result.isEmpty()){
            result.add("Error");

        }
        return result;

//        return result;

    }

}
