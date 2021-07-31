package lk.karunathilaka.OLMS.bean;

public class PublisherBean {
    private String publisherID;
    private String name;
    private String telephone;
    private String email;
    private String state;

    public PublisherBean(String publisherID, String name, String telephone, String email, String state) {
        this.publisherID = publisherID;
        this.name = name;
        this.telephone = telephone;
        this.email = email;
        this.state = state;
    }

    public PublisherBean() {
    }

    public String getPublisherID() {
        return publisherID;
    }

    public void setPublisherID(String publisherID) {
        this.publisherID = publisherID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
