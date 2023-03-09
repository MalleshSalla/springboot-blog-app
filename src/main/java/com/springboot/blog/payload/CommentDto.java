package com.springboot.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto 
{
	private long id;
	
	//name should not be empty
	@NotEmpty(message = "Name should not be empty")
	private String name;
	
	//email should not be null or empty
	//email field validation
	@NotEmpty(message = "Email should not be empty")
	@Email(message = "Entered email is not valid")
	private String email;
	
	//comment body should not be null or empty
	//comment body must be minimum 10 characters
	@NotEmpty(message = "Comment body should not be empty")
	@Size(min=10 , message="Comment body should have minimum 10 characters")
	private String body;
}

/*Steps to create Comment entity
 1.Create entity package and Create Comment class and declare all fields and create table name, column name
 2.Create CommentRepository and extends JpaRepository 
 3.Create payload package and create CommentDto class declare all fields
 4.Create service package and create commentservice interface in that interface define abstract methods
 5.Create CommentService implementation package and create CommentService class which implements CommentService interface override abstract methods.
 6.Create controller package and create controller class create CommentService objject using @Autowired and create get,put,post,delete methods.
 * */
