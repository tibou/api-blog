package com.hydroquebec.apiblog.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hydroquebec.apiblog.entity.Comment;
import com.hydroquebec.apiblog.entity.Post;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

	public List<Comment> findByPostId(Integer postId);

	public Comment findByIdAndPostId(Integer commentId, Integer postId);
}
