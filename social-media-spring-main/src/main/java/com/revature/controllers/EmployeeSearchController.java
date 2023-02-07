package com.revature.controllers;

import com.revature.dtos.EmployeeResponse;
import com.revature.dtos.SearchRequest;
import com.revature.dtos.UserResponse;
import com.revature.models.Employee;
import com.revature.services.EmployeeSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employee/advance/search")
public class EmployeeSearchController {

    private EmployeeSearchService employeeSearchService;
    @Autowired
    public EmployeeSearchController(EmployeeSearchService employeeSearchService) {
        this.employeeSearchService = employeeSearchService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> combinedEmployeeSearch(@RequestBody SearchRequest searchRequest){
        List<Employee> employees = employeeSearchService.combinedEmployeeSearch(searchRequest);
        if(employees.isEmpty()){return ResponseEntity.noContent().build();}
        List<EmployeeResponse> responseDTOS = new ArrayList<>();
        for(Employee employee:employees){
            UserResponse userResponse = new UserResponse(employee.getAuthor().getId(),employee.getAuthor().getEmail(),employee.getAuthor().getPassword(),employee.getAuthor().getFirstName(),employee.getAuthor().getLastName(),employee.getAuthor().getCreatedDate());
            EmployeeResponse dto= new EmployeeResponse(employee.getId(),employee.getFirstName(),employee.getLastName(), userResponse,employee.getDepartment(),employee.getCreatedDate());
            responseDTOS.add(dto);
        }
        return ResponseEntity.ok(responseDTOS);
    }
}
