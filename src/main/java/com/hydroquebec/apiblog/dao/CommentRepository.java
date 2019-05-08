package com.hydroquebec.apiblog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hydroquebec.apiblog.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
