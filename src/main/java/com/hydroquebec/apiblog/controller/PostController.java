package com.hydroquebec.apiblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hydroquebec.apiblog.entity.Post;
import com.hydroquebec.apiblog.service.PostService;

@Controller
@RequestMapping("/posts")
public class PostController {

	@Autowired
	private PostService postService;

	@GetMapping("/list")
	public String listPosts(Model model) {

		List<Post> posts = postService.findAll();

		model.addAttribute("posts", posts);

		return "posts/list-posts";
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model model) {

		Post post = new Post();

		model.addAttribute("post", post);

		return "posts/post-form";
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("postId") int postId, Model model) {

		Post post = postService.findById(postId);

		model.addAttribute("post", post);

		return "posts/post-form";
	}
	
	@PostMapping("/save")
	public String savePost(@ModelAttribute("post") Post post) {
		
		postService.save(post);
		
		return "redirect:/posts/list";
	}
	
	
	@DeleteMapping("/delete")
	public String deletePost(@RequestParam("postId") int postId) {
		
		postService.deleteById(postId);
		
		return "redirect:/posts/list";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
