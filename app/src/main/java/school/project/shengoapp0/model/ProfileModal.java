package school.project.shengoapp0.model;

import com.google.gson.annotations.SerializedName;

public class ProfileModal {

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("middle_name")
    private String middleName;

    @SerializedName("phone_number")
    private String phoneNumber;
    @SerializedName("profile_picture")
    private String profilePicture;

    @SerializedName("verification_status")
    private String verificationStatus;

    @SerializedName("subscription_status")
    private String subscriptionStatus;

    public String getEmail() {
        return email;
    }

    public ProfileModal(String firstName, String middleName, String phoneNumber, String profilePicture, String verificationStatus, String subscriptionStatus, String email) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.phoneNumber = phoneNumber;
        this.profilePicture = profilePicture;
        this.verificationStatus = verificationStatus;
        this.subscriptionStatus = subscriptionStatus;
        this.email = email;
    }

    @SerializedName("email")
    private String email;


    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public String getSubscriptionStatus() {
        return subscriptionStatus;
    }
}
