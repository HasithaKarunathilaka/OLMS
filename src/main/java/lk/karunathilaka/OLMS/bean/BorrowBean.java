package lk.karunathilaka.OLMS.bean;

public class BorrowBean {
    private String bookIDBorrow;
    private String memberIDBorrow;
    private String borrowedDate;
    private String dueDate;
    private String issuedBy;
    private String returnedDate;
    private int fine;
    private String acceptedBy;

    public BorrowBean(String bookIDBorrow, String memberIDBorrow, String borrowedDate, String dueDate, String issuedBy, String returnedDate, int fine, String acceptedBy) {
        this.bookIDBorrow = bookIDBorrow;
        this.memberIDBorrow = memberIDBorrow;
        this.borrowedDate = borrowedDate;
        this.dueDate = dueDate;
        this.issuedBy = issuedBy;
        this.returnedDate = returnedDate;
        this.fine = fine;
        this.acceptedBy = acceptedBy;
    }

    public BorrowBean(String bookIDBorrow, String memberIDBorrow, String issuedBy) {
        this.bookIDBorrow = bookIDBorrow;
        this.memberIDBorrow = memberIDBorrow;
        this.issuedBy = issuedBy;
    }

    public BorrowBean(String bookIDBorrow, String acceptedBy) {
        this.bookIDBorrow = bookIDBorrow;
        this.acceptedBy = acceptedBy;
    }

    public String getBookIDBorrow() {
        return bookIDBorrow;
    }

    public void setBookIDBorrow(String bookIDBorrow) {
        this.bookIDBorrow = bookIDBorrow;
    }

    public String getMemberIDBorrow() {
        return memberIDBorrow;
    }

    public void setMemberIDBorrow(String memberIDBorrow) {
        this.memberIDBorrow = memberIDBorrow;
    }

    public String getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(String borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public String getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(String returnedDate) {
        this.returnedDate = returnedDate;
    }

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }

    public String getAcceptedBy() {
        return acceptedBy;
    }

    public void setAcceptedBy(String acceptedBy) {
        this.acceptedBy = acceptedBy;
    }
}
