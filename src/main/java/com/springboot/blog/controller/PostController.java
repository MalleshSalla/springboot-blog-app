package com.springboot.blog.controller;

import java.util.List;

import org.aspectj.apache.bcel.classfile.Module.Require;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	private PostService postService;
	
	/*
	 * Here we are injecting PostService interface not PostServiceImpl class because
	 * to provide loose coupling. We can inject PostServiceImpl class directly but
	 * it is tightly coupled Here only one constructor is there so need
	 * of @Autowired
	 */
	public PostController(PostService postService)
	{
		this.postService=postService;
	}
	
	/*@PostMapping method stores data as java object and return response to the client as Dto
	 *@PreAuthorize is a Spring Security annotation that is used to secure methods by specifying access controls on them.
	 * It can be used to express a method-level security constraint that is checked before the method is invoked.
	 *  The annotation is placed before the method or class that needs to be secured, 
	 *  and it takes an expression that is evaluated at runtime to determine if the user has the required permission to access the method. 
	 *  For example, @PreAuthorize("hasRole('ROLE_ADMIN')") will only allow users with the "ROLE_ADMIN" role to access the method.
     */
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto)
	{
		return new ResponseEntity<>(postService.createPost(postDto),HttpStatus.CREATED);
	}
	
	@GetMapping
	public PostResponse getAllPosts(
	@RequestParam(value="pageNo",defaultValue =AppConstants.DEFAULT_PAGE_NUMBER,required = false)int pageNo,   //Paging
	@RequestParam(value ="pageSize",defaultValue=AppConstants.DEFAULT_PAGE_SIZE,required=false)int pageSize,
	@RequestParam(value="sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy, //Sorting
	//This parameter is used to give option to the client to sort in ascending order or desending order in postman
	@RequestParam(value="sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false)String sortDir
	)//required=false because if we dont give pageNo and pageSize default values will be intilized, that's why required value is false
	{
		return postService.getAllPosts(pageNo,pageSize,sortBy, sortDir);
	}
	
	@GetMapping({"/{id}"})
	public ResponseEntity<PostDto> getPostById(@PathVariable(name="id") long id)
	{
		return ResponseEntity.ok(postService.getPostById(id));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePostById(@Valid @RequestBody PostDto postDto, @PathVariable(name="id") long id)
	{
		PostDto postResponse=postService.updatePost(postDto, id);
		return new ResponseEntity<>(postResponse, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable(name="id") long id)
	{
		postService.deletePostById(id);
		return new ResponseEntity<>("Post entity deleted successfully",HttpStatus.OK);
	}
}
