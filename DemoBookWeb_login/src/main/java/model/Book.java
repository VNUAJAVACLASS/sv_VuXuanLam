package model;

public class Book {
    private int id;
    private String title;
    private String content;
    private long price; // VND

    public Book(int id, String title, String content, long price) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.price = price;
    }

    public Book(String title, String content, long price) {
        this.title = title;
        this.content = content;
        this.price = price;
    }

    public Book(int id) { this.id = id; }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public long getPrice() { return price; }

    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setPrice(long price) { this.price = price; }
}
