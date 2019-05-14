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

import com.hydroquebec.apiblog.entity.Post;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostRepositoryIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private PostRepository postRepository;

	@Test
	public void whenFindById_thenReturnPost() {

		// given
		Post post = new Post("Ceci est le titre du post", "Ceci est le contenu du post", "auteur", LocalDateTime.now(),
				LocalDateTime.now());
		Integer id = entityManager.persistAndGetId(post, Integer.class);
		entityManager.flush();

		// when
		Post actual = postRepository.findById(id).get();

		// then
		assertEquals(post.getId(), actual.getId());
		assertEquals(post.getTitle(), actual.getTitle());
		assertEquals(post.getBody(), actual.getBody());
	}

	@Test
	public void whenDeleteById_thenDropPostFromDatabase() {

		// given
		Post post = new Post("Ceci est le titre du post", "Ceci est le contenu du post", "auteur", LocalDateTime.now(),
				LocalDateTime.now());
		Integer id = entityManager.persistAndGetId(post, Integer.class);
		entityManager.flush();

		// when
		postRepository.deleteById(id);
		Post actual = postRepository.findById(id).orElse(null);

		// then
		assertNull(actual);

	}

	@Test
	public void whenSave_thenSavePostIntoDatabase() {

		// given
		Post post = new Post("Ceci est le titre du post", "Ceci est le contenu du post", "auteur", LocalDateTime.now(),
				LocalDateTime.now());
		Integer id = entityManager.persistAndGetId(post, Integer.class);
		entityManager.flush();

		// when
		Post actual = postRepository.findById(id).orElse(null);

		// then
		assertEquals(post.getId(), actual.getId());
		assertEquals(post.getTitle(), actual.getTitle());
		assertEquals(post.getBody(), actual.getBody());
	}

	@Test
	public void whenFindAll_thenReturnAllPost() {

		// given
		Post post1 = new Post("Ceci est le titre du premier post", "Ceci est le contenu du premier post", "auteur1", LocalDateTime.now(),
				LocalDateTime.now());
		Post post2 = new Post("Ceci est le titre du deuxième post", "Ceci est le contenu du deuxième post", "auteur2", LocalDateTime.now(),
				LocalDateTime.now());
		entityManager.persist(post1);
		entityManager.persist(post2);
		entityManager.flush();

		// when
		List<Post> actuals = postRepository.findAll();

		// then
		assertEquals(2, actuals.size());
		assertArrayEquals(new Post[] {post1, post2}, actuals.toArray(new Post[actuals.size()]));
	}

}
