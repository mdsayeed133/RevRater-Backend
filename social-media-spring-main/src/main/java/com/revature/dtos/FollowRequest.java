package com.revature.dtos;

public class FollowRequest {
    private int userId;
    private int employeeId;

    public FollowRequest(int userId, int employeeId) {
        this.userId = userId;
        this.employeeId = employeeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        employeeId = employeeId;
    }
}
