package school.project.shengoapp0.ui.others;

public class OTPModal {
    private String email;
    private String otp;

    public OTPModal(String email, String otp_code) {
        this.email = email;
        this.otp = otp_code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp_code() {
        return otp;
    }

    public void setOtp_code(String otp_code) {
        this.otp = otp_code;
    }
}
