package com.revature.models;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(columnDefinition = "TEXT", nullable = false)
	private String message;
	private int imageId;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Post> comments;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(nullable = false)
	private User author;

	private PostType postType;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "rating_id")
	private Rating rating;

	@CreatedDate
	private Instant createdDate;

	public Post() {
	}

	public Post(String message, int imageId, List<Post> comments, User author, PostType postType, Rating rating, Instant createdDate) {
		this.message = message;
		this.imageId = imageId;
		this.comments = comments;
		this.author = author;
		this.postType = postType;
		this.rating = rating;
		this.createdDate = createdDate;
	}

	public Post(int id, String message, int imageId, List<Post> comments, User author, PostType postType, Rating rating, Instant createdDate) {
		this.id = id;
		this.message = message;
		this.imageId = imageId;
		this.comments = comments;
		this.author = author;
		this.postType = postType;
		this.rating = rating;
		this.createdDate = createdDate;
	}

	public Post(int id, String message, int imageId, List<Post> comments, User author, PostType postType, Instant createdDate) {
		this.id = id;
		this.message = message;
		this.imageId = imageId;
		this.comments = comments;
		this.author = author;
		this.postType = postType;
		this.createdDate = createdDate;
	}

	public Post(String message, List<Post> comments, User author, PostType postType) {
		this.message = message;
		this.comments = comments;
		this.author = author;
		this.postType = postType;
	}

	public Post(String message, int imageId, List<Post> comments, User author, PostType postType, Rating rating) {
		this.message = message;
		this.imageId = imageId;
		this.comments = comments;
		this.author = author;
		this.postType = postType;
		this.rating = rating;
	}

	public Post(int id, String message) {
		this.id = id;
		this.message = message;
	}

	public Post(String text, int imageId, List<Post> comments, User user, PostType type, Instant now) {
		this.message = text;
		this.imageId = imageId;
		this.comments = comments;
		this.author = user;
		this.postType = type;
		this.createdDate = now;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public List<Post> getComments() {
		return comments;
	}

	public void setComments(List<Post> comments) {
		this.comments = comments;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public PostType getPostType() {
		return postType;
	}

	public void setPostType(PostType postType) {
		this.postType = postType;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}