package lk.karunathilaka.OLMS.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class CalculationService {
    public String checkDateBefore(String date1, String date2) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if(dateFormat.parse(date1).before(dateFormat.parse(date2))){
            return "before";

        }else{
            return "after";
        }

    }

    public int calculateDateDifference(String date1, String date2) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date firstDate = dateFormat.parse(date1);
        Date secondDate = dateFormat.parse(date2);

        return (int) ChronoUnit.DAYS.between(firstDate.toInstant(), secondDate.toInstant());

    }

    public int calculateFine(int numberOfDays){
        int fine;
        fine = numberOfDays*5;

        return fine;
    }
}
