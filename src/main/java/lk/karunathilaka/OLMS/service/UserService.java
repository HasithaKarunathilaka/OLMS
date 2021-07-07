package lk.karunathilaka.OLMS.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lk.karunathilaka.OLMS.bean.MemberBean;
import lk.karunathilaka.OLMS.bean.PublisherBean;
import lk.karunathilaka.OLMS.bean.UserBean;
import lk.karunathilaka.OLMS.repository.MemberRepository;
import lk.karunathilaka.OLMS.repository.PublisherRepository;
import lk.karunathilaka.OLMS.repository.UserRepository;

public class UserService {
    public String registerMember(MemberBean memberBean, UserBean userBean){
        String resultRegisterMember;

        boolean resultUserRepository = UserRepository.setUser(userBean);

        if(resultUserRepository){
            String memberID = "";
            int memberCount = MemberRepository.rowCount();
            memberID = "M" + Integer.toString (memberCount + 1);
            memberBean.setMemberID(memberID);

            boolean resultMemberRepository = MemberRepository.setMember(memberBean);

            if(resultMemberRepository){
                resultRegisterMember = "success";
            }else{
                resultRegisterMember = "setMember error";
            }

        }else{
            resultRegisterMember = "setUser error";
        }
        return resultRegisterMember;
    }

    public String registerPublisher(PublisherBean publisherBean, UserBean userBean){
        String resultRegisterPublisher;

        boolean resultUserRepository = UserRepository.setUser(userBean);

        if(resultUserRepository){
            String publisherID = "";
            int publisherCount = PublisherRepository.rowCount();
            publisherID = "P" + Integer.toString (publisherCount + 1);
            publisherBean.setPublisherID(publisherID);

            boolean resultPublisherRepository = PublisherRepository.setPublisher(publisherBean);

            if(resultPublisherRepository){
                resultRegisterPublisher = "success";
            }else{
                resultRegisterPublisher = "setPublisher error";
            }

        }else{
            resultRegisterPublisher = "setUser error";
        }
        return resultRegisterPublisher;
    }

    public Object userLogin(UserBean userBean){
        JsonArray userLoginResult = new JsonArray();
        boolean userRepositoryResult = UserRepository.getUserLogin(userBean);

        if(userRepositoryResult){

            if(userBean.getType().equals("member")){
                MemberBean memberBean = MemberRepository.getMemberLogin(userBean);

                if(memberBean.equals(null)){
                    return "error memberRepository";

                }else{
                    JsonObject memberLogin = new JsonObject();
                    memberLogin.addProperty("id", memberBean.getMemberID());
                    memberLogin.addProperty("name", memberBean.getfName());
                    memberLogin.addProperty("state", memberBean.getState());
                    userLoginResult.add(memberLogin);
                    return userLoginResult;
                }

            }else if(userBean.getType().equals("publisher")){
                PublisherBean publisherBean = PublisherRepository.getPublisherLogin(userBean);

                if(publisherBean.equals(null)){
                    return "error publisherRepository";

                }else{
                    JsonObject publisherLogin = new JsonObject();
                    publisherLogin.addProperty("id", publisherBean.getPublisherID());
                    publisherLogin.addProperty("name", publisherBean.getName());
                    publisherLogin.addProperty("state", publisherBean.getState());
                    userLoginResult.add(publisherLogin);
                    return userLoginResult;

                }

            }

        }else{
            return "invalid username password";

        }
        return null;
    }
}
