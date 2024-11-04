package school.project.shengoapp0.model;

public class LawyersData {
    String fullName;
    String caseWon;
    int lawyerImg;

    public String getCaseWon() {
        return caseWon;
    }

    public void setCaseWon(String caseWon) {
        this.caseWon = caseWon;
    }

    public LawyersData(String fullName, String caseWon, int lawyerImg) {
        this.fullName = fullName;
        this.caseWon = "Case Won "+caseWon;
        this.lawyerImg = lawyerImg;

    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getLawyerImg() {
        return lawyerImg;
    }

    public void setLawyerImg(int lawyerImg) {
        this.lawyerImg = lawyerImg;
    }
}
