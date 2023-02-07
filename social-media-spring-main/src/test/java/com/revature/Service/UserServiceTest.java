package com.revature.Service;

import com.revature.models.Department;
import com.revature.models.Employee;
import com.revature.models.User;
import com.revature.repositories.EmployeeRepository;
import com.revature.repositories.UserRepository;
import com.revature.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private UserService userService;

    private User mockUser;
    private Employee mockEmployee;
    private Employee mockEmployee2;
    private Department mockDepartment;
    @BeforeEach
    public void setup(){
        mockDepartment= new Department(1,"trainer");
        mockUser = new User(0, "test@example.com", "password", "John", "Doe", Instant.now());
        mockEmployee = new Employee(1,"Ben","Ten",mockUser,mockDepartment,Instant.now());
        mockEmployee2= new Employee(2, "Ben2","2",mockUser,mockDepartment,Instant.now());
        ArrayList<Employee> followedEmployees = new ArrayList<Employee>();
        followedEmployees.add(mockEmployee);
        followedEmployees.add(mockEmployee2);
        mockUser.setFollowedEmployees(followedEmployees);
    }
    @Test
    void findByCredentialsTest() {
        when(userRepository.findByEmailAndPassword("test@example.com", "password")).thenReturn(Optional.of(mockUser));
        Optional<User> result = userService.findByCredentials("test@example.com", "password");
        assertThat(result.get(), equalTo(mockUser));
    }

    @Test
    void getAllFollowingTest() {
        when(userRepository.findById(0)).thenReturn(Optional.of(mockUser));
        List<Employee> result = userService.getAllFollowing(0);
        assertThat(result, equalTo(mockUser.getFollowedEmployees()));
    }

    @Test
    void getUserByIdTest() {
        when(userRepository.findById(0)).thenReturn(Optional.of(mockUser));
        Optional<User> result = userService.getUserById(0);
        assertThat(result.get(), equalTo(mockUser));
    }

    @Test
    void createUserTest(){
        when(userRepository.save(mockUser)).thenReturn(mockUser);
        User newUser= userService.save(mockUser);
        assertThat(newUser, equalTo(mockUser));
    }

    @Test
    void updatePasswordTest() {
        when(userRepository.findById(0)).thenReturn(Optional.of(mockUser));
        boolean result = userService.updatePassword(0, "newPassword");
        assertThat(result, equalTo(true));
        verify(userRepository, times(1)).save(mockUser);
        assertThat(mockUser.getPassword(), equalTo("newPassword"));
    }

    @Test
    void getUserByNameTest() {
        String search= mockUser.getFirstName();//John
        userRepository.save(mockUser);
        List<User> userList= List.of(new User[]{mockUser});
        when(userRepository.findByFirstNameContainingIgnoreCase(search)).thenReturn(Optional.of(userList));
        List<User> result = userService.getUserByName(search);

        assertThat(result, notNullValue());
        assertThat(result.size(), is(1));
        assertThat(result, hasItem(mockUser));

    }
    @Test
    void followEmployeeTest() {
        Employee newEmployee = new Employee(3,"Ben3","Bee",mockUser,mockDepartment,Instant.now());
        when(userRepository.findById(mockUser.getId())).thenReturn(Optional.of(mockUser));
        when(employeeRepository.findById(newEmployee.getId())).thenReturn(Optional.of(newEmployee));
        when(userRepository.save(mockUser)).thenReturn(mockUser);

        boolean result = userService.follow(mockUser.getId(), newEmployee.getId());
        assertTrue(result);
    }

    @Test
    void unFollowEmployeeTest() {
        when(userRepository.findById(mockUser.getId())).thenReturn(Optional.of(mockUser));
        when(employeeRepository.findById(mockEmployee.getId())).thenReturn(Optional.of(mockEmployee));
        when(userRepository.save(mockUser)).thenReturn(mockUser);
        boolean result = userService.unFollow(mockUser.getId(), mockEmployee.getId());
        assertTrue(result);
    }

}
