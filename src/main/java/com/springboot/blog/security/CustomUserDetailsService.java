package com.springboot.blog.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.springboot.blog.entity.User;
import com.springboot.blog.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService
{

	private UserRepository userRepository;
	
	public CustomUserDetailsService(UserRepository userRepository) {
		
		this.userRepository = userRepository;
	}

	//Step 1(Method 3)
	@Override
	public UserDetails loadUserByUsername(String userNameOrEmail) throws UsernameNotFoundException
	{
		com.springboot.blog.entity.User user=userRepository.findByUserNameOrEmail(userNameOrEmail, userNameOrEmail)
		.orElseThrow(()->new UsernameNotFoundException("User is not found with : "+userNameOrEmail));
		
		Set<GrantedAuthority> authorities=user.getRoles().stream()
		.map((role)->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
	}
	/*Steps to create Database authentication(User,Admin,Role authentication)
	 *1.Create class User(Entity), create "users" table, create "users_roles" table
	 *2.Create class Role(Entity), create "roles" table
	 *3.Create UserRepository define abstract methods in UserRepository
	 *4.Create RoleRepository define abstract methods in RoleRepository
	 *5.Create CustomUserDetailsService class implements UserDetailsService interface
	 *6.Create object of UserRepository interface in CustomUserDetailsService class to use interface abstract methods in CustomUserDetailsService class
	 *7.UserDetailsService interface provides abstract method loadUserByUserName(String username). Override that method in CustomUserDetailsService class. this method will return UserDetails user object 
	 *8.Create UserDetailsService interface object in SecurityConfig class. We are creating object for UserDetailsService interface not implementation class CustomUserDetailsService to avoid tight coupling
	 *9.Create securityFilterChain(HttpSecurity http) method and provide implementation to set which roles can access which methods
	 *8.Encrypt the password using  PasswordEncoder. It converts text type(String) password to unreadable code. This code is stored in the database not text password for more securing
	 *9.Create authenticationManager() method. This method will authenticate the user request by taking userDetailsService object encoded password automatically. We don't need to provide userDetailsService object
	 * */
	
	/* UserDetailsService interface
	 * UserDetailsService is an interface in the Spring Security framework that is used to load user-specific data in order to perform authentication and authorization. The interface has a single method called "loadUserByUsername()" which takes a String representing the username of the user and returns an implementation of the UserDetails interface, which contains all the information about the user that is required by the authentication and authorization process.
	 * A typical implementation of UserDetailsService will retrieve user data from a database or other storage mechanism and create a UserDetails object that contains the user's name, password, and a list of authorities (roles) that the user has. This implementation is then used by the Spring Security framework to authenticate and authorize the user.
	 * It is important to note that, UserDetailsService is a contract for retrieving user information. It is not used directly by Spring Security for authentication or authorization, but rather for loading user details to pass to an AuthenticationManager for those purposes.
	 * */
	
	/*UserDetails
	 * UserDetails is an interface in the Spring Security framework that represents the user's information that is required for authentication and authorization. It contains the following methods:
	 ->getUsername(): Returns the username of the user.
	 ->getPassword(): Returns the password of the user.
	 ->getAuthorities(): Returns the authorities (roles) that the user has.
	 ->isAccountNonExpired(): Returns true if the user's account has not expired.
	 ->isAccountNonLocked(): Returns true if the user's account is not locked.
	 ->isCredentialsNonExpired(): Returns true if the user's credentials (password) have not expired.
	 ->isEnabled(): Returns true if the user's account is enabled.
		A typical implementation of UserDetails will include the following information :
		1.username
		2.password
		3.a list of granted authorities (roles)
		account status information such as whether it is expired or locked.
		Spring Security provides a User class that implements the UserDetails interface which can be used as a reference implementation.
		In summary, UserDetails interface is a representation of a user's data, it contains all the information that Spring Security needs to perform authentication and authorization and it's the responsibility of UserDetailsService to load the user data and return it as UserDetails object.
	 * */
	
	/* GrantedAuthority, SimpleGrantedAuthority
	 * GrantedAuthority is an interface in the Spring Security framework that represents a user's role or authority. It has a single method called getAuthority() which returns a string representation of the authority.
	 * SimpleGrantedAuthority is an implementation of the GrantedAuthority interface that simply wraps a string representing the authority. It has a single constructor which takes a string argument, the authority string.
	 * In Spring Security, the authorities a user has are usually represented as a collection of strings, such as "ROLE_USER" or "ROLE_ADMIN". These strings can be used to check if a user has a specific role or permission, and are used to control access to resources and functionality in the application.
	 * A user can have multiple roles and authorities, SimpleGrantedAuthority is used to hold these roles and authorities, and it is passed to the UserDetails object, which is then used by the Spring Security framework for authentication and authorization.
	 * For example, if a user has the roles "ROLE_USER" and "ROLE_ADMIN", the UserDetails object would contain two SimpleGrantedAuthority objects, one for each role.
	 * In summary, GrantedAuthority is an interface which represents a user's role or authority, SimpleGrantedAuthority is an implementation of the GrantedAuthority interface which wraps a string representing the authority and it is used to hold the roles and authorities of a user.
	 * */
}
