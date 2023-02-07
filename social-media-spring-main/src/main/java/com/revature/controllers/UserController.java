package com.revature.controllers;

import com.revature.dtos.EmployeeResponse;
import com.revature.dtos.FollowRequest;
import com.revature.dtos.PasswordResetRequest;
import com.revature.dtos.UserResponse;
import com.revature.models.Employee;
import com.revature.models.User;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}/id")
    public ResponseEntity<UserResponse> getUserById(@PathVariable int id){
        Optional<User> optional = userService.getUserById(id);
        if(optional.isPresent()){
            User user= optional.get();
            UserResponse dto= new UserResponse(user.getId(), user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(),user.getCreatedDate());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/followed/{userId}/id")
    public ResponseEntity<List<EmployeeResponse>> getAllFollowing(@PathVariable int userId){
        List<Employee> followedEmployees = userService.getAllFollowing(userId);
        if(followedEmployees != null) {
            List<EmployeeResponse> responseDTOS = new ArrayList<>();
            for(Employee employee:followedEmployees){
                UserResponse userResponse = new UserResponse(employee.getAuthor().getId(),employee.getAuthor().getEmail(),employee.getAuthor().getPassword(),employee.getAuthor().getFirstName(),employee.getAuthor().getLastName(),employee.getAuthor().getCreatedDate());
                EmployeeResponse dto= new EmployeeResponse(employee.getId(),employee.getFirstName(),employee.getLastName(), userResponse,employee.getDepartment(),employee.getCreatedDate());
                responseDTOS.add(dto);
            }
            return ResponseEntity.ok(responseDTOS);}
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<Object> updatePassword(@RequestBody PasswordResetRequest passwordResetRequest){
        boolean result = userService.updatePassword(passwordResetRequest.getUserId(), passwordResetRequest.getPassword());
        if(result) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{search}/search")
    public ResponseEntity<List<UserResponse>> searchUsers(@PathVariable String search){
        List<User> searchResults = userService.getUserByName(search);
        if(searchResults != null){
            List<UserResponse> UserDTOs= new ArrayList<>();
            for (User user : searchResults) {
                UserResponse dto= new UserResponse(user.getId(), user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(),user.getCreatedDate());
                UserDTOs.add(dto);
            }
            return ResponseEntity.ok(UserDTOs);}
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/follow")
    public ResponseEntity<Object> follow(@RequestBody FollowRequest request){
        boolean result = userService.follow(request.getUserId(), request.getEmployeeId());
        if(result) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/unfollow")
    public ResponseEntity<Object> unFollow(@RequestBody FollowRequest request){
        boolean result = userService.unFollow(request.getUserId(), request.getEmployeeId());
        if(result) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

}


