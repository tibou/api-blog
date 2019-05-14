package com.hydroquebec.apiblog.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hydroquebec.apiblog.entity.Comment;
import com.hydroquebec.apiblog.entity.Post;
import com.hydroquebec.apiblog.service.CommentService;
import com.hydroquebec.apiblog.service.PostService;

@RestController
@RequestMapping("/post")
public class PostRestController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private PostService postService;

	@GetMapping("/{postId}/comments")
	public List<Comment> getCommentsForPost(@PathVariable int postId) {

		List<Comment> allComments = commentService.getAllCommentsForGivenPost(postId);

		return allComments;
	}

	@PostMapping("/{postId}/comments")
	public Comment addCommentToGivenPost(@PathVariable int postId, @RequestBody Comment comment) {

		// In case comment id is provided in the content, set it to 0 to force to add
		// new comment
		// instead of update
		comment.setId(0);

		Post post = postService.findById(postId);
		comment.setPost(post);
		post.addComment(comment);

		return commentService.save(comment);
	}

	@DeleteMapping("/{commentId}/comments")
	public String deleteComment(@PathVariable int commentId) {
		commentService.findById(commentId);

		commentService.deleteById(commentId);

		return "Comment with id : " + commentId + " deleted";
	}

	@PutMapping("/{commentId}/comments")
	public Comment updateComment(@PathVariable int commentId, @RequestBody Comment comment) {
		comment.setId(commentId);

		Comment old = commentService.findById(commentId);

		comment.setPost(old.getPost());
		comment.setCreatedAt(old.getCreatedAt());
		return commentService.save(comment);
	}

}
