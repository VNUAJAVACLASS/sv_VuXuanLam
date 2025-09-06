package model;

public class Book {

	private int id;
	private String title;
	private String content;

	public Book(int id, String title, String content) {
		this.id = id;
		this.title = title;
		this.content = content;
	}

	public Book(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public Book(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
