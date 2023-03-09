package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto 
{
	private String name;
	private String userName;
	private String email;
	private String password;
}
/*Steps to create Register functionality
 * 1.Create LoginDto class
 * 2.Create AuthService interface and create abstract method
 * 3.Create AuthServiceImpl class and create object of AuthenticationManager interface for getting authenticate() method
 * 4.Override abstract method in AuthServiceImpl class
 * 5.Implement  configuration methods in SecurityConfig class
 * 6.Create Controller class and create PostMapping method
 */
