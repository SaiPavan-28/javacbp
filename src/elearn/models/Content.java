package elearn.models;

public class Content {
    private int id;
    private String title;
    private String description;
    private String filePath;

    public Content() {}

    public Content(String title, String description, String filePath) {
        this.title = title;
        this.description = description;
        this.filePath = filePath;
    }

    public Content(int id, String title, String description, String filePath) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.filePath = filePath;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
}
