package lk.karunathilaka.OLMS.bean;

public class RateBean {
    private String bookIDRate;
    private String memberIDRate;
    private int rate;
    private long time;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
