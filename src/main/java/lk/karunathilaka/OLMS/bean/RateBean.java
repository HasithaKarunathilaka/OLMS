package lk.karunathilaka.OLMS.bean;

import java.sql.Time;

public class RateBean {
    private String bookIDRate;
    private String memberIDRate;
    private int rate;
    private Time time;
    private int page;

    public RateBean() {
    }

    public String getBookIDRate() {
        return bookIDRate;
    }

    public void setBookIDRate(String bookIDRate) {
        this.bookIDRate = bookIDRate;
    }

    public String getMemberIDRate() {
        return memberIDRate;
    }

    public void setMemberIDRate(String memberIDRate) {
        this.memberIDRate = memberIDRate;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
