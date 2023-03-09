package com.springboot.blog.payload;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDto {
/*Dto stands for Data transfer object.It is a design pattern which is used to pass data with multiple
  parameters from client(Postman)to server in one shot
 *Dto is used to transfer data between client and server
 *The advantage of using Dto is which helps to hide implementation of Jpa entity.
 *This class is for sending data to the controller and send back response to the client*/	

	private long id;
	
	//Title should not be null or empty, Title should have atleast 2 characters
	@NotEmpty
	@Size(min=2, message = "Title should have minimum 2 characters")
	private String title;
	
	//Post description should not be null or empty, Post description should have atleast 10 characters
	@NotEmpty
	@Size(min=10, message = "Description should have minimum 10 characters")
	private String description;
	
	//Post content should not be null or empty
	@NotEmpty(message = "Must not be empty")
	private String content;
	
	//This field is for displaying post details along with comments
	//Post comment should not be null or empty
	
	private Set<CommentDto> comments;
	
	/*Steps to add validations
	 * 1.Add springboot validations dependency in pom.xml file
	 * 2.Apply validation annotations to the PostDto class
	 * 3.Add @Valid annotation in Controller class Post mapping method, Put mapping method argument (Along with @RequestBody annotation) 
	 * 
	 * Steps to get custom exception response when field exceptions occured
	 * 1.To customize exception response extends GlobalExceptionHandler class with ResponseEntityExceptionHandler class.ResponseEntityExceptionHandler class provides some methods by overriding that methods we will get custome exception response
	 * 2.MethodArgumentNotValidException is thrown when validation fails. 
	 * */
	
}
