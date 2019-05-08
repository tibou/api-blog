package com.hydroquebec.apiblog.service;

import java.util.List;

import com.hydroquebec.apiblog.entity.Post;

public interface PostService {

	public List<Post> findAll();

	public Post findById(long id);

	public Post save(Post post);

	public void deleteById(long id);

}
