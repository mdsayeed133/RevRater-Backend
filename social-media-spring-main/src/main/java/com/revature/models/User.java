package com.revature.models;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "email", unique = true)
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    @ManyToMany(mappedBy = "followers")
    private List<Employee> followedEmployees;

    @CreatedDate
    @Column(name = "date", updatable = false)
    private Instant createdDate;

    public User() {
    }

    public User(int id, String email, String password, String firstName, String lastName, Instant createdDate) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdDate = createdDate;
    }

    public User(int id, String email, String password, String firstName, String lastName, List<Employee> followedEmployees, Instant createdDate) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.followedEmployees = followedEmployees;
        this.createdDate = createdDate;
    }

    public User(String email, String password, String firstName, String lastName, List<Employee> followedEmployees, Instant createdDate) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.followedEmployees = followedEmployees;
        this.createdDate = createdDate;
    }

    public User(String email, String password, String firstName, String lastName, List<Employee> followedEmployees) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.followedEmployees = followedEmployees;
    }

    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String email, String password, String firstName, String lastName, Instant createdDate) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Employee> getFollowedEmployees() {
        return followedEmployees;
    }

    public void setFollowedEmployees(List<Employee> followedEmployees) {
        this.followedEmployees = followedEmployees;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }
}
