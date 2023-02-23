package com.revature.Controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controllers.AuthController;
import com.revature.dtos.LoginRequest;
import com.revature.models.User;
import com.revature.services.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthController.class)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Test
    void LoginTest() throws Exception {
        String email = "test@test.com";
        String password = "testpassword";
        User user = new User(1, email, password, "John", "Doe", Instant.now());

        // Create a LoginRequest object with the email and password
        LoginRequest loginRequest = new LoginRequest(email, password);

        // Set up the mock authentication service to return the user
        when(authService.findByCredentials(email, password)).thenReturn(Optional.of(user));

        // Send a POST request to the /login endpoint with a JSON body containing the LoginRequest object
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.email", equalTo(email)))
                .andExpect(jsonPath("$.firstName", equalTo("John")))
                .andExpect(jsonPath("$.lastName", equalTo("Doe")))
                .andExpect(jsonPath("$.createdDate", notNullValue()));
    }

    // Helper method to convert a Java object to JSON
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
