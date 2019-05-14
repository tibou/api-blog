package com.hydroquebec.apiblog.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.hydroquebec.apiblog.dao.CommentRepository;
import com.hydroquebec.apiblog.entity.Comment;

@RunWith(SpringRunner.class)
public class CommentServiceImplTest {

	@TestConfiguration
	static class CommentServiceImplTestContextConfiguration {

		@Bean
		public CommentService commentService() {
			return new CommentServiceImpl();
		}
	}

	@Autowired
	private CommentService commentService;

	@MockBean
	private CommentRepository commentRepository;

	@Test
	public void whenFindAll_thenReturnAllComment() {

		// given
		Comment comment1 = new Comment("Ceci est le contenu du premier commentare", "auteur1", LocalDateTime.now(),
				LocalDateTime.now());
		Comment comment2 = new Comment("Ceci est le contenu du deuxième commentare", "auteur2", LocalDateTime.now(),
				LocalDateTime.now());
		List<Comment> comments = new ArrayList<>();
		comments.add(comment1);
		comments.add(comment2);

		// when
		Mockito.when(commentRepository.findAll()).thenReturn(comments);
		List<Comment> actuals = commentService.findAll();

		// then
		assertEquals(2, actuals.size());
		assertArrayEquals(new Comment[] { comment1, comment2 }, actuals.toArray(new Comment[actuals.size()]));
	}

	@Test
	public void whenFindById_thenReturnComment() {

		// given
		Comment comment = new Comment("Ceci est le contenu du commentare", "auteur", LocalDateTime.now(),
				LocalDateTime.now());

		// when
		Mockito.when(commentRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(comment));
		Comment actual = commentService.findById(1);

		// then
		assertEquals(comment.getMessage(), actual.getMessage());
		assertEquals(comment.getAuthor(), actual.getAuthor());
	}

	@Test
	public void whenSave_thenSaveAndReturnComment() {

		// given
		Comment comment = new Comment("Ceci est le contenu du commentare", "auteur", LocalDateTime.now(),
				LocalDateTime.now());

		// when
		Mockito.when(commentRepository.save(Mockito.any(Comment.class))).thenReturn(comment);
		Comment actual = commentService.save(comment);

		// then
		assertEquals(comment.getMessage(), actual.getMessage());
		assertEquals(comment.getAuthor(), actual.getAuthor());
	}

	@Test(expected = Exception.class)
	public void whenDeleteById_thenThrowException() {

		// given

		// when
		Mockito.doThrow(Exception.class).when(commentRepository).deleteById(Mockito.anyInt());
		commentService.deleteById(1);

		// then
		Mockito.verify(commentRepository, Mockito.times(1)).deleteById(Mockito.anyInt());
	}

}
