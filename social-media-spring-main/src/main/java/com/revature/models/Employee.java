package com.revature.models;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "author_user_id")
    private User author;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_follow_employee",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> followers;


    @CreatedDate
    @Column(name = "date", updatable = false)
    private Instant createdDate;

    public Employee() {
    }

    public Employee(String firstName, String lastName, User author, Department department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.author = author;
        this.department = department;
    }

    public Employee(String firstName, String lastName, User author, Department department, Instant createdDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.author = author;
        this.department = department;
        this.createdDate = createdDate;
    }

    public Employee(int id, String firstName, String lastName, User author, Department department, List<User> followers, Instant createdDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.author = author;
        this.department = department;
        this.followers = followers;
        this.createdDate = createdDate;
    }

    public Employee(String firstName, String lastName, User author, Department department, List<User> followers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.author = author;
        this.department = department;
        this.followers = followers;
    }

    public Employee(int id, String firstName, String lastName, User author, Department department, Instant createdDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.author = author;
        this.department = department;
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }
}

