package lk.karunathilaka.OLMS.service;

import com.google.gson.JsonArray;
import lk.karunathilaka.OLMS.bean.ApprovalBean;
import lk.karunathilaka.OLMS.bean.EbookBeen;
import lk.karunathilaka.OLMS.repository.ApprovalRepository;
import lk.karunathilaka.OLMS.repository.EbookRepository;

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
            boolean resultUpdateEbook = EbookRepository.updateEbook(ebookBeen, "approval");

            if(resultUpdateEbook){
                result = "success";
            }else {
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
        ebookBeen.setAvailability("available");

        JsonArray result = EbookRepository.searchEbook(ebookBeen);

    }

}
