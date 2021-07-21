package lk.karunathilaka.OLMS.bean;

public class ReadBean {
    private String bookIDRead;
    private int totReadPages;
    private int avgTimeForPage;
    private int totNumberOfViews;
    private int maxPage;
    private int maxTime;

    public ReadBean() {
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

    public int getAvgTimeForPage() {
        return avgTimeForPage;
    }

    public void setAvgTimeForPage(int avgTimeForPage) {
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

    public int getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }
}
