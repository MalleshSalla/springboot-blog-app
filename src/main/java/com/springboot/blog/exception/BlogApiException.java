package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;

public class BlogApiException extends RuntimeException
{
	//We throw this exception whenever we write some business logic or validate request parameter
	private HttpStatus httpStatus;
	private String message;
	
	public BlogApiException(HttpStatus httpStatus, String message) {
		super();
		this.httpStatus = httpStatus;
		this.message = message;
	}


	public BlogApiException(String message,HttpStatus httpStatus,String message1)
	{
		super(message);
		this.httpStatus=httpStatus;
		this.message=message1;
	}


	public HttpStatus getHttpStatus() {
		return httpStatus;
	}


	public String getMessage() {
		return message;
	}

	
	
	
	
	
	
}
