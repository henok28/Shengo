package school.project.shengoapp0.model;

public class ChatMessage {
    private String userName;
    private String message;
    private int profileImage;

    public ChatMessage(String userName, String message, int profileImage) {
        this.userName = userName;
        this.message = message;
        this.profileImage = profileImage;
    }

    public String getUserName() {
        return userName;
    }

    public String getMessage() {
        return message;
    }

    public int getProfileImage() {
        return profileImage;
    }
}