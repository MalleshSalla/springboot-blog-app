package com.springboot.blog.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.RegisterDto;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.security.JwtTokenProvider;
import com.springboot.blog.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService
{
	//Signin or login implementation
	//We can authenticate the login request using authenticationManager() method which is present in AuthenticationManager interface. So If we want to use authenticate() method we have to create object of AuthenticationManager using @AutoWired. authenticate() method returns Authenticate object.  
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	/*public String login(LoginDto loginDto)
	{
		Authentication authentication=authenticationManager.authenticate
				(new UsernamePasswordAuthenticationToken(loginDto.getUserNameOrEmail(), loginDto.getPassword()));
		
	   	*SecurityContextHolder: It stores the SecurityContext. SecurityContext consisting of authentication object which holds information about the principal that is currently authenticated(Currently Logged-in user), such as their username,password, roles and authorities. 
		*getContext():  Retrieving the SecurityContext from the SecurityContextHolder. 
		*setAuthentication(): It is used to set the authentication object in the SecurityContext.
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return "User Logged-in successfully";
	}
	*/
	
	public String login(LoginDto loginDto)
	{
		Authentication authentication=authenticationManager.authenticate
				(new UsernamePasswordAuthenticationToken(loginDto.getUserNameOrEmail(), loginDto.getPassword()));
		
	   /*SecurityContextHolder: It stores the SecurityContext. SecurityContext consisting of authentication object which holds information about the principal that is currently authenticated(Currently Logged-in user), such as their username,password, roles and authorities. 
		*getContext():  Retrieving the SecurityContext from the SecurityContextHolder. 
		*setAuthentication(): It is used to set the authentication object in the SecurityContext.
		*/
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token=jwtTokenProvider.generateToken(authentication);
		return token;
	}

	//Register User implementation
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public String register(RegisterDto registerDto) 
	{
		//Check weather user exist with same username
		if(userRepository.existsByUserName(registerDto.getUserName()))
		{
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Username is already exists");
		}
		
		//Check weather user exist with same email
		if(userRepository.existsByEmail(registerDto.getEmail()))
		{
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Email is already exists");
		}
		
		User user=new User();
		user.setName(registerDto.getName());
		user.setUserName(registerDto.getUserName());
		user.setEmail(registerDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		
		Set<Role> roles=new HashSet<>();
		//By default new Registered user is assigned with role "ROLE_USER"
		Role userRole=roleRepository.findByName("ROLE_USER").get();
		roles.add(userRole);
		user.setRoles(roles);
		userRepository.save(user);
		return "User is registered successfully";
	}
	
}
