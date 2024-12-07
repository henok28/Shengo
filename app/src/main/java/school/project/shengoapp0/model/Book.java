package school.project.shengoapp0.model;

public class Book {
    private String title;
    private String author;
    private double rating;
    private String category;
    private String description;

    // Constructor
    public Book(String title, String category, String description) {
        this.title = title;
        this.category = category;
        this.description = description;
    }

    // Getters
    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getRating() {
        return rating;
    }
}
