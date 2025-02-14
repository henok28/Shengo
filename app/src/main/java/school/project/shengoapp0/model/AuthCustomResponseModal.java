package school.project.shengoapp0.model;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

import okhttp3.ResponseBody;

public class AuthCustomResponseModal {
    private User user;

    public User getUser() {
        return user;
    }

    private String status;

    public String getStatus() {
        return status;
    }

    @SerializedName("verification_status")
    private String verificationStatus;

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public String getSubscriptionStatus() {
        return subscriptionStatus;
    }

    @SerializedName("subscription_status")
    private String subscriptionStatus;
    @SerializedName("message")
    private String message;

    @SerializedName("error")
    private String loginError;
    private String token;
    // Getters
    public String getAccess_token() {
        return token;
    }
    public String getMessage() {
        return message;
    }
    public String getLoginError(){
        return loginError;
    }



}
