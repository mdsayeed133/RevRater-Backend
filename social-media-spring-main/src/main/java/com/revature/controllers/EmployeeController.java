package com.revature.controllers;

import com.revature.dtos.AddEmployeeRequest;
import com.revature.dtos.EmployeeResponse;
import com.revature.dtos.UserResponse;
import com.revature.exceptions.ProfanityException;
import com.revature.models.Employee;
import com.revature.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employee")
@CrossOrigin
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}/id")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable int id){
        Employee employee = employeeService.getEmployeeById(id);
        if(employee == null){
            return ResponseEntity.notFound().build();
        }
        UserResponse userResponse = new UserResponse(employee.getAuthor().getId(),employee.getAuthor().getEmail(),employee.getAuthor().getPassword(),employee.getAuthor().getFirstName(),employee.getAuthor().getLastName(),employee.getAuthor().getCreatedDate());
        EmployeeResponse eDTO = new EmployeeResponse(employee.getId(), employee.getFirstName(), employee.getLastName(), userResponse, employee.getDepartment(), employee.getCreatedDate());

        return ResponseEntity.ok(eDTO);
    }

    @GetMapping()
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees(){
        List<Employee> employees = employeeService.getAllEmployees();
        if(employees.isEmpty()){return ResponseEntity.noContent().build();}

        List<EmployeeResponse> responseDTOS = new ArrayList<>();
        for(Employee employee:employees){
            UserResponse userResponse = new UserResponse(employee.getAuthor().getId(),employee.getAuthor().getEmail(),employee.getAuthor().getPassword(),employee.getAuthor().getFirstName(),employee.getAuthor().getLastName(),employee.getAuthor().getCreatedDate());
            EmployeeResponse dto= new EmployeeResponse(employee.getId(),employee.getFirstName(),employee.getLastName(), userResponse,employee.getDepartment(),employee.getCreatedDate());
            responseDTOS.add(dto);
        }
        return ResponseEntity.ok(responseDTOS);
    }

    @PostMapping()
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody AddEmployeeRequest addEmployeeRequest) {
        try {
            Employee employee = employeeService.createEmployee(addEmployeeRequest);
            UserResponse userResponse = new UserResponse(employee.getAuthor().getId(),employee.getAuthor().getEmail(),employee.getAuthor().getPassword(),employee.getAuthor().getFirstName(),employee.getAuthor().getLastName(),employee.getAuthor().getCreatedDate());
            EmployeeResponse dto= new EmployeeResponse(employee.getId(),employee.getFirstName(),employee.getLastName(), userResponse,employee.getDepartment(),employee.getCreatedDate());
            return ResponseEntity.ok(dto);
        } catch (ProfanityException pe){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/{departmentId}/department")
    public ResponseEntity<List<EmployeeResponse>> getEmployeeByDepartment(@PathVariable int departmentId){
        List<Employee> employees = employeeService.getEmployeeByDepartment(departmentId);
        if(employees == null){
            return ResponseEntity.notFound().build();
        }
        List<EmployeeResponse> responseDTOS = new ArrayList<>();
        for(Employee employee:employees){
            UserResponse userResponse = new UserResponse(employee.getAuthor().getId(),employee.getAuthor().getEmail(),employee.getAuthor().getPassword(),employee.getAuthor().getFirstName(),employee.getAuthor().getLastName(),employee.getAuthor().getCreatedDate());
            EmployeeResponse dto= new EmployeeResponse(employee.getId(),employee.getFirstName(),employee.getLastName(), userResponse,employee.getDepartment(),employee.getCreatedDate());
            responseDTOS.add(dto);
        }
        return ResponseEntity.ok(responseDTOS);
    }

    @GetMapping("/{search}/search")
    public ResponseEntity<List<EmployeeResponse>> getEmployeeByName(@PathVariable String search){
        List<Employee> employees = employeeService.getEmployeeByName(search);
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