package com.hydroquebec.apiblog.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hydroquebec.apiblog.entity.Post;
import com.hydroquebec.apiblog.service.PostService;

@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
public class PostControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PostService postService;

	@Test
	public void testListPosts() throws Exception {

		// given
		Post post1 = new Post("Ceci est le titre du premier post", "Ceci est le contenu du premier post", "auteur1",
				LocalDateTime.now(), LocalDateTime.now());
		Post post2 = new Post("Ceci est le titre du deuxième post", "Ceci est le contenu du deuxième post", "auteur2",
				LocalDateTime.now(), LocalDateTime.now());
		List<Post> posts = new ArrayList<>();
		posts.add(post1);
		posts.add(post2);

		// when
		Mockito.when(postService.findAll()).thenReturn(posts);

		// then
		mockMvc.perform(get("/posts/list")).andExpect(status().isOk()).andExpect(model().attributeExists("posts"))
				.andExpect(view().name("posts/list-posts"));

	}

	@Test
	public void testShowDetail() throws Exception {

		// given
		Post post = new Post("Ceci est le titre du post", "Ceci est le contenu du post", "auteur1", LocalDateTime.now(),
				LocalDateTime.now());

		// when
		Mockito.when(postService.findById(Mockito.anyInt())).thenReturn(post);

		// then
		mockMvc.perform(get("/posts/detail?postId=1")).andExpect(status().isOk())
				.andExpect(model().attributeExists("post")).andExpect(view().name("posts/post-detail"));

	}

	@Test
	public void testShowFormForAdd() throws Exception {

		// then
		mockMvc.perform(get("/posts/showFormForAdd")).andExpect(status().isOk())
				.andExpect(model().attributeExists("post")).andExpect(view().name("posts/post-form"));

	}

	@Test
	public void testShowFormForUpdate() throws Exception {
		// given
		Post post = new Post("Ceci est le titre du post", "Ceci est le contenu du post", "auteur", LocalDateTime.now(),
				LocalDateTime.now());

		// when
		Mockito.when(postService.findById(Mockito.anyInt())).thenReturn(post);

		// then
		mockMvc.perform(get("/posts/showFormForUpdate?postId=1")).andExpect(status().isOk())
				.andExpect(model().attributeExists("post")).andExpect(view().name("posts/post-form"));

	}

	@Test
	public void testSavePost() throws Exception {

		// then
		mockMvc.perform(post("/posts/save")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/posts/list"));

	}

	@Test(expected = Exception.class)
	public void testDeletePost() throws Exception {

		// when
		Mockito.doThrow(Exception.class).when(postService).deleteById(Mockito.anyInt());

		// then
		mockMvc.perform(get("/posts/delete?postId=1")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/posts/list"));

	}

}
