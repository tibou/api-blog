package com.hydroquebec.apiblog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hydroquebec.apiblog.dao.CommentRepository;
import com.hydroquebec.apiblog.entity.Comment;
import com.hydroquebec.apiblog.exception.CommentNotFoundException;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Override
	@Transactional
	public List<Comment> findAll() {
		return commentRepository.findAll();
	}

	@Override
	@Transactional
	public Comment findById(int id) {
		Optional<Comment> result = commentRepository.findById(id);

		Comment comment = null;

		if (result.isPresent()) {
			comment = result.get();
		} else {
			throw new CommentNotFoundException("comment with id " + id + " not found");
		}
		return comment;
	}

	@Override
	@Transactional
	public Comment save(Comment comment) {
		return commentRepository.save(comment);
	}

	@Override
	@Transactional
	public void deleteById(int id) {
		commentRepository.deleteById(id);
	}

	@Override
	@Transactional
	public List<Comment> getAllCommentsForGivenPost(int postId) {
		return commentRepository.findByPostId(postId);
	}

}
