package school.project.shengoapp0.model;

public class LawyerModal {

    private String user_id;

    public String getUser_id() {
        return user_id;
    }

    private String name;

    private String specialty;
    private String email;
    private String phone;
    private String city;
    private String yearsOfExperiance;
    private String profilePictureUrl;

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    //    private final int profileImage; // Drawable resource ID
    private final String address;

    public String getSpecialty() {
        return specialty;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCity() {
        return city;
    }

    public String getYearsOfExperiance() {
        return yearsOfExperiance;
    }

    public String getAddress() {
        return address;
    }



    public String getName() {
        return name;
    }

    public LawyerModal(String name,
                       String specialty,
                       String email,
                       String phone,
                       String city,
                       String address,
                       String yearsOfExperiance,
                       String profilePictureUrl,
                       String user_id) {
        this.name = name;
        this.specialty = specialty;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.yearsOfExperiance = yearsOfExperiance;
        this.profilePictureUrl = profilePictureUrl;
        this.user_id = user_id;
    }

}