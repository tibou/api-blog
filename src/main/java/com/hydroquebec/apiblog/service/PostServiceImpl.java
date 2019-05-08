package com.hydroquebec.apiblog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hydroquebec.apiblog.dao.PostRepository;
import com.hydroquebec.apiblog.entity.Post;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Override
	public List<Post> findAll() {
		return postRepository.findAll();
	}

	@Override
	public Post findById(long id) {
		Optional<Post> result = postRepository.findById(id);

		Post post = null;

		if (result.isPresent()) {
			post = result.get();
		} else {
			throw new RuntimeException("Did not find post id - " + id);
		}
		return post;
	}

	@Override
	public Post save(Post post) {
		return postRepository.save(post);

	}

	@Override
	public void deleteById(long id) {
		postRepository.deleteById(id);
	}

}
