package com.revature.controllers;

import com.revature.dtos.EmployeeResponse;
import com.revature.dtos.UserResponse;
import com.revature.models.Employee;
import com.revature.models.Tag;
import com.revature.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rating/post")
@CrossOrigin
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }


    @GetMapping("/employees/{tagId}/tagSearch")
    public ResponseEntity<List<EmployeeResponse>> searchEmployeesByTag(@PathVariable int tagId) {
        List<Employee> employees = ratingService.searchEmployeesByTag(tagId);
        if (employees == null) {
            return ResponseEntity.badRequest().build();
        }
        List<EmployeeResponse> responseDTOS = new ArrayList<>();
        for(Employee employee:employees){
            UserResponse userResponse = new UserResponse(employee.getAuthor().getId(),employee.getAuthor().getEmail(),employee.getAuthor().getPassword(),employee.getAuthor().getFirstName(),employee.getAuthor().getLastName(),employee.getAuthor().getCreatedDate());
            EmployeeResponse dto= new EmployeeResponse(employee.getId(),employee.getFirstName(),employee.getLastName(), userResponse,employee.getDepartment(),employee.getCreatedDate());
            responseDTOS.add(dto);
        }
        return ResponseEntity.ok(responseDTOS);
    }

    @GetMapping("/employee/{employeeId}/average")
    public ResponseEntity<Double> getEmployeeAvgRating(@PathVariable int employeeId) {
        try {
            Double avgRating = ratingService.getEmployeeAvgRating(employeeId);
            return ResponseEntity.ok(avgRating);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/employee/{employeeId}/top3tags")
    public ResponseEntity<List<Tag>> getTop3TagsOfEmployee(@PathVariable int employeeId) {
        List<Tag> top3Tags = ratingService.getTop3TagsOfEmployee(employeeId);
        if (top3Tags == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(top3Tags);
    }

    @GetMapping("/top3employees")
    public ResponseEntity<List<EmployeeResponse>> getTop3Employees() {
        try {
            List<Employee> employees = ratingService.getTop3Employees();
            List<EmployeeResponse> responseDTOS = new ArrayList<>();
            for(Employee employee:employees){
                UserResponse userResponse = new UserResponse(employee.getAuthor().getId(),employee.getAuthor().getEmail(),employee.getAuthor().getPassword(),employee.getAuthor().getFirstName(),employee.getAuthor().getLastName(),employee.getAuthor().getCreatedDate());
                EmployeeResponse dto= new EmployeeResponse(employee.getId(),employee.getFirstName(),employee.getLastName(), userResponse,employee.getDepartment(),employee.getCreatedDate());
                responseDTOS.add(dto);
            }
            return ResponseEntity.ok(responseDTOS);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}

