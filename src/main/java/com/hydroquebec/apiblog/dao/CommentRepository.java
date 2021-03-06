package com.hydroquebec.apiblog.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hydroquebec.apiblog.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

	public List<Comment> findByPostId(Integer postId);

}
