package com.springboot.blog.payload;

import java.util.Date;

public class ErrorDetails 
{
	/*Steps to add Exception handling 
	 *1.Create error details class
	 *2.Create GlobalExceptionHandler Class
	 * */
	private Date timestamp;
	private String message;
	private String details;
	
	public ErrorDetails(Date date, String message, String details) {
		super();
		this.timestamp = date;
		this.message = message;
		this.details = details;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getDetails() {
		return details;
	}
}
