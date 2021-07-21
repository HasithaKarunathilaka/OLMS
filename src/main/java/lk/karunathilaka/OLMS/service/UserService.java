package lk.karunathilaka.OLMS.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lk.karunathilaka.OLMS.bean.MemberBean;
import lk.karunathilaka.OLMS.bean.PublisherBean;
import lk.karunathilaka.OLMS.bean.UserBean;
import lk.karunathilaka.OLMS.repository.MemberRepository;
import lk.karunathilaka.OLMS.repository.PublisherRepository;
import lk.karunathilaka.OLMS.repository.UserRepository;

import javax.xml.bind.SchemaOutputResolver;

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
            resultRegisterMember = "Your Email already Registered!";
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

    public JsonArray userLogin(UserBean userBean){
        JsonArray userLoginResult = new JsonArray();
        boolean userRepositoryResult = UserRepository.getUserLogin(userBean);

        if(userRepositoryResult){

            if(userBean.getType().equals("member")){
                MemberBean memberBean = MemberRepository.getMemberLogin(userBean);

                if(memberBean.equals(null)){
                    userLoginResult.add("error memberRepository");

                }else{
                    System.out.println(memberBean.getMemberID());
                    JsonObject memberLogin = new JsonObject();
                    memberLogin.addProperty("id", memberBean.getMemberID());
                    memberLogin.addProperty("name", memberBean.getfName());
                    memberLogin.addProperty("state", memberBean.getState());
                    memberLogin.addProperty("type", userBean.getType());
                    userLoginResult.add(memberLogin);
                    System.out.println(userLoginResult);
                }
                return userLoginResult;

            }else if(userBean.getType().equals("publisher")){
                PublisherBean publisherBean = PublisherRepository.getPublisherLogin(userBean);

                if(publisherBean.equals(null)){
                    userLoginResult.add("error publisherRepository");

                }else{
                    JsonObject publisherLogin = new JsonObject();
                    publisherLogin.addProperty("id", publisherBean.getPublisherID());
                    publisherLogin.addProperty("name", publisherBean.getName());
                    publisherLogin.addProperty("state", publisherBean.getState());
                    publisherLogin.addProperty("type", userBean.getType());
                    userLoginResult.add(publisherLogin);

                }

            }

        }else{
            userLoginResult.add("invalid username password");

        }
        return userLoginResult;
    }
}
