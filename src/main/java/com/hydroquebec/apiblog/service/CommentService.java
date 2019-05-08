package com.hydroquebec.apiblog.service;

import java.util.List;

import com.hydroquebec.apiblog.entity.Comment;

public interface CommentService {

	public List<Comment> findAll();

	public Comment findById(long id);

	public Comment save(Comment comment);

	public void deleteById(long id);

}
