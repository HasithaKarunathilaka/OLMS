package lk.karunathilaka.OLMS.service;

import com.google.gson.JsonArray;
import lk.karunathilaka.OLMS.bean.ApprovalBean;
import lk.karunathilaka.OLMS.bean.MemberBean;
import lk.karunathilaka.OLMS.repository.ApprovalRepository;
import lk.karunathilaka.OLMS.repository.MemberRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MemberService {
    public JsonArray getStateMemberList(MemberBean memberBean){
        JsonArray result = MemberRepository.getStateMember(memberBean);

        if(result.isEmpty()){
            result.add("Error");

        }
        return result;

    }

    public String approveMember(ApprovalBean approvalBean, String approvalType){
        String result = "Error";

        MemberBean memberBean = new MemberBean();
        memberBean.setMemberID(approvalBean.getItemID());

        if(approvalType.equals("approve")){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            approvalBean.setApprovedDate(simpleDateFormat.format(calendar.getTime()));
            memberBean.setMembershipDate(simpleDateFormat.format(calendar.getTime()));

            //        ------ Calculate 4 Years ------
            calendar.add(Calendar.YEAR,4);
            memberBean.setExpireDate(simpleDateFormat.format(calendar.getTime()));
            System.out.println(memberBean.getExpireDate());
            memberBean.setState("active member");

            boolean resultApprove = ApprovalRepository.setApproval(approvalBean);
            if(!resultApprove){
                return "Error! \nError while updating Approval";
            }

        }else{
            memberBean.setState("reject");
        }

        boolean resultUpdateMember = MemberRepository.updateMember(memberBean, approvalType);

        if(resultUpdateMember){
            result = "success";

        }else{
            result ="Error! \nError while updating Member State";

        }

        return result;

    }
}
