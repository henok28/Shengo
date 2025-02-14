package school.project.shengoapp0.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private String id;

    public String getId() {
        return id;
    }
}
