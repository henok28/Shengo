package school.project.shengoapp0.model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class LawyerResponseModal {
    private String id;
    private String userId;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;


    private String email;
    private String role;

    @SerializedName("profile_picture")
    private String profilePicture;

    @SerializedName("phone_number")
    private String phoneNumber;

    private String dateOfBirth;


    private String gender;

    @SerializedName("address")
    private String address;
    private String city;
    private String state;

    @SerializedName("year_of_experience")
    private String yearOfExperience;
    private String specialization;
    private String verificationStatus;
    private String subscriptionStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getYearOfExperience() {
        return yearOfExperience;
    }
}

