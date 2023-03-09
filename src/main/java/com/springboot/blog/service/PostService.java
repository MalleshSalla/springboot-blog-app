package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

public interface PostService 
{
	//This is post method to store data
	PostDto createPost(PostDto postDto);
	
	//This method is used to get all post details
	PostResponse getAllPosts(int pageNo, int pageSize, String sortBy,String sortDir);
	
	//This method return results by id
	PostDto getPostById(long id);
	
	//This method is used to update details by id
	PostDto updatePost(PostDto postDto, long id);
	
	//This method is used to delete details by id
	void deletePostById(long id);
}
