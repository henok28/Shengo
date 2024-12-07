package school.project.shengoapp0.model;

import com.google.gson.annotations.SerializedName;

public class VerificationFormModal {
    private String message;
    private Data data;

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    // Nested Data class
    public static class Data {
        private String userId;
        private String fullName;
        private String phoneNumber;
        private String dateOfBirth;
        private String gender;
        private String residentialAddress;
        private String city;
        private String state;
        private String licenseNumber;
        private String yearsOfExperience;
        private String specialization;
        private String attachedDocument;
        private String profilePicture;
        private String idPhoto;
        private String id;
        private String updatedAt;
        private String createdAt;

        // Getters and Setters
        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getResidentialAddress() {
            return residentialAddress;
        }

        public void setResidentialAddress(String residentialAddress) {
            this.residentialAddress = residentialAddress;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getLicenseNumber() {
            return licenseNumber;
        }

        public void setLicenseNumber(String licenseNumber) {
            this.licenseNumber = licenseNumber;
        }

        public String getYearsOfExperience() {
            return yearsOfExperience;
        }

        public void setYearsOfExperience(String yearsOfExperience) {
            this.yearsOfExperience = yearsOfExperience;
        }

        public String getSpecialization() {
            return specialization;
        }

        public void setSpecialization(String specialization) {
            this.specialization = specialization;
        }

        public String getAttachedDocument() {
            return attachedDocument;
        }

        public void setAttachedDocument(String attachedDocument) {
            this.attachedDocument = attachedDocument;
        }

        public String getProfilePicture() {
            return profilePicture;
        }

        public void setProfilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
        }

        public String getIdPhoto() {
            return idPhoto;
        }

        public void setIdPhoto(String idPhoto) {
            this.idPhoto = idPhoto;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }
    }
}
