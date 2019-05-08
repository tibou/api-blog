package com.hydroquebec.apiblog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hydroquebec.apiblog.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
