package com.revature.Service;

import com.revature.dtos.RatingPostRequest;
import com.revature.models.*;
import com.revature.repositories.PostRepository;
import com.revature.services.*;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private UserService userService;
    @Mock
    private RatingService ratingService;
    @Mock
    private EmployeeService employeeService;
    @Mock
    private ProfanityService profanityService;

    @InjectMocks
    private PostService postService;

    private RatingPostRequest ratingPostRequest;
    private Department fakeDepartment;
    private Tag fakeTag;
    private User fakeUser;
    private Employee fakeEmployee;
    private Rating fakeRating;

    @BeforeEach
    public void setup() {
        fakeDepartment= new Department("N/A");
        fakeTag= new Tag("N/A");
        fakeUser = new User("fake@email.com","fake","N/A","N/A");
        fakeEmployee= new Employee("N/A", "N/A", fakeUser,fakeDepartment, Instant.now());
        fakeRating= new Rating(fakeEmployee,0,fakeTag,fakeTag,fakeTag);

    }

    @Test
    public void testCreateRatingPost() {

    }
}
