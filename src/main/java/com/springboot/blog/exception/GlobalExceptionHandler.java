package com.springboot.blog.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.springboot.blog.payload.ErrorDetails;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{
	/*GlobalExceptionHandler handles global exceptions and specific exceptions(like ResourceNotFoundException, BlogApiException)
	 *@ExceptionHandler Handles specific exceptions(ResourceNotFoundException).@ExceptionHandler provided with ResourceNotFoundException.class It tells the spring that  if  that exception occured than only execute that method. 
	 *WebRequest is an interface which is used to send details from webrequest to client. webRequest>getDescription(false) false because we are sending url to the client not description. we will get url in details field 
	 *Handling specific exceptions
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
			                                                                            WebRequest webRequest)
	{
		ErrorDetails errorDetails=new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BlogApiException.class)
	public ResponseEntity<ErrorDetails> handleBlogApiException(BlogApiException exception, WebRequest webRequest)
	{
		ErrorDetails errorDetails=new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	/*Handling gblobal exceptions
	 *To handle global exceptions provide Exception class to the @ExceptionHandler annotation because Exception class is super class to the all the exceptions  
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalExceptions(Exception exception, WebRequest webRequest)
	{
		ErrorDetails errorDetails=new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/*The MethodArgumentNotValidException is a built-in exception in Spring and it is handled by the ResponseEntityExceptionHandler class by default.
	 *The ResponseEntityExceptionHandler class provides several methods that can be overridden to handle different types of exceptions, including handleMethodArgumentNotValid which is responsible for handling MethodArgumentNotValidException.
	 *By extending ResponseEntityExceptionHandler class, the handleMethodArgumentNotValid method will be overridden automatically, so there is no need to add the @ExceptionHandler annotation for MethodArgumentNotValidException again.
	 *ExceptionHandler annotation is applied to handle user defined exceptions example ResourceNotFound.
	 *In ex object all exception description is stored. 
	 *The getBindingResult() method is a method provided by the MethodArgumentNotValidException class in Spring, which is used to retrieve the BindingResult object that contains the validation errors that caused the exception to be thrown.
	 *getAllErrors() method  retrieves all validation errors that occurred during data binding and validation. 
	 *forEach(consumer) is used to retrieve field names(example "title","content") and messages(messages which are provided in PostDto class(Example "Title should have minimum 2 characters"))
	 *FieldError is class it consisting of getField() method. In forEach() error is casted to FieldError because we can not apply getField() method directly to the error. Casting the error to FieldError ensure the developer that the error is coming from a field, and not some other type of error. Since the method is only interested in the field name, it is safe to cast the error to FieldError to access the field name.
	 *getField() is a method that is available in the FieldError class in Spring Framework. It is used to retrieve the name of the field that caused the error.
	 *getDefaultMessage() method is used to retrieve message which is provided for each field in PostDto class.
	 */	
	//Method 1 Handling field exceptions
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
		                                    	HttpHeaders headers, HttpStatusCode status, WebRequest request)
	{
		Map<String, String> errors=new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String fieldName=((FieldError)error).getField();
			String message=error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
	}
	
	//Method 2 Handling field exceptions
	/*@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
        	WebRequest request)
	{
		Map<String, String> errors=new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error)->{
		String fieldName=((FieldError)error).getField();
		String message=error.getDefaultMessage();
		errors.put(fieldName, message);
	});
		return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
	}*/
	
	//Handling Access denied exception(These are occured when user want to access admin urls)
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException exception,
			                                                                            WebRequest webRequest)
	{
		ErrorDetails errorDetails=new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails,HttpStatus.UNAUTHORIZED);
	}
	
	
	
}
