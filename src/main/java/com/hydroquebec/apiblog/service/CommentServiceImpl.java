package com.hydroquebec.apiblog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hydroquebec.apiblog.dao.CommentRepository;
import com.hydroquebec.apiblog.entity.Comment;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Override
	public List<Comment> findAll() {
		return commentRepository.findAll();
	}

	@Override
	public Comment findById(long id) {
		Optional<Comment> result = commentRepository.findById(id);

		Comment comment = null;

		if (result.isPresent()) {
			comment = result.get();
		} else {
			throw new RuntimeException("Did not find comment id - " + id);
		}
		return comment;
	}

	@Override
	public Comment save(Comment comment) {
		return commentRepository.save(comment);
	}

	@Override
	public void deleteById(long id) {
		commentRepository.deleteById(id);
	}

}
