package com.springboot.blog.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//Make class as spring component or spring bean
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint
{
	//This class is used to throw exception(Unauthorized) when unauthorized user try to access the resource
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException 
	{
		
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
	}
	
}
/*AuthenticationEntryPoint
 *1.AuthenticationEntryPoint is an interface it consisting of commence abstract method
 *2.The "commence" method is called when an unauthenticated user tries to access a protected resource. 
 *The implementation of the method defines what happens when an unauthorized request is made, such as redirecting the user to a login page or returning a 401 (Unauthorized) HTTP response.
 *
 */

/*HttpServletRequest
 *1.HttpServletRequest interface represents an HTTP request received by a servlet. 
 *2.It provides methods to retrieve information(consisting of HttpServletRequest object) such as headers, parameters, request method, and the requested URI, among others
 *3.The information obtained through this interface can be used to customize the behavior of a servlet for a particular request, for example, to perform authentication or provide content based on user's locale.
 *
 *HttpServletResponse
 *1.HttpServletResponse is an interface used in Java Servlet API that represents a response from a servlet to a client. 
 *2.This interface consisting of some methods which are used to send response to the client. commence method will give the HttpServletRequest(It represents Http request received from sevlet) and HttpServletResponse(It represents response from servlet to client), AuthenticationException objects as arguments.
 *3.This object contains default response which is received from servlet. We can customize response and send to the client by using different methods provided by HttpResponseServlet interface. 
 *4.sendError(int sc, String mes) method returns status code and exception name to the client.
 *
 *AuthenticationException
 *1.When client send request , that request will be authenticated by AuthenticationManager.
 *2.If authentication fails AuthenticationException is thrown by the authentication system, such as the AuthenticationManager
 *3.If the authentication process fails, the commence method will catch the AuthenticationException that was thrown by the AuthenticationManager and use the information contained in the exception to generate an error response, such as a 401 Unauthorized HTTP status code, that is returned to the user. 
 */

/*Steps to implement Jwt authentication 
 *1.Add Jwt related maven dependency
 *2.Create JwtAuthenticationEntryPoint
 *3.Add Jwt propraties in application.properties
 *4.Create JwtTokenProvider-Utility class
 *5.Create JwtAuthenticationFilter
 *6.Create JwtAuthResponse Dto
 *7.Configure Jwt in spring security
 *8.Change login/signin Rest api to return Jwt token
 */