package com.revature.dtos;


public class RatingDTO {

    private int employeeId;
    private int score;
    private int tags1;
    private int tags2;
    private int tags3;

    public RatingDTO(int employeeId, int score, int tags1, int tags2, int tags3) {
        this.employeeId = employeeId;
        this.score = score;
        this.tags1 = tags1;
        this.tags2 = tags2;
        this.tags3 = tags3;
    }

    public RatingDTO()
    {

    }

    public int getEmployeeId() {
        return employeeId;
    }

    public int getScore() {
        return score;
    }

    public int getTags1() {
        return tags1;
    }

    public int getTags2() {
        return tags2;
    }

    public int getTags3() {
        return tags3;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setTags1(int tags1) {
        this.tags1 = tags1;
    }

    public void setTags2(int tags2) {
        this.tags2 = tags2;
    }

    public void setTags3(int tags3) {
        this.tags3 = tags3;
    }

}
