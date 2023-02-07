package com.revature.models;

import javax.persistence.*;

@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Employee employee;
    private int score;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "tag_1")
    private Tag tag1;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "tag_2")
    private Tag tag2;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "tag_3")
    private Tag tag3;

    public Rating() {
    }

    public Rating(Employee employee, int score, Tag tag1, Tag tag2, Tag tag3) {
        this.employee = employee;
        this.score = score;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.tag3 = tag3;
    }

    public Rating(int id, Employee employee, int score, Tag tag1, Tag tag2, Tag tag3) {
        this.id = id;
        this.employee = employee;
        this.score = score;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.tag3 = tag3;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
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
