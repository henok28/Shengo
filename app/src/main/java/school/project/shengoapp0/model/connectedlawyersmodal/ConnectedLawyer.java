package school.project.shengoapp0.model.connectedlawyersmodal;

public class ConnectedLawyer {
    private String proPic;
    private String firstName;
    private String middleName;

    public ConnectedLawyer(String proPic, String firstName, String middleName) {
        this.proPic = proPic;
        this.firstName = firstName;
        this.middleName = middleName;
    }

    public String getProPic() {
        return proPic;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }
}
