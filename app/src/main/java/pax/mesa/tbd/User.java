package pax.mesa.tbd;

import java.util.List;

public class User {
    public String fName;
    public String lName;
    public String email;
    public List classes;

    public User() {

    }

    public User(String FName, String LName, String Email, List Classes) {
        this.fName = FName;
        this.lName = LName;
        this.email = Email;
        this.classes = Classes;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List getClasses() {
        return classes;
    }

    public void setClasses(List classes) {
        this.classes = classes;
    }
}
