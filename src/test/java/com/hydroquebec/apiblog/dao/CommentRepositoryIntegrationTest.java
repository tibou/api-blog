package com.hydroquebec.apiblog.dao;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.hydroquebec.apiblog.entity.Comment;
import com.hydroquebec.apiblog.entity.Post;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepositoryIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private CommentRepository commentRepository;

	@Test
	public void whenFindById_thenReturnPost() {

		// given
		Comment comment = new Comment("Ceci est le contenu du commentare", "auteur", LocalDateTime.now(),
				LocalDateTime.now());
		Integer id = entityManager.persistAndGetId(comment, Integer.class);
		entityManager.flush();

		// when
		Comment actual = commentRepository.findById(id).get();

		// then
		assertEquals(comment.getId(), actual.getId());
		assertEquals(comment.getMessage(), actual.getMessage());
		assertEquals(comment.getAuthor(), actual.getAuthor());
	}

	@Test
	public void whenDeleteById_thenDropCommentFromDatabase() {

		// given
		Comment comment = new Comment("Ceci est le contenu du commentare", "auteur", LocalDateTime.now(),
				LocalDateTime.now());
		Integer id = entityManager.persistAndGetId(comment, Integer.class);
		entityManager.flush();

		// when
		commentRepository.deleteById(id);
		Comment actual = commentRepository.findById(id).orElse(null);

		// then
		assertNull(actual);

	}

	@Test
	public void whenSave_thenSaveCommentIntoDatabase() {

		// given
		Comment comment = new Comment("Ceci est le contenu du commentare", "auteur", LocalDateTime.now(),
				LocalDateTime.now());
		Integer id = entityManager.persistAndGetId(comment, Integer.class);
		entityManager.flush();

		// when
		Comment actual = commentRepository.findById(id).orElse(null);

		// then
		assertEquals(comment.getId(), actual.getId());
		assertEquals(comment.getMessage(), actual.getMessage());
		assertEquals(comment.getAuthor(), actual.getAuthor());
	}

	@Test
	public void whenFindAll_thenReturnAllPost() {

		// given
		Comment comment1 = new Comment("Ceci est le contenu du premier commentare", "auteur1", LocalDateTime.now(),
				LocalDateTime.now());
		Comment comment2 = new Comment("Ceci est le contenu du deuxième commentare", "auteur2", LocalDateTime.now(),
				LocalDateTime.now());
		entityManager.persist(comment1);
		entityManager.persist(comment2);
		entityManager.flush();

		// when
		List<Comment> actuals = commentRepository.findAll();

		// then
		assertEquals(2, actuals.size());
		assertArrayEquals(new Comment[] { comment1, comment2 }, actuals.toArray(new Comment[actuals.size()]));
	}

	@Test
	public void whenFindByPostId_thenReturnComments() {

		// given
		Comment comment1 = new Comment("Ceci est le contenu du premier commentare", "auteur1", LocalDateTime.now(),
				LocalDateTime.now());
		Comment comment2 = new Comment("Ceci est le contenu du deuxième commentare", "auteur2", LocalDateTime.now(),
				LocalDateTime.now());
		Post post = new Post("Ceci est le titre du post", "Ceci est le contenu du post", "auteur", LocalDateTime.now(),
				LocalDateTime.now());
		comment1.setPost(post);
		comment2.setPost(post);
		entityManager.persist(comment1);
		entityManager.persist(comment2);
		Integer id = entityManager.persistAndGetId(post, Integer.class);
		entityManager.flush();

		// when
		List<Comment> actuals = commentRepository.findByPostId(id);

		// then
		assertEquals(2, actuals.size());
		assertArrayEquals(new Comment[] { comment1, comment2 }, actuals.toArray(new Comment[actuals.size()]));

	}

}
