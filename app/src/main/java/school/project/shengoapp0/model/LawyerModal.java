package school.project.shengoapp0.model;

public class LawyerModal {
    private final String name;
    private final String specialty;
    private final int profileImage; // Drawable resource ID

    public LawyerModal(String name, String specialty, int profileImage) {
        this.name = name;
        this.specialty = specialty;
        this.profileImage = profileImage;
    }

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public int getProfileImage() {
        return profileImage;
    }
}