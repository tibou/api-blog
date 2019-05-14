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

import com.hydroquebec.apiblog.dao.PostRepository;
import com.hydroquebec.apiblog.entity.Post;

@RunWith(SpringRunner.class)
public class PostServiceImplTest {

	@TestConfiguration
	static class PostServiceImplTestContextConfiguration {

		@Bean
		public PostService postService() {
			return new PostServiceImpl();
		}
	}

	@Autowired
	private PostService postService;

	@MockBean
	private PostRepository postRepository;

	@Test
	public void whenFindAll_thenReturnAllPost() {

		// given
		Post post1 = new Post("Ceci est le titre du premier post", "Ceci est le contenu du premier post", "auteur1",
				LocalDateTime.now(), LocalDateTime.now());
		Post post2 = new Post("Ceci est le titre du deuxième post", "Ceci est le contenu du deuxième post", "auteur2",
				LocalDateTime.now(), LocalDateTime.now());
		List<Post> posts = new ArrayList<Post>();
		posts.add(post1);
		posts.add(post2);

		// when
		Mockito.when(postRepository.findAll()).thenReturn(posts);
		List<Post> actuals = postService.findAll();

		// then
		assertEquals(2, actuals.size());
		assertArrayEquals(new Post[] { post1, post2 }, actuals.toArray(new Post[actuals.size()]));
	}

	@Test
	public void whenFindById_thenReturnPost() {

		// given
		Post post = new Post("Ceci est le titre du post", "Ceci est le contenu du post", "auteur", LocalDateTime.now(),
				LocalDateTime.now());

		// when
		Mockito.when(postRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(post));
		Post actual = postService.findById(1);

		// then
		assertEquals(post.getTitle(), actual.getTitle());
		assertEquals(post.getBody(), actual.getBody());
	}

	@Test
	public void whenSave_thenSaveAndReturnPost() {

		// given
		Post post = new Post("Ceci est le titre du post", "Ceci est le contenu du post", "auteur", LocalDateTime.now(),
				LocalDateTime.now());

		// when
		Mockito.when(postRepository.save(Mockito.any(Post.class))).thenReturn(post);
		Post actual = postService.save(post);

		// then
		assertEquals(post.getTitle(), actual.getTitle());
		assertEquals(post.getBody(), actual.getBody());
	}

	@Test(expected = Exception.class)
	public void whenDeleteById_thenThrowException() {

		// given

		// when
		Mockito.doThrow(Exception.class).when(postRepository).deleteById(Mockito.anyInt());
		postService.deleteById(1);

		// then
		Mockito.verify(postRepository, Mockito.times(1)).deleteById(Mockito.anyInt());
	}

}
