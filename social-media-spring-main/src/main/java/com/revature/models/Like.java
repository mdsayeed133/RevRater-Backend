package com.revature.models;

import javax.persistence.*;

@Entity
@Table(name = "likes", uniqueConstraints = { @UniqueConstraint(columnNames = { "post_id", "user_id" }) })
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Like() {
    }

    public Like(int id, Post post, User user) {
        this.id = id;
        this.post = post;
        this.user = user;
    }

    public Like(Post post, User user) {
        this.post = post;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
