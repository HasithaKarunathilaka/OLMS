package lk.karunathilaka.OLMS.service;

import com.google.gson.JsonArray;
import lk.karunathilaka.OLMS.bean.ApprovalBean;
import lk.karunathilaka.OLMS.bean.PublisherBean;
import lk.karunathilaka.OLMS.repository.ApprovalRepository;
import lk.karunathilaka.OLMS.repository.PublisherRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PublisherService {
    public JsonArray getStatePublisherList(PublisherBean publisherBean){
        JsonArray result = PublisherRepository.getStatePublisher(publisherBean);

        if(result.isEmpty()){
            result.add("Error");

        }
        return result;

    }

    public String approvePublisher(ApprovalBean approvalBean, String approvalType){
        String result = "Error";

        PublisherBean publisherBean = new PublisherBean();
        publisherBean.setPublisherID(approvalBean.getItemID());

        if(approvalType.equals("approve")){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            approvalBean.setApprovedDate(simpleDateFormat.format(calendar.getTime()));

           publisherBean.setState("active publisher");

            boolean resultApprove = ApprovalRepository.setApproval(approvalBean);
            if(!resultApprove){
                return "Error! \nError while updating Approval";
            }

        }else{
            publisherBean.setState("reject");
        }

        boolean resultUpdatePublisher = PublisherRepository.updatePublisher(publisherBean);

        if(resultUpdatePublisher){
            result = "success";

        }else{
            result ="Error! \nError while updating Publisher State";

        }

        return result;

    }
}
