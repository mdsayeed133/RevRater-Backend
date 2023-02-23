package com.revature.Service;

import com.revature.dtos.RatingDTO;
import com.revature.dtos.RatingPostRequest;
import com.revature.models.*;
import com.revature.repositories.PostRepository;
import com.revature.repositories.UserRepository;
import com.revature.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) //JUnit5
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;
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

    private Post fakePost;
    private Post fakePost1;
    private Post fakePost2;
    private RatingPostRequest ratingPostRequest;
    private Department fakeDepartment;
    private Tag fakeTag;
    private User fakeUser;
    private User fakeUser1;
    private Employee fakeEmployee;
    private Rating fakeRating;
    private RatingDTO rDTO;

    @BeforeEach
    public void setup() {
        fakeDepartment= new Department("N/A");
        fakeTag= new Tag("N/A");
        fakeUser = new User(0,"fake@email.com","fake","N/A","N/A",Instant.now());
        //fakeUser1 = new User(1,"fafke@email.com","fakfe","N/A","N/A",Instant.now());
        fakeEmployee= new Employee("N/A", "N/A", fakeUser,fakeDepartment, Instant.now());
        fakeRating= new Rating(fakeEmployee,0,fakeTag,fakeTag,fakeTag);
        rDTO = new RatingDTO(1,2,3,4,5);
        ratingPostRequest = new RatingPostRequest(fakeUser.getId(), "some text", 2, rDTO);
        fakePost = new Post(0,"any string", 2, new ArrayList<>(), fakeUser, PostType.Rating, Instant.now());
        fakePost1 = new Post(0,"any string", 2, new ArrayList<>(), fakeUser, PostType.Comment, Instant.now());
        fakePost2 = new Post(0,"any string", 2, null, fakeUser, PostType.Reply, Instant.now());
    }

    @Test
    public void testCreateRatingPost() {

        when(userService.getUserById(ratingPostRequest.getUserId())).thenReturn(Optional.of(fakeUser));
        when(ratingService.createRating(ratingPostRequest.getRatingDTO())).thenReturn(fakeRating);
        when(profanityService.filterProfanity(ratingPostRequest.getText())).thenReturn("any string");
        when(postRepository.save(any(Post.class))).thenReturn(fakePost);

        Post result = postService.createRatingPost(ratingPostRequest);

        assertThat(fakePost, equalTo(result));
    }

    @Test
    void testGetRatingsPostOfUser(){
        List<Post> d = Arrays.asList(fakePost);
        when(userService.getUserById(fakePost.getId())).thenReturn(Optional.of(fakeUser));
        when(postRepository.findByAuthorAndPostType(fakeUser, PostType.Rating)).thenReturn(Optional.of(d));
        List<Post> result = postService.getRatingPostsOfUser(fakeUser.getId());
        assertThat(result, equalTo(d));
    }

    @Test
    void testGetCommentsPostOfUser(){
        List<Post> d = Arrays.asList(fakePost1);
        when(userService.getUserById(fakePost1.getId())).thenReturn(Optional.of(fakeUser));
        when(postRepository.findByAuthorAndPostType(fakeUser, PostType.Comment)).thenReturn(Optional.of(d));
        List<Post> result = postService.getCommentPostsOfUser(fakeUser.getId());
        assertThat(result, equalTo(d));
    }

    @Test
    void testGetReplyPostOfUser(){
        List<Post> d = Arrays.asList(fakePost2);
        when(userService.getUserById(fakePost2.getId())).thenReturn(Optional.of(fakeUser));
        when(postRepository.findByAuthorAndPostType(fakeUser, PostType.Reply)).thenReturn(Optional.of(d));
        List<Post> result = postService.getReplyPostsOfUser(fakeUser.getId());
        assertThat(result, equalTo(d));
    }

    @Test
    void testGettingCommentOfPost(){
        List<Post> comments= Arrays.asList(fakePost2);
        fakePost.setComments(comments);
        when(postRepository.findById(fakePost.getId())).thenReturn(Optional.of(fakePost));

        List<Post> result= postService.getCommentsOfAPost(fakePost.getId());
        assertThat(result,equalTo(comments));

    }

    @Test
    void testGettingReplyOfComment(){
        List<Post> comments= Arrays.asList(fakePost2);
        fakePost.setComments(comments);
        when(postRepository.findById(fakePost.getId())).thenReturn(Optional.of(fakePost));

        List<Post> result= postService.getRepliesOfComment(fakePost.getId());
        assertThat(result,equalTo(comments));

    }

}
