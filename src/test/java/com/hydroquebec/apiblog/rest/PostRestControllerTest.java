package com.hydroquebec.apiblog.rest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hydroquebec.apiblog.entity.Comment;
import com.hydroquebec.apiblog.entity.Post;
import com.hydroquebec.apiblog.exception.CommentNotFoundException;
import com.hydroquebec.apiblog.service.CommentService;
import com.hydroquebec.apiblog.service.PostService;

@RunWith(SpringRunner.class)
@WebMvcTest(PostRestController.class)
public class PostRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PostService postService;

	@MockBean
	private CommentService commentService;

	@Test
	public void whenGetCommentsForPost_thenReturnJsonArray() throws Exception {

		// given
		Comment comment1 = new Comment("Ceci est le contenu du premier commentare", "auteur1", LocalDateTime.now(),
				LocalDateTime.now());
		Comment comment2 = new Comment("Ceci est le contenu du deuxième commentare", "auteur2", LocalDateTime.now(),
				LocalDateTime.now());
		List<Comment> comments = new ArrayList<>();
		comments.add(comment1);
		comments.add(comment2);

		// when
		Mockito.when(commentService.getAllCommentsForGivenPost(Mockito.anyInt())).thenReturn(comments);

		// then
		mockMvc.perform(get("/post/1/comments").contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].message", is(comment1.getMessage())))
				.andExpect(jsonPath("$[0].author", is(comment1.getAuthor())))
				.andExpect(jsonPath("$[1].message", is(comment2.getMessage())))
				.andExpect(jsonPath("$[1].author", is(comment2.getAuthor())));
	}

	@Test
	public void whenGetCommentsForPost_thenThrowCommentNotFoundException() throws Exception {

		// when
		Mockito.doThrow(CommentNotFoundException.class).when(commentService)
				.getAllCommentsForGivenPost(Mockito.anyInt());

		// then
		mockMvc.perform(get("/post/2/comments").contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound());
	}

	@Test
	public void whenAddCommentToGivenPost_thenAddTheCommentAndReturnIt() throws Exception {

		// given
		Post post = new Post("Ceci est le titre du post", "Ceci est le contenu du post", "auteur", LocalDateTime.now(),
				LocalDateTime.now());
		Comment comment = new Comment("Ceci est le contenu du commentare", "auteur", LocalDateTime.now(),
				LocalDateTime.now());
		comment.setCreatedAt(null);
		comment.setUpdatedAt(null);

		// when
		Mockito.when(postService.findById(Mockito.anyInt())).thenReturn(post);
		Mockito.when(commentService.save(Mockito.any(Comment.class))).thenReturn(comment);

		// then
		mockMvc.perform(
				post("/post/1/comments").contentType(MediaType.APPLICATION_JSON_UTF8).content(asJsonString(comment)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.author", is(comment.getAuthor())))
				.andExpect(jsonPath("$.message", is(comment.getMessage())));
	}

	@Test(expected = Exception.class)
	public void whenDeleteComment_thenDeleteTheComment() throws Exception {

		// given
		Comment comment = new Comment("Ceci est le contenu du commentare", "auteur", LocalDateTime.now(),
				LocalDateTime.now());

		// when
		Mockito.when(commentService.findById(Mockito.anyInt())).thenReturn(comment);
		Mockito.doThrow(Exception.class).when(commentService).deleteById(Mockito.anyInt());

		// then
		mockMvc.perform(delete("/post/1/comments").contentType(MediaType.APPLICATION_JSON_UTF8));
	}

	@Test
	public void whenUpdateComment_thenUpdateTheCommentAndReturnIt() throws Exception {

		// given
		Comment comment = new Comment("Ceci est le contenu du commentare", "auteur", LocalDateTime.now(),
				LocalDateTime.now());
		comment.setCreatedAt(null);
		comment.setUpdatedAt(null);

		// when
		Mockito.when(commentService.findById(Mockito.anyInt())).thenReturn(comment);
		Mockito.when(commentService.save(Mockito.any(Comment.class))).thenReturn(comment);

		// then
		mockMvc.perform(
				put("/post/1/comments").contentType(MediaType.APPLICATION_JSON_UTF8).content(asJsonString(comment)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.author", is(comment.getAuthor())))
				.andExpect(jsonPath("$.message", is(comment.getMessage())));
	}

	// Helper method to convert an object to json string
	private static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
