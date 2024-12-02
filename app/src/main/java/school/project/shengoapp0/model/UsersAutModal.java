package school.project.shengoapp0.model;

import com.google.gson.annotations.SerializedName;

public class UsersAutModal {
    @SerializedName("firstname")
    private String FirstName;
    @SerializedName("lastname")
    private String LastName;
    @SerializedName("email")
    private String Email;

    @SerializedName("password")
    private String password;
    @SerializedName("role")
    private String Role;


    public UsersAutModal(String firstName,
                         String lastName,
                         String email,
                         String passWord) {
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        password = passWord;
        Role = "client";
    }

    public UsersAutModal(String email,
                         String passWord){

        Email = email;
        password = passWord;
        Role = "client";

    }
}
