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
}
