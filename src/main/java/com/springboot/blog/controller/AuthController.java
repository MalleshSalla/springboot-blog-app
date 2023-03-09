package com.springboot.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.JwtAuthResponse;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.RegisterDto;
import com.springboot.blog.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController 
{
	@Autowired
	private AuthService authService;
	
	//Build login rest api
	/*@PostMapping(value = {"/login","signin"})
	public ResponseEntity<String> login(@RequestBody LoginDto loginDto)
	{
		String response=authService.login(loginDto);
		return ResponseEntity.ok(response);
	}*/
	
	@PostMapping(value = {"/login","/signin"})
	public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto)
	{
		String token=authService.login(loginDto);
		JwtAuthResponse jwtAuthResponse=new JwtAuthResponse();
		jwtAuthResponse.setAccessToken(token);
		return ResponseEntity.ok(jwtAuthResponse);
	}
	
	
	//Build register rest api
	@PostMapping(value= {"/register", "/signup"})
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto)
	{
		String response=authService.register(registerDto);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
}
