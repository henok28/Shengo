package school.project.shengoapp0.model;

import com.google.gson.annotations.SerializedName;

public class NotificatoinResponseModal {
    @SerializedName("id")
    private String id;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("message")
    private String message;

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }


    public String getMessage() {
        return message;
    }




}
