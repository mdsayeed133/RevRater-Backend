package com.revature.controllers;

import com.revature.dtos.LikesDTO;
import com.revature.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@CrossOrigin
public class LikeController {

    private LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/like")
    public ResponseEntity<String> likePost(@RequestBody LikesDTO lDTO){
        try {
            likeService.likePost(lDTO);
            return ResponseEntity.accepted().body("User #" + lDTO.getUserId() + " has liked post #" + lDTO.getPostId());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Like unsuccessful");
        }
    }

    @GetMapping("/{postId}/amount")
    public ResponseEntity<Integer> likesAmount(@PathVariable int postId){
        try {
            int likes = likeService.likesAmount(postId);
            return ResponseEntity.accepted().body(likes);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(0);
        }
    }
}
