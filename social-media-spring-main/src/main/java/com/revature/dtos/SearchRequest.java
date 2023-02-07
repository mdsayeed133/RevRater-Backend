package com.revature.dtos;

public class SearchRequest {

    private String name;
    private int departmentId;
    private int tagId;

    public SearchRequest(String name, int departmentId, int tagId) {
        this.name = name;
        this.departmentId = departmentId;
        this.tagId = tagId;
    }

    public SearchRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }
}
