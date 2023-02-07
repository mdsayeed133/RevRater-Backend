package com.revature.dtos;


public class RatingPostRequest {

    private int userId;

    private String text;

    private int imageId;

    private RatingDTO ratingDTO;



    public RatingPostRequest() {
    }

    public RatingPostRequest(int userId, String text, int imageId, RatingDTO ratingDTO) {
        this.userId = userId;
        this.text = text;
        this.imageId = imageId;
        this.ratingDTO = ratingDTO;
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

    public RatingDTO getRatingDTO() {
        return ratingDTO;
    }

    public void setRatingDTO(RatingDTO ratingDTO) {
        this.ratingDTO = ratingDTO;
    }
}
