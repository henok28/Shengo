package school.project.shengoapp0.model.connectedlawyersmodal;

// ConnectedLawyerModal.java
public class ConnectedLawyerModal {
    private String request_id;
    private String description;
    private String status;
    private ClientProfile Lawyer_profile;
    private String updated_at;

    // Getters and setters
    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ClientProfile getClient_profile() {
        return Lawyer_profile;
    }

    public void setClient_profile(ClientProfile client_profile) {
        this.Lawyer_profile = client_profile;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {  //Helper method for debugging
        return "ConnectedLawyerModal{" +
                "request_id='" + request_id + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", client_profile=" + Lawyer_profile +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }

}
