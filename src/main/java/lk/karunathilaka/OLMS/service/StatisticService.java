package lk.karunathilaka.OLMS.service;

import com.google.gson.JsonArray;
import lk.karunathilaka.OLMS.bean.RateBean;
import lk.karunathilaka.OLMS.repository.EbookRepository;
import lk.karunathilaka.OLMS.repository.MemberRepository;
import lk.karunathilaka.OLMS.repository.RateRepository;

public class StatisticService {
    public JsonArray genderStat(){
        JsonArray result = new JsonArray();
        int maleCount = MemberRepository.genderCount("Male");
        int femaleCount = MemberRepository.genderCount("Female");

        if(maleCount >=0 && femaleCount >= 0){
            result.add(maleCount);
            result.add(femaleCount);

        }else{
            result.add("Error");
        }


        return result;
    }

    public JsonArray categoryStat(){
        JsonArray result = new JsonArray();
        int it = EbookRepository.categoryCount("IT");
        int science = EbookRepository.categoryCount("science");
        int story = EbookRepository.categoryCount("story");
        int fiction = EbookRepository.categoryCount("fiction");

        if(it >=0 && science >= 0 && story >= 0 && fiction >= 0){
            result.add(it);
            result.add(science);
            result.add(story);
            result.add(fiction);

        }else{
            result.add("Error");
        }


        return result;
    }
    public JsonArray top10Stat(){
        JsonArray result = new JsonArray();
        JsonArray resultTop10 = EbookRepository.getTop10Rate();
        if(resultTop10.isEmpty()){
            result.add("Error");
            return result;

        }else {
            return resultTop10;
        }
//        return result;
    }

    public JsonArray memberStateCount(){
        JsonArray result = new JsonArray();
        int active = MemberRepository.memberStateCount("active member");
        int inactive = MemberRepository.memberStateCount("inactive");
        int reject = MemberRepository.memberStateCount("reject");
        int pending = MemberRepository.memberStateCount("pending");
        if(active >=0 && inactive >= 0 && pending >=0 && reject >= 0){
            result.add(active);
            result.add(inactive);
            result.add(reject);
            result.add(pending);

        }else {
            result.add("Error");

        }
        return result;

    }

    public JsonArray memberStat(String memberID){
        JsonArray result = new JsonArray();
        RateBean rateBean = new RateBean();
        rateBean.setMemberIDRate(memberID);

        boolean resultGetMemberStat = RateRepository.getMemberStat(rateBean);
        int resultTotalReadBook = RateRepository.totalReadBookCount(rateBean.getMemberIDRate());

        if(resultGetMemberStat && resultTotalReadBook >= 0){
            int minutes = (int) (rateBean.getTime()/60);
            result.add(minutes);
            result.add(rateBean.getPage());
            result.add(resultTotalReadBook);

        }else{
            result.add("Error");

        }
        return result;
    }
}
