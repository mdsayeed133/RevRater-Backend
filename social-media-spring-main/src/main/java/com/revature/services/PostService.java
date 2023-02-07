package com.revature.services;

import com.revature.dtos.CommentPostRequest;
import com.revature.dtos.RatingPostRequest;
import com.revature.exceptions.PostNotFound;
import com.revature.exceptions.UserNotFound;
import com.revature.models.*;
import com.revature.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService {

	private PostRepository postRepository;
	private UserService userService;
	private RatingService ratingService;
	private EmployeeService employeeService;
	private ProfanityService profanityService;
	private Department fakeDepartment= new Department("N/A");
	private Tag fakeTag= new Tag("N/A");
	private User fakeUser= new User("fake@email.com","fake","N/A","N/A");
	private Employee fakeEmployee= new Employee("N/A", "N/A", fakeUser,fakeDepartment,Instant.now());
	private Rating fakeRating= new Rating(fakeEmployee,0,fakeTag,fakeTag,fakeTag);
	@Autowired
	public PostService(PostRepository postRepository, UserService userService, RatingService ratingService, EmployeeService employeeService, ProfanityService profanityService) {
		this.postRepository = postRepository;
		this.userService = userService;
		this.ratingService = ratingService;
		this.employeeService = employeeService;
		this.profanityService = profanityService;
	}

	public Optional<Post> getPostById(int postId)
	{
		return postRepository.findById(postId);
	}



	public Post createRatingPost(RatingPostRequest ratingPostRequest){
		User user = userService.getUserById(ratingPostRequest.getUserId()).orElse(null);
		Rating rating = ratingService.createRating(ratingPostRequest.getRatingDTO());
		String clearText= profanityService.filterProfanity(ratingPostRequest.getText());
		Post newRatingPost = new Post(clearText	, ratingPostRequest.getImageId(), null, user, PostType.Rating, rating, Instant.now());
		return postRepository.save(newRatingPost);
	}


	public Post createCommentPost(CommentPostRequest commentPostRequest) throws PostNotFound {
		User user = userService.getUserById(commentPostRequest.getUserId()).orElse(null);
		String clearText= profanityService.filterProfanity(commentPostRequest.getText());
		Post newCommentPost = new Post(clearText, commentPostRequest.getImageId(), null, user, PostType.Comment, Instant.now());
		Post ratingPost = postRepository.findById(commentPostRequest.getPostId()).orElse(null);
		if(ratingPost==null) throw new PostNotFound();
		ArrayList<Post> currentComments = new ArrayList<>(ratingPost.getComments());
		currentComments.add(newCommentPost);
		ratingPost.setComments(currentComments);
		postRepository.save(ratingPost);
		return newCommentPost;
	}

	public Post createReplyPost(CommentPostRequest replyPostRequest)throws PostNotFound {
		User user = userService.getUserById(replyPostRequest.getUserId()).orElse(null);
		String clearText= profanityService.filterProfanity(replyPostRequest.getText());
		Post newReplyPost = new Post(clearText, replyPostRequest.getImageId(), null, user, PostType.Reply, Instant.now());
		Post commentPost = postRepository.findById(replyPostRequest.getPostId()).orElse(null);
		if(commentPost==null) throw new PostNotFound();
		ArrayList<Post> currentReply = new ArrayList<>(commentPost.getComments());
		currentReply.add(newReplyPost);
		commentPost.setComments(currentReply);
		postRepository.save(commentPost);
		return newReplyPost;
	}

	public List<Post> getRatingPostsOfUser(int id) throws UserNotFound {
		User user = userService.getUserById(id).orElse(null);
		if(user==null) throw new UserNotFound();
		List<Post> posts = postRepository.findByAuthorAndPostType(user,PostType.Rating).orElse(null);
		if(posts==null)return new ArrayList<>(List.of(new Post[]{new Post(0, user.getFirstName() + " has not made any post", 0, null, fakeUser, PostType.Rating, fakeRating, Instant.now())}));
		posts.sort(Comparator.comparing(Post::getCreatedDate).reversed());
		return posts;
	}
	public List<Post> getCommentPostsOfUser(int id) throws UserNotFound {
		User user = userService.getUserById(id).orElse(null);
		if(user==null) throw new UserNotFound();
		List<Post> posts = postRepository.findByAuthorAndPostType(user,PostType.Comment).orElse(null);
		if(posts==null)return new ArrayList<>(List.of(new Post[]{new Post(0, user.getFirstName() + " has not made any comments", 0, null, fakeUser, PostType.Comment, null, Instant.now())}));
		posts.sort(Comparator.comparing(Post::getCreatedDate).reversed());
		return posts;
	}
	public List<Post> getReplyPostsOfUser(int id) throws UserNotFound {
		User user = userService.getUserById(id).orElse(null);
		if(user==null) throw new UserNotFound();
		List<Post> posts = postRepository.findByAuthorAndPostType(user,PostType.Reply).orElse(null);
		if(posts==null)return new ArrayList<>(List.of(new Post[]{new Post(0, user.getFirstName() + " has not made any reply", 0, null, fakeUser, PostType.Reply, null, Instant.now())}));
		posts.sort(Comparator.comparing(Post::getCreatedDate).reversed());
		return posts;
	}

	public List<Post> getPostsAboutEmployee(int employeeId){
		Employee emp = employeeService.getEmployeeById(employeeId);
		List<Post> posts = new ArrayList<>();
		List<Rating> ratings = ratingService.findByEmployee(emp).orElse(null);
		if(ratings==null) return new ArrayList<>(List.of(new Post[]{new Post(0, emp.getFirstName() + " has no post about them", 0, null, fakeUser, PostType.Rating, fakeRating, Instant.now())}));
		for (Rating rating : ratings) {
			Post post = postRepository.findByRating(rating).orElse(null);
			posts.add(post);
		}
		posts.sort(Comparator.comparing(Post::getCreatedDate).reversed());
		return posts;
	}
	//this is the method to getPostByUserFollower
	public List<Post> getUserFeed(int userId) {
		List<Employee> employees= userService.getAllFollowing(userId);
		if(employees==null)return new ArrayList<>(List.of(new Post[]{new Post(0, "You have to follow a employee to have a feed", 0, null, fakeUser, PostType.Rating, fakeRating, Instant.now())}));
		List<Post> posts = new ArrayList<>();
		for (Employee employee : employees) {
			List<Rating> ratings = ratingService.findByEmployee(employee).orElse(null);
			for (Rating rating : ratings) {
				Post post = postRepository.findByRating(rating).orElse(null);
				posts.add(post);
			}
		}
		posts.sort(Comparator.comparing(Post::getCreatedDate).reversed());
		return posts;
	}


	public List<Post> getCommentsOfAPost(int postId) throws PostNotFound {
		Post post = postRepository.findById(postId).orElse(null);
		if (post==null) throw new PostNotFound();
		List<Post> comments= new ArrayList<>(post.getComments());
		comments.sort(Comparator.comparing(Post::getCreatedDate).reversed());
		return comments;
	}

	public List<Post> getRepliesOfComment(int commentId) throws PostNotFound {
		Post comment = postRepository.findById(commentId).orElse(null);
		if(comment==null) throw new PostNotFound();
		List<Post> replies = new ArrayList<>(comment.getComments());
		replies.sort(Comparator.comparing(Post::getCreatedDate).reversed());
		return replies;
	}

	public Boolean editRatingPost(RatingPostRequest ratingsPostRequest, int postId) throws PostNotFound{
		try {
			Post post = postRepository.findById(postId).orElse(null);
			if(post==null)throw new PostNotFound();
			Rating rating = post.getRating();
			String clearText= profanityService.filterProfanity(ratingsPostRequest.getText());
			post.setMessage(clearText);
			post.setImageId(ratingsPostRequest.getImageId());
			ratingService.editRating(ratingsPostRequest.getRatingDTO(), rating.getId());
			postRepository.save(post);
			return true;
		}catch (Exception e){
			return  false;
		}
	}

	public Boolean editCommentPost(CommentPostRequest commentPostRequest, int commentId) throws PostNotFound {
		try {
			Post post = postRepository.findById(commentId).orElse(null);
			if(post==null)throw new PostNotFound();
			String clearText= profanityService.filterProfanity(commentPostRequest.getText());
			post.setMessage(clearText);
			post.setImageId(commentPostRequest.getImageId());
			postRepository.save(post);
			return true;
		} catch (Exception e){
			return false;
		}
	}

	public Boolean editRelyPost(CommentPostRequest commentPostRequest, int replyId) throws PostNotFound {
		try {
			Post post = postRepository.findById(replyId).orElse(null);
			if(post==null)throw new PostNotFound();
			String clearText= profanityService.filterProfanity(commentPostRequest.getText());
			post.setMessage(clearText);
			post.setImageId(commentPostRequest.getImageId());
			postRepository.save(post);
			return true;
		} catch (Exception e){
			return false;
		}
	}
	public void deletePost(int postId) throws PostNotFound{
		Post post = postRepository.findById(postId).orElse(null);
		if(post==null) throw new PostNotFound();
		if (post.getPostType().equals(PostType.Rating)) {
			Rating rating = post.getRating();
			ratingService.delete(rating);
		}
		postRepository.delete(post);
	}

}
