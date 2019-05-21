package in.codepredators.contactspot;

public class DetailedContact {

    private String Name;
    private String Phone;
    private String Email;
    private String Address;
    private String Job;
    private String Company;
    private String Notes;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getJob() {
        return Job;
    }

    public void setJob(String job) {
        Job = job;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    DetailedContact(){
        Name = "";
        Phone = "";
        Email = "";
        Address = "";
        Job = "";
        Company = "";
        Notes = "";
    }
}
