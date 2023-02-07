package com.revature.dtos;

import com.revature.models.Employee;
import com.revature.models.Tag;

import javax.persistence.*;

public class RatingResponse {
    private int id;
    private EmployeeResponse employee;
    private int score;
    private Tag tag1;
    private Tag tag2;
    private Tag tag3;

    public RatingResponse(int id, EmployeeResponse employee, int score, Tag tag1, Tag tag2, Tag tag3) {
        this.id = id;
        this.employee = employee;
        this.score = score;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.tag3 = tag3;
    }

    public RatingResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EmployeeResponse getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeResponse employee) {
        this.employee = employee;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Tag getTag1() {
        return tag1;
    }

    public void setTag1(Tag tag1) {
        this.tag1 = tag1;
    }

    public Tag getTag2() {
        return tag2;
    }

    public void setTag2(Tag tag2) {
        this.tag2 = tag2;
    }

    public Tag getTag3() {
        return tag3;
    }

    public void setTag3(Tag tag3) {
        this.tag3 = tag3;
    }
}
