package com.revature.services;

import com.revature.dtos.AddEmployeeRequest;
import com.revature.exceptions.ProfanityException;
import com.revature.models.Department;
import com.revature.models.Employee;
import com.revature.models.User;
import com.revature.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private UserService userService;

    private DepartmentService departmentService;
    private ProfanityService profService;
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, UserService userService, DepartmentService departmentService, ProfanityService profService) {
        this.employeeRepository = employeeRepository;
        this.userService = userService;
        this.departmentService = departmentService;
        this.profService = profService;
    }

    public Employee getEmployeeById(int empId)
    {
        return employeeRepository.findById(empId).orElse(null);
    }

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public Employee createEmployee(AddEmployeeRequest addEmployeeRequest) throws ProfanityException {
        if(profService.profanityLikely(addEmployeeRequest.getFirstName())||profService.profanityLikely(addEmployeeRequest.getLastName()))
            throw new ProfanityException();
        Department department= departmentService.getDepartmentById(addEmployeeRequest.getDepartmentId());
        User user = userService.getUserById(addEmployeeRequest.getAuthorId()).orElse(null);
        Employee newEmployee = new Employee(addEmployeeRequest.getFirstName(), addEmployeeRequest.getLastName(), user,department, Instant.now());
        Employee createdEmployee= employeeRepository.save(newEmployee);
        //auto follow employee when created
        userService.follow(addEmployeeRequest.getAuthorId(),createdEmployee.getId());
        return createdEmployee;
    }

    public List<Employee> getEmployeeByDepartment(int departmentId){
        Department department = departmentService.getDepartmentById(departmentId);
        List<Employee> employees= employeeRepository.findByDepartment(department).orElse(null);
        return new ArrayList<>(employees);
    }

    public List<Employee> getEmployeeByName(String search){
        Set<Employee> allResults= new HashSet<>();
        List<Employee> firstResults = employeeRepository.findByFirstNameContainingIgnoreCase(search).orElse(null);
        List<Employee> lastResults = employeeRepository.findByLastNameContainingIgnoreCase(search).orElse(null);
        if (firstResults!=null)allResults.addAll(firstResults);
        if(lastResults!=null)allResults.addAll(lastResults);
        return new ArrayList<>(allResults);
    }

}
