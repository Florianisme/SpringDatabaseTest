package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Posts")
public class Post {

	@Id
	protected Long id;

	@Column
	protected String title;

	@Column
	protected String author;

	@Column
	protected String content;

	public Post() {
	}

	public Post(Long id, String title, String author, String content) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public Post setId(Long id) {
		this.id = id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Post setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getAuthor() {
		return author;
	}

	public Post setAuthor(String author) {
		this.author = author;
		return this;
	}

	public String getContent() {
		return content;
	}

	public Post setContent(String content) {
		this.content = content;
		return this;
	}
}
