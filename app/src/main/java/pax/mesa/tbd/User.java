package pax.mesa.tbd;

import java.util.ArrayList;

public class User {
    public String fName;
    public String lName;
    public String email;
    //public ArrayList classes; // this might be wrong

    public User() {

    }

    public User(String FName, String LName, String Email) {
        this.fName = FName;
        this.lName = LName;
        this.email = Email;
        //this.classes = Classes;
    }
}
