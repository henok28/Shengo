package school.project.shengoapp0.model;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

import okhttp3.ResponseBody;

public class AuthCustomResponseModal {
    @SerializedName("message")
    private String message;

    @SerializedName("error")
    private String loginError;
    private String access_token;
    // Getters
    public String getAccess_token() {
        return access_token;
    }
    public String getMessage() {
        return message;
    }
    public String getLoginError(){
        return loginError;
    }



}
