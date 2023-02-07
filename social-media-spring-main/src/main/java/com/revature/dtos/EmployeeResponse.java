package com.revature.dtos;

import com.revature.models.Department;

import java.time.Instant;

public class EmployeeResponse {

    private int id;
    private String firstName;
    private String lastName;
    private UserResponse author;
    private Department department;
    private Instant createdDate;

    public EmployeeResponse(int id, String firstName, String lastName, UserResponse author, Department department, Instant createdDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.author = author;
        this.department = department;
        this.createdDate = createdDate;
    }

    public EmployeeResponse()
    {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public UserResponse getAuthor() {
        return author;
    }

    public void setAuthor(UserResponse author) {
        this.author = author;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }
}
