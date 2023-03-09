package com.springboot.blog.security;

import java.io.IOException;
import java.net.http.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter
{

	private JwtTokenProvider jwtTokenProvider;
	private UserDetailsService userDetailsService;
	
	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
		
		this.jwtTokenProvider = jwtTokenProvider;
		this.userDetailsService = userDetailsService;
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException 
	{
		//get token from http request
		String token=getTokenFromRequest(request);
	
		// validate token
        if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token))
        {
			//get username from token
			String userName=jwtTokenProvider.getUserName(token);
			
			//loadUserByUsername(userName)  method is used to load user details(retrieve user data) from database
			UserDetails userDetails=userDetailsService.loadUserByUsername(userName);
			
			//UsernamePasswordAuthenticationToken is a class in the Spring Security framework, used for authentication purposes. It represents a token that contains a username and password, which can be used to authenticate a user and grant them access to protected resources. The UsernamePasswordAuthenticationToken is typically created from the user-supplied credentials and then passed to an authentication manager for authentication.
			//userDetails object will give the username and password, userDetails.getAuthorities() method gives roles, no need to pass credentials as second argument because userDetails object already contains user details.
			UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		
			//add request object to authentication token
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			//set authenticationToken object to the security context holder
			SecurityContextHolder.getContext().setAuthentication(authenticationToken); 
        }
        filterChain.doFilter(request, response);
	}
	
	
	/*HttpServletRequest object(request) consisting of header. 
	 *Header consisting of key and value pair. key is "Authorization" and value is "Bearer token" 
	 *getHeader() method in JWT (JSON Web Tokens) is used to retrieve the header part of the JWT. This header consisting of Key(Authorization) and value(bearerToken).
	 *To get bearer token pass key(Authorization) as String argument to the getHeader() method, we will get bearer token.
	 */
	private  String getTokenFromRequest(HttpServletRequest request)
	{
		String bearerToken=request.getHeader("Authorization");
		
		//StringUtils.hasText(bearerToken) method checks weather token is empty or not null and contains text. If text is there returns true otherwise return false
		if(StringUtils.hasText(bearerToken)&&bearerToken.startsWith("Bearer "))
		{
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
	
}



