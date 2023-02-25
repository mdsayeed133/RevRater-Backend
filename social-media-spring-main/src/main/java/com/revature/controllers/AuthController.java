package com.revature.controllers;

import com.revature.dtos.LoginRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.dtos.UserResponse;
import com.revature.exceptions.ProfanityException;
import com.revature.models.User;
import com.revature.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    //Login works
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        User user = authService.findByCredentials(loginRequest.getEmail(), loginRequest.getPassword()).get();
        if(user == null) return ResponseEntity.badRequest().build();
        UserResponse uDTO = new UserResponse(user.getId(), user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getCreatedDate());
        session.setAttribute("user", user);

        return ResponseEntity.ok(uDTO);
    }

    //Logout works
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        if(session.getAttribute("user") == (null))
        {
            return ResponseEntity.ok().body("You wasn't logged in foo!");
        }
        session.removeAttribute("user");

        return ResponseEntity.ok().body("You've been successfully signed out");
    }



    //Register works
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest registerRequest, HttpSession session) {
       try {
           User user = authService.register(registerRequest);
           UserResponse uDTO = new UserResponse(user.getId(), user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getCreatedDate());
           session.setAttribute("user", user);
           return ResponseEntity.status(HttpStatus.CREATED).body(uDTO);
       } catch (ProfanityException pe){
           return  ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
       } catch (Exception e){
           return ResponseEntity.badRequest().body(new UserResponse(-1,e.getMessage(),registerRequest.toString(),"","",null));
       }
    }


}
