package com.revature.services;

import com.revature.dtos.RegisterRequest;
import com.revature.exceptions.ProfanityException;
import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class AuthService {

    private final UserService userService;
    private ProfanityService profanityService;
    @Autowired
    public AuthService(UserService userService, ProfanityService profanityService) {
        this.userService = userService;
        this.profanityService = profanityService;
    }

    public Optional<User> findByCredentials(String email, String password) {
        return userService.findByCredentials(email, password);
    }

    public User register(RegisterRequest registerRequest) throws ProfanityException {
        String[] beforeEmailPart = registerRequest.getEmail().split("@");
        if(beforeEmailPart.length>2)
            throw new ProfanityException("Too many @ Symbols in email request.");
        if(profanityService.profanityLikely(beforeEmailPart[0])) throw  new ProfanityException();
        if(profanityService.profanityLikely(registerRequest.getFirstName())) throw  new ProfanityException();
        if(profanityService.profanityLikely(registerRequest.getLastName())) throw  new ProfanityException();
        User created = new User(
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                registerRequest.getFirstName(),
                registerRequest.getLastName(),
                Instant.now());
        return userService.save(created);
    }
}
