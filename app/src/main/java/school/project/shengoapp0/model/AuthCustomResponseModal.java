package school.project.shengoapp0.model;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

import okhttp3.ResponseBody;

public class AuthCustomResponseModal {
    private String message;

    @SerializedName("error")
    private String loginError;
    private String access_token;

    private Map<String, String[]> errors; // For the "errors" object in the response

    // Getters
    public String getMessage() {
        return message;
    }
    public String getLoginError(){
        return loginError;
    }

    public String getAccess_token() {
        return access_token;
    }

    public Map<String, String[]> getErrors() {
        return errors;
    }

    // Helper to get the first error message (if available)
    public String getFirstErrorMessage() {
        if (errors != null && errors.containsKey("email")) {
            String[] emailErrors = errors.get("email");
            if (emailErrors != null && emailErrors.length > 0) {
                return emailErrors[0]; // Return the first error message
            }
        }
        return null;
    }
}
