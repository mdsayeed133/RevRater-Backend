package com.revature.Service;

import com.revature.dtos.AddEmployeeRequest;
import com.revature.models.Department;
import com.revature.models.Employee;
import com.revature.models.User;
import com.revature.repositories.EmployeeRepository;
import com.revature.services.DepartmentService;
import com.revature.services.EmployeeService;
import com.revature.services.ProfanityService;
import com.revature.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @Mock
    private UserService userService;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private DepartmentService departmentService;
    @Mock
    private ProfanityService profanityService;
    @InjectMocks
    private EmployeeService employeeService;

    private User mockUser;
    private Employee mockEmployee;
    private Department mockDepartment;
    @BeforeEach
    public void setup(){
        mockDepartment= new Department(1,"trainer");
        mockUser = new User(0, "test@example.com", "password", "John", "Doe", Instant.now());
        mockEmployee = new Employee(1,"Ben","1",mockUser,mockDepartment,Instant.now());
    }

    @Test
    void testGetEmployeeByName()
    {
        String search= mockEmployee.getFirstName();//John
        employeeRepository.save(mockEmployee);
        List<Employee> employeeList= List.of(new Employee[]{mockEmployee});
        when(employeeRepository.findByFirstNameContainingIgnoreCase(search)).thenReturn(Optional.of(employeeList));
        List<Employee> result = employeeService.getEmployeeByName(search);

        assertThat(result, notNullValue());
        assertThat(result.size(), is(1));
        assertThat(result, hasItem(mockEmployee));
    }

    @Test
    void testGetEmployeeById() {
        when(employeeRepository.findById(mockEmployee.getId())).thenReturn(Optional.of(mockEmployee));
        Employee result = employeeService.getEmployeeById(mockEmployee.getId());

        assertThat(mockEmployee, equalTo(result));
    }

    @Test
    void testGetAllEmployees() {
        List<Employee> employees = Arrays.asList(mockEmployee);
        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> result = employeeService.getAllEmployees();

        assertThat(employees, equalTo(result));
    }


    @Test
    void testCreateEmployee() {
        AddEmployeeRequest addEmployeeRequest = new AddEmployeeRequest(mockEmployee.getFirstName(),mockEmployee.getLastName(),mockEmployee.getAuthor().getId(),mockDepartment.getId());
        when(profanityService.profanityLikely(any())).thenReturn(false);
        when(userService.getUserById(mockUser.getId())).thenReturn(Optional.of(mockUser));
        when(departmentService.getDepartmentById(addEmployeeRequest.getDepartmentId())).thenReturn(mockDepartment);
        when(employeeRepository.save(any())).thenReturn(mockEmployee);

        Employee result = employeeService.createEmployee(addEmployeeRequest);

        assertThat(mockEmployee, equalTo(result));
    }

    @Test
     void testGetEmployeeByDepartment() {
        List<Employee> employees = Arrays.asList(mockEmployee);
        when(departmentService.getDepartmentById(mockDepartment.getId())).thenReturn(mockDepartment);
        when(employeeRepository.findByDepartment(mockDepartment)).thenReturn(Optional.of(employees));

        List<Employee> result = employeeService.getEmployeeByDepartment(mockDepartment.getId());

        assertThat(employees, equalTo(result));
    }
}

