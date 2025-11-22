package elearn.models;

public class Course {
    private int id;
    private String title;
    private String description;
    private String instructor;
    private String source;   // NEW FIELD

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }

    // ⭐ NEW GETTER/SETTER
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
}
