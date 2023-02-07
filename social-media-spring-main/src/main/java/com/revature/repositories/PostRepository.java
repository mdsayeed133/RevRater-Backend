package com.revature.repositories;

import com.revature.models.*;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{


    Optional<List<Post>> findByAuthorAndPostType(User user, PostType postType);

    Optional<Post> findByRating(Rating rating);


}
