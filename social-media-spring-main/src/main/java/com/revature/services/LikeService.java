package com.revature.services;

import com.revature.dtos.LikesDTO;
import com.revature.exceptions.PostNotFound;
import com.revature.exceptions.UserNotFound;
import com.revature.models.Like;
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.repositories.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LikeService {

    private LikeRepository likesRepo;
    private PostService postService;
    private UserService userService;


    @Autowired
    public LikeService(LikeRepository likesRepo, PostService postService, UserService userService) {
        this.likesRepo = likesRepo;
        this.postService = postService;
        this.userService = userService;
    }

    public Like likePost(LikesDTO lDTO) throws PostNotFound, UserNotFound
    {
        Post post = postService.getPostById(lDTO.getPostId()).orElse(null);
        User user = userService.getUserById(lDTO.getUserId()).orElse(null);
        if (post==null) throw new PostNotFound();
        if(user==null) throw new UserNotFound();
        Like like = new Like(post,user);
        return likesRepo.save(like);
    }

    public int likesAmount(int postId) throws PostNotFound
    {
        Post searchPost = postService.getPostById(postId).orElse(null);
        if(searchPost==null) throw new PostNotFound();
        return likesRepo.countByPost(searchPost).orElse(0);
    }


}
