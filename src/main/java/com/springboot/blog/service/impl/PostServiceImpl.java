package com.springboot.blog.service.impl;

import java.awt.print.Pageable;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.mapping.List;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService
{
	private PostRepository postRepository;
	
	private ModelMapper mapper;
	
	//If we have only one constructor don't need to mention @Autowired annotation for creating bean object
	public PostServiceImpl(PostRepository postRepository, ModelMapper mapper)
	{
		this.postRepository=postRepository;
		this.mapper=mapper;
	}
	
	public PostDto createPost(PostDto postDto)
	{
		//Convert Dto to entity(Data which is sending from postman as a Json parameter(Data transfer object)and converting it into entity(Java obect))
		Post post=mapToEntity(postDto);//We should create Post default constructor in Post class because we have already constructor with argument. If constructor is there in class than compiler will not create default constructor, so we have to create default constructor
		Post newPost=postRepository.save(post);
		
		//Convert Entity to Dto(Sending response from controller to client as Dto. so converting entity(java object) to Dto)
		PostDto postResponse=mapToDto(newPost);
		
		return postResponse;
		//Post datatype is  java object type
		//PostDto datatype is Dto type
	}

	
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) 
	{
		/*Create pagable object. pageble object storing pageNo,pageSize values
		 *PageRequest is a class which consist of of() method. PageRequest class implements Pageable interface
		 *The PageRequest.of() method takes two parameters: the page number and the page size. It returns an instance of PageRequest that can be passed to a repository's method that supports pagination, such as findAll(Pageable pageable)
		 *Sort is a class it consisting of of() method. sort the object that can be passed to the repository method
		 *org.springframework.data.domain.Pageable pageable=PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending()); */
		Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
		org.springframework.data.domain.Pageable pageable=PageRequest.of(pageNo, pageSize, sort);
		
		//findAll() method returns pages of entites i.e given pageNo,pageSize which is of Page<Post> type. suppose total records 14 ,if we are giving pageSize=10 than 10 records will be displayed as response in postman and pageNumber is 1, remaining 5 records displayed in second pageNumber
		Page<Post> posts=postRepository.findAll(pageable);
		
		//Get content for page object
		//getContent() method is used to retrieve data from post object.
		java.util.List<Post> listOfPosts=posts.getContent();
		
		//Converting entity List<Post>(java object) to Dto using streams
		//mapToDto() method is called and converted from List<Post>(Java obect) to Dto.
		java.util.List<PostDto> content= listOfPosts.stream().map(post->mapToDto(post)).collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(posts.getNumber());//getNumber() method is used to get page number of particular page
		postResponse.setPageSize(posts.getSize());//getSize() method id used to get total records in that page
		postResponse.setTotalElements(posts.getTotalElements()); //getTotalElements() method returns total number of elements in the database
		postResponse.setTotalPages(posts.getTotalPages()); //getTotalPages() method 
		postResponse.setLast(posts.isLast()); //isLast() method return boolean value and it indicates that weather that page is last page or not. If page is last page return true otherwise return false
		return postResponse;
	}
	
	public PostDto getPostById(long id)
	{
		Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "id", id));
		return mapToDto(post);
	}
	
	public PostDto updatePost(PostDto postDto, long id)
	{
		Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "id", id));
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		
		Post updatePost=postRepository.save(post);
		return mapToDto(updatePost);
	}
	
	@Override
	public void deletePostById(long id)
	{
		Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "id", id));	
		postRepository.delete(post);
	}
	
	//Convert entity(java object) to Dto
	//This method is used to send response as Dto to the  CRUD methods, that  methods  will return response as Dto
	private PostDto mapToDto(Post post)
	{
		PostDto postDto=mapper.map(post, PostDto.class);
	  /*PostDto postDto=new PostDto(); //No need to create PostDto constructor in PostDto class because Compiler will create default constructor automatically
		postDto.setId(post.getId());
		postDto.setTitle(post.getTitle());
		postDto.setDescription(post.getDescription());
		postDto.setContent(post.getContent());*/
		return postDto;
	}
	
	//Converted Dto to entity(Java object)
	private Post mapToEntity(PostDto postDto)
	{
		Post post=mapper.map(postDto, Post.class); //mapper is object and it is ModelPaper type it consisting of map() method to convert postDto object to post. map() method consisting of two arguments map(source(passing given object), destination(convertable object type classname.class))
		/*Post post=new Post();   //We should create Post default constructor in Post class because we have already constructor with argument. If constructor is there in class than compiler will not create default constructor, so we have to create default constructor
		post.setId(postDto.getId());
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());*/
		return post;
	}
}
