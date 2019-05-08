package com.hydroquebec.apiblog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hydroquebec.apiblog.dao.PostRepository;
import com.hydroquebec.apiblog.entity.Post;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Override
	@Transactional
	public List<Post> findAll() {
		return postRepository.findAll();
	}

	@Override
	@Transactional
	public Post findById(int id) {
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
	@Transactional
	public Post save(Post post) {
		return postRepository.save(post);

	}

	@Override
	@Transactional
	public void deleteById(int id) {
		postRepository.deleteById(id);
	}

}
