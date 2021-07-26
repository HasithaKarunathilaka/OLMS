package lk.karunathilaka.OLMS.bean;

public class BookStatisticBean {
    private String bookIDRead;
    private int totReadPages;
    private long avgTimeForPage;
    private int totNumberOfViews;
    private int maxPage;
    private long maxTime;

    public BookStatisticBean() {
    }

    public BookStatisticBean(String bookIDRead, int totReadPages, int avgTimeForPage, int totNumberOfViews, int maxPage, int maxTime) {
        this.bookIDRead = bookIDRead;
        this.totReadPages = totReadPages;
        this.avgTimeForPage = avgTimeForPage;
        this.totNumberOfViews = totNumberOfViews;
        this.maxPage = maxPage;
        this.maxTime = maxTime;
    }

    public String getBookIDRead() {
        return bookIDRead;
    }

    public void setBookIDRead(String bookIDRead) {
        this.bookIDRead = bookIDRead;
    }

    public int getTotReadPages() {
        return totReadPages;
    }

    public void setTotReadPages(int totReadPages) {
        this.totReadPages = totReadPages;
    }

    public long getAvgTimeForPage() {
        return avgTimeForPage;
    }

    public void setAvgTimeForPage(long avgTimeForPage) {
        this.avgTimeForPage = avgTimeForPage;
    }

    public int getTotNumberOfViews() {
        return totNumberOfViews;
    }

    public void setTotNumberOfViews(int totNumberOfViews) {
        this.totNumberOfViews = totNumberOfViews;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public long getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(long maxTime) {
        this.maxTime = maxTime;
    }
}
