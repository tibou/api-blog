package com.hydroquebec.apiblog.service;

import java.util.List;

import com.hydroquebec.apiblog.entity.Comment;

public interface CommentService {

	public List<Comment> findAll();

	public Comment findById(int id);

	public Comment save(Comment comment);

	public void deleteById(int id);

	public List<Comment> getAllCommentsForGivenPost(int postId);

	public Comment getCommentForGivenPost(int postId, int commentId);

}
