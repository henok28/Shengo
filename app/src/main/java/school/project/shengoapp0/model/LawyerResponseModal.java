package school.project.shengoapp0.model;

public class LawyerResponseModal {
    public LawyerResponseModal(String firstName,
                               String lastName,
                               String maidenName,
                               String email,
                               String phone,
                               String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.maidenName = maidenName;
        this.email = email;
        this.phone = phone;
        this.username = username;
    }

    private int id;
    private String firstName;
    private String lastName;
    private String maidenName;
    private int age;
    private String gender;
    private String email;
    private String phone;
    private String username;
    private String password;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }



    public String getLastName() {
        return lastName;
    }


    public String getMaidenName() {
        return maidenName;
    }


    public int getAge() {
        return age;
    }


    public String getGender() {
        return gender;
    }


    public String getEmail() {
        return email;
    }


    public String getPhone() {
        return phone;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}

