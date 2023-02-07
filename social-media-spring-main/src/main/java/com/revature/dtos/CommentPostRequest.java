package com.revature.dtos;


public class CommentPostRequest {
        private int userId;

        private String text;

        private int imageId;

        private int postId;

    public CommentPostRequest() {
    }

    public CommentPostRequest(int userId, String text, int imageId, int postId) {
        this.userId = userId;
        this.text = text;
        this.imageId = imageId;
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int  getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}
