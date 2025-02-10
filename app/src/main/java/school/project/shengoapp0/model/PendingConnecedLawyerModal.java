package school.project.shengoapp0.model;

// PendingLawyers.java
public class PendingConnecedLawyerModal {
    private LawyerProfile Lawyer_profile; //Note the name, it has to be exactly the same as in the JSON
    private String status;
    private String description;

    public LawyerProfile getLawyer_profile() {
        return Lawyer_profile;
    }

    public void setLawyer_profile(LawyerProfile lawyer_profile) {
        Lawyer_profile = lawyer_profile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() { //Helper method for debugging.
        return "PendingLawyers{" +
                "Lawyer_profile=" + Lawyer_profile +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

