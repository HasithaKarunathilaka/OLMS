package lk.karunathilaka.OLMS.service;

import com.google.gson.JsonArray;
import lk.karunathilaka.OLMS.repository.BorrowRepository;
import lk.karunathilaka.OLMS.repository.EbookRepository;
import lk.karunathilaka.OLMS.repository.MemberRepository;
import lk.karunathilaka.OLMS.repository.PublisherRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DashboardService {
    public JsonArray dashboardOnLoad(){
        JsonArray result = new JsonArray();

//                ------- Set Current Date -------

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        String today = simpleDateFormat.format(calendar.getTime());

        int resultEbookCount = EbookRepository.availabilityCount("pending");
        int resultMemberCount = MemberRepository.memberStateCount("pending");
        int resultPublisherCount = PublisherRepository.stateCount("pending");
        int resultDueTodayCount = BorrowRepository.dueTodayCount(today);
        int resultDueBook = BorrowRepository.dueBookCount(today);

        result.add(resultEbookCount);
        result.add(resultMemberCount);
        result.add(resultPublisherCount);
        result.add(resultDueTodayCount);
        result.add(resultDueBook);

        return result;
    }
}
