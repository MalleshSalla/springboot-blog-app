package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto 
{
	private String userNameOrEmail;
	private String password;
	
}

/*Steps to create signin or login functionality
 * 1.Create LoginDto class
 * 2.Create AuthService interface and create abstract method
 * 3.Create AuthServiceImpl class and create object of AuthenticationManager interface for getting authenticate() method
 * 4.Override abstract method in AuthServiceImpl class
 * 5.Implement  configuration methods in SecurityConfig class
 * 6.Create Controller class and create PostMapping method
 */