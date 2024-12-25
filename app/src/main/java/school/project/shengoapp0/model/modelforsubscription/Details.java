package school.project.shengoapp0.model.modelforsubscription;

import com.google.gson.annotations.SerializedName;

public class Details{
    private String message;
    private String status;

    private Data data;

    public String getStatus() {
        return status;
    }

    public Data getData() {
        return data;
    }
}
