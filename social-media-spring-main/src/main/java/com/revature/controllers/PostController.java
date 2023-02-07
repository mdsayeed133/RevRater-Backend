package com.revature.controllers;

import com.revature.dtos.*;
import com.revature.exceptions.PostNotFound;
import com.revature.models.*;
import com.revature.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/posts")
@CrossOrigin
public class PostController {

    private final PostService postService;


    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/rating")
    public ResponseEntity<PostResponse> createRatingPost(@RequestBody RatingPostRequest ratingPostRequest) {
        try {
            Post post = postService.createRatingPost(ratingPostRequest);
            PostResponse response= createPostResponse(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/comment")
    public ResponseEntity<PostResponse> createCommentPost(@RequestBody CommentPostRequest commentPostRequest) {
        try {
            Post post = postService.createCommentPost(commentPostRequest);
            PostResponse response= createPostResponse(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/reply")
    public ResponseEntity<PostResponse> createReplyPost(@RequestBody CommentPostRequest replyPostRequest) {
        try {
            Post post = postService.createReplyPost(replyPostRequest);
            PostResponse response= createPostResponse(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (PostNotFound pe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}/user/ratings")
    public ResponseEntity<List<PostResponse>> getRatingPostsOfUser(@PathVariable int id) {
        try {
            List<Post> posts = postService.getRatingPostsOfUser(id);
            List<PostResponse> responses = new ArrayList<>();
            for(Post post: posts){
                PostResponse response= createPostResponse(post);
                responses.add(response);
            }
            return ResponseEntity.ok(responses);
        } catch (PostNotFound pe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}/user/comments")
    public ResponseEntity<List<PostResponse>> getCommentPostsOfUser(@PathVariable int id) {
        try {
            List<Post> posts = postService.getCommentPostsOfUser(id);
            List<PostResponse> responses = new ArrayList<>();
            for(Post post: posts){
                PostResponse response= createPostResponse(post);
                responses.add(response);
            }
            return ResponseEntity.ok(responses);
        } catch (PostNotFound pe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}/user/replies")
    public ResponseEntity<List<PostResponse>> getReplyPostsOfUser(@PathVariable int id) {
        try {
            List<Post> posts = postService.getReplyPostsOfUser(id);
            List<PostResponse> responses = new ArrayList<>();
            for(Post post: posts){
                PostResponse response= createPostResponse(post);
                responses.add(response);
            }
            return ResponseEntity.ok(responses);
        } catch (PostNotFound pe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}/emp/posts")
    public ResponseEntity<List<PostResponse>> getPostsAboutEmployee(@PathVariable int id){
        try{
            List<Post> posts = postService.getPostsAboutEmployee(id);
            List<PostResponse> responses = new ArrayList<>();
            for(Post post: posts){
                PostResponse response= createPostResponse(post);
                responses.add(response);
            }
            return ResponseEntity.ok(responses);
        } catch (PostNotFound pe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}/user/feed")
    public ResponseEntity<List<PostResponse>> getUserFeed(@PathVariable int id){
        try{
            List<Post> posts= postService.getUserFeed(id);
            List<PostResponse> responses = new ArrayList<>();
            for(Post post: posts){
                PostResponse response= createPostResponse(post);
                responses.add(response);
            }
            return ResponseEntity.ok(responses);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/{id}/post/comments")
    public ResponseEntity<List<PostResponse>> getCommentsOfAPost(@PathVariable int id){
        try{
            List<Post> comments= postService.getCommentsOfAPost(id);
            List<PostResponse> responses = new ArrayList<>();
            for(Post post: comments){
                PostResponse response= createPostResponse(post);
                responses.add(response);
            }
            return ResponseEntity.ok(responses);
        } catch (PostNotFound pe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/{id}/comment/replies")
    public ResponseEntity<List<PostResponse>> getRepliesOfComment(@PathVariable int id){
        try{
            List<Post> replies= postService.getRepliesOfComment(id);
            List<PostResponse> responses = new ArrayList<>();
            for(Post post: replies){
                PostResponse response= createPostResponse(post);
                responses.add(response);
            }
            return ResponseEntity.ok(responses);
        } catch (PostNotFound pe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping("/{id}/post/edit")
    public ResponseEntity<Object> editRatingPost(@RequestBody RatingPostRequest ratingPostRequest, @PathVariable int id){
        try {//TODO: Incorporate session at the controller level to prevent users from modifying other people's posts.
            boolean result = postService.editRatingPost(ratingPostRequest, id);
            if(result) return ResponseEntity.ok().build();
            return ResponseEntity.notFound().build();
        } catch (PostNotFound pe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/comment/edit")
    public ResponseEntity<Object> editCommentPost(@RequestBody CommentPostRequest commentPostRequest, @PathVariable int id){
        try {//TODO: Incorporate session at the controller level to prevent users from modifying other people's posts.
            boolean result = postService.editCommentPost(commentPostRequest, id);
            if(result) return ResponseEntity.ok().build();
            return ResponseEntity.badRequest().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/reply/edit")
    public ResponseEntity<Object> editReplyPost(@RequestBody CommentPostRequest commentPostRequest, @PathVariable int id){
        try {//TODO: Incorporate session at the controller level to prevent users from modifying other people's posts.
            boolean result = postService.editRelyPost(commentPostRequest, id);
            if(result) return ResponseEntity.ok().build();
            return ResponseEntity.badRequest().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/post/delete")
    public ResponseEntity<Object> deletePost(@PathVariable int id){
        try{
            postService.deletePost(id);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    private PostResponse createPostResponse(Post post) {
        User user = post.getAuthor();
        UserResponse userResponse = new UserResponse(user.getId(), user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getCreatedDate());
        RatingResponse ratingResponse = null;
        if (post.getPostType() == PostType.Rating) {
            Rating rating = post.getRating();
            Employee employee = rating.getEmployee();
            EmployeeResponse employeeResponse = new EmployeeResponse(employee.getId(), employee.getFirstName(), employee.getLastName(), userResponse, employee.getDepartment(), employee.getCreatedDate());
            ratingResponse = new RatingResponse(rating.getId(), employeeResponse, rating.getId(), rating.getTag1(), rating.getTag2(), rating.getTag3());
        }
        return new PostResponse(post.getId(), post.getMessage(), post.getImageId(), userResponse, post.getPostType(), ratingResponse, post.getCreatedDate());
    }
}



