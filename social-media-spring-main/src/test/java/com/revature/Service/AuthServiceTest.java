package com.revature.Service;

import com.revature.dtos.RegisterRequest;
import com.revature.models.User;
import com.revature.services.AuthService;
import com.revature.services.ProfanityService;
import com.revature.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private UserService userService;
    @Mock
    private ProfanityService profanityService;

    @InjectMocks
    private AuthService authService;

    @Test
    void testFindByCredentials() {
        User mockUser = new User();
        mockUser.setEmail("user@example.com");
        mockUser.setPassword("password");

        when(userService.findByCredentials("user@example.com", "password")).thenReturn(Optional.of(mockUser));

        Optional<User> result = authService.findByCredentials("user@example.com", "password");

        assertThat(result, equalTo(Optional.of(mockUser)));
    }

    @Test
    void testRegister() {
        User mockUser = new User(0, "test@example.com", "password", "John", "Doe", Instant.now());
        RegisterRequest request = new RegisterRequest("test@example.com","password","John","Doe");
        when(profanityService.profanityLikely(anyString())).thenReturn(false);
        when(userService.save(any())).thenReturn(mockUser);

        User result = authService.register(request);

        assertThat(result, equalTo(mockUser));
    }

}
