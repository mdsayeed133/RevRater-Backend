package com.revature.dtos;

public class AddEmployeeRequest {
    private String firstName;
    
    private String lastName;
    private int authorId;
    private int departmentId;

    public AddEmployeeRequest() {}

    public AddEmployeeRequest(String firstName, String lastName, int authorId, int departmentId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.authorId = authorId;
        this.departmentId = departmentId;
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

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
}
