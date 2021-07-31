package lk.karunathilaka.OLMS.bean;

public class MemberBean {
    private String memberID;
    private String fName;
    private String lName;
    private String gender;
    private String dob;
    private String telephone;
    private String email;
    private String addLine1;
    private String addLine2;
    private String addLine3;
    private String membershipDate;
    private String expireDate;
    private String state;

    public MemberBean(String memberID, String fName, String lName, String gender, String dob, String telephone, String email, String addLine1, String addLine2, String addLine3, String membershipDate, String expireDate, String state) {
        this.memberID = memberID;
        this.fName = fName;
        this.lName = lName;
        this.gender = gender;
        this.dob = dob;
        this.telephone = telephone;
        this.email = email;
        this.addLine1 = addLine1;
        this.addLine2 = addLine2;
        this.addLine3 = addLine3;
        this.membershipDate = membershipDate;
        this.expireDate = expireDate;
        this.state = state;
    }

    public MemberBean() {
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddLine1() {
        return addLine1;
    }

    public void setAddLine1(String addLine1) {
        this.addLine1 = addLine1;
    }

    public String getAddLine2() {
        return addLine2;
    }

    public void setAddLine2(String addLine2) {
        this.addLine2 = addLine2;
    }

    public String getAddLine3() {
        return addLine3;
    }

    public void setAddLine3(String addLine3) {
        this.addLine3 = addLine3;
    }

    public String getMembershipDate() {
        return membershipDate;
    }

    public void setMembershipDate(String membershipDate) {
        this.membershipDate = membershipDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
