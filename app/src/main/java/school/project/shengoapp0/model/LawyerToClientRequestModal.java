package school.project.shengoapp0.model;

public class LawyerToClientRequestModal {
    private String description;

    private String status;

    public String getStatus() {
        return status;
    }

    public LawyerToClientRequestModal(String description) {
        this.description = description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
