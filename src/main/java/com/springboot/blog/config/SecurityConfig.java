package com.springboot.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.User;
import com.springboot.blog.security.CustomUserDetailsService;
import com.springboot.blog.security.JwtAuthenticationEntryPoint;
import com.springboot.blog.security.JwtAuthenticationFilter;

/*@Configuration annotation is used to indicate that a class defines a Spring configuration. A configuration class is a class that contains bean definitions and other application context metadata.@Configuration is processed by the Spring container when the application is started, and the container uses the information in the class to create and configure the beans that make up the application's context.
 *@EnableMethodSecurity is a Spring annotation that is used to enable method-level security in a Spring application. It can be used to configure security settings for methods in a Spring-managed bean, such as specifying which roles are allowed to invoke a method or which access rules should be applied to a method. When the annotation is applied to a configuration class, it triggers the configuration of Spring Security's method security infrastructure, which is used to protect methods in Spring-managed beans. It consisting of @PreAuthorization,@postAuthorization,@PreFilter,@PostFilter this annotations are applied to the controller class crud methods. According to those annotations it will give permissions to the roles
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Autowired
	private JwtAuthenticationFilter authenticationFilter;

	/*
	 * Method 1: Authentication using configuration
	 * proparties(application.proparties) securityFilterChain(HttpSecurity
	 * http):This method is used to create security configurations for application
	 * and this method returns security configured object. This is a method that
	 * takes in an instance of HttpSecurity and returns a SecurityFilterChain
	 * object. It configures the security filter chain for the application using the
	 * Spring Security framework. The HttpSecurity class is typically used in the
	 * configuration of a web application to set up security-related rules, such as
	 * which URLs require authentication, which roles are allowed to access certain
	 * resources, and which authentication mechanisms should be used. Once the
	 * security configuration is complete, the HttpSecurity instance can be used to
	 * generate a set of Spring Security filters that can be applied to the
	 * application's URLs to enforce the security rules. http.csrf().disable(): This
	 * method disables CSRF (Cross-Site Request Forgery) protection. CSRF is a type
	 * of attack in which an attacker tricks a user into performing an action on a
	 * website without their knowledge or consent. By disabling CSRF, the
	 * application will not have protection against this type of attack.
	 * authorizeHttpRequests((authorize)->authorize.anyRequest().authenticated()):
	 * This method configures the authorization rules for the application.This
	 * method takes lambda expression as argument. It specifies that any incoming
	 * request must be authenticated (i.e., the user must be logged in) in order to
	 * access the resource. httpBasic(Customizer.withDefaults()): This method
	 * enables HTTP Basic authentication for the application. The
	 * Customizer.withDefaults() call sets the authentication mechanism to use the
	 * default settings for the application. http.build(): This method builds the
	 * HttpSecurity object and returns the resulting SecurityFilterChain object that
	 * can be used to secure the application's URLs. build() method throws Exception
	 * so declare Exception
	 * 
	 * @Bean annotation tells the spring that create and manage
	 * object(SecurityFilterChain object) of particular method. If we not indicate
	 * with @Bean annotation than spring will not create object
	 * 
	 * @Bean public SecurityFilterChain securityFilterChain(HttpSecurity http)
	 * throws Exception {
	 * http.csrf().disable().authorizeHttpRequests((authorize)->authorize.anyRequest
	 * ().authenticated()) .httpBasic(Customizer.withDefaults()); return
	 * http.build(); }
	 */

	/*
	 * Method 2: InMemory authentication A class that implements the
	 * UserDetailsService interface is responsible for loading user information from
	 * a data store, such as a database, and creating a UserDetails object that
	 * contains the user's information and authorities. The UserDetails object is
	 * then used by the Spring Security framework to perform authentication and
	 * authorization. UserDetailsService interface consisting of one method
	 * loadUserByUsername(String username) this method is UserDetails return type. A
	 * UserDetails interface is typically used in a Java-based application to
	 * represent a user's information. It is used to provide a consistent way of
	 * accessing and managing user details across different parts of the
	 * application. The interface typically includes methods for accessing the
	 * user's username, password, and other information such as their roles and
	 * permissions. It is usually implemented by a class that retrieves and stores
	 * user information from a database or other storage system. User is a class it
	 * implements UserDetails interface. User class is used to set username,
	 * password, roles. builder() method is present in User class. builder() is a
	 * static method this method is used to returns userBuilder object in User
	 * class. UserBuilder class typically includes
	 * methods(username(),password(),roles()) for setting the various properties of
	 * a User object, such as the username, password, and roles. These methods
	 * usually return the UserBuilder instance, so that calls can be chained
	 * together. Once all the desired properties have been set, the build() method
	 * is called to construct the User object. InMemoryUserDetailsManager is a class
	 * that is provided by the Spring Security framework. It is an implementation of
	 * the UserDetailsService interface, and it can be used to store user
	 * information in memory.It stores the user information in a UserDetails objects
	 * in an in-memory collection (e.g. List, Set) and can be used to authenticate
	 * and authorize users in a simple in-memory authentication scenario. Step
	 * 1(Method 2)
	 *
	 * @Bean public UserDetailsService userDetailsService() { UserDetails
	 * sai=org.springframework.security.core.userdetails.User.builder().username(
	 * "saikumar").password(passwordEncoder().encode("12345")).roles("USER").build()
	 * ; UserDetails
	 * admin=org.springframework.security.core.userdetails.User.builder().username(
	 * "admin").password(passwordEncoder().encode("12345")).roles("ADMIN").build();
	 * return new InMemoryUserDetailsManager(sai,admin); }
	 */

	/*
	 * A password encoder is a mechanism used to encrypt or hash a user's password
	 * before it is stored in a database or other storage system. The purpose of
	 * this is to protect the password from being read or stolen by someone who
	 * gains unauthorized access to the storage system.When a user enters their
	 * password during the login process, the entered password is encoded in the
	 * same way and then compared with the stored encoded password. If the two
	 * encoded passwords match, the user is authenticated. BCryptPasswordEncoder: It
	 * uses the bcrypt algorithm to hash the passwords. This is a widely used
	 * algorithm that is considered to be very secure. Step 2(Method 2) Step
	 * 2(Method 3)
	 **/
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*
	 * Method 3: Database authentication AuthenticationManager is an interface in
	 * the Spring Security framework that is responsible for authenticating a user.
	 * It provides a method called authenticate() that takes in an Authentication
	 * object and returns an authenticated user. The Authentication object contains
	 * the user's credentials such as their username and password. The basic flow of
	 * authentication process is: A user attempts to access a protected resource.
	 * Spring Security's filter chain intercepts the request. Spring Security's
	 * filters call the AuthenticationManager to authenticate the user. The
	 * AuthenticationManager calls the UserDetailsService to load the user's
	 * details. The UserDetailsService returns the user's details to the
	 * AuthenticationManager. The AuthenticationManager compares the user's
	 * credentials with the loaded details. If the credentials match, the user is
	 * authenticated, otherwise, an exception is thrown. Note: We don't need to
	 * provide userDetailsService object and passwordEncoder to
	 * AuthenticationManager it automatically(spring will provide it to the
	 * AuthenticationManager) get and do database authentication. Step 3(Method 3)
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	/*
	 * This method provides the security configurations for role based
	 * authentication i.user and admin. is an implementation of a method called
	 * securityFilterChain() that appears to be creating a SecurityFilterChain
	 * object. SecurityFilterChain is a collection of filters in Spring Security
	 * that are used to secure a web application and it is associated with a
	 * specific request pattern, when a request is made to the application that
	 * matches the request pattern, the Spring Security framework will execute the
	 * filters in the associated SecurityFilterChain in a specific order. This
	 * method accepts an HttpSecurity object as a parameter and configures it by
	 * calling several methods on it. It disables the CSRF (Cross-Site Request
	 * Forgery) protection by calling the csrf().disable() method on the http
	 * object. authorizeHttpRequests() method accepts a lambda expression that
	 * configures the authorization rules for the application.
	 * requestMatchers(HttpMethod.GET,"/api/**") to match all GET requests that
	 * begin with "/api/", then calls permitAll() to allow all requests to these
	 * URLs(admin and user can access get methods) anyRequest().authenticated()
	 * method is used to require authentication for all other URLs(Other than Get
	 * methods) in the application. Finally, it calls the httpBasic() method and
	 * pass Customizer.withDefaults() as parameter to configure HTTP Basic
	 * authentication. It returns the result of calling the build() method on the
	 * http object, which creates a SecurityFilterChain object with the specified
	 * configuration Step 3(Method 2) In this method we are providing basic
	 * authentication using httpBasic(Customizer.withDefaults()) In this method we
	 * are providing access to the all Users for all GetMapping with url "/api"
	 * 
	 * @Bean SecurityFilterChain securityFilterChain(HttpSecurity http) throws
	 * Exception { http.csrf().disable().authorizeHttpRequests((authorize)->
	 * authorize.requestMatchers(HttpMethod.GET,"/api/**").permitAll().anyRequest().
	 * authenticated()) .httpBasic(Customizer.withDefaults()); return http.build();
	 * }
	 */

	/*
	 * Step 4(Method 3) In this method we are providing authentication using login
	 * functionality so no need of httpBasic(Customizer.withDefaults()) method. In
	 * this method we are providing access to the all Users for all GetMapping with
	 * url "/api/*" to get data and url "/api/auth/**" methods to login all users
	 * 
	 * @Bean SecurityFilterChain securityFilterChain(HttpSecurity http) throws
	 * Exception { http.csrf().disable().authorizeHttpRequests((authorize)->
	 * authorize.requestMatchers(HttpMethod.GET,"/api/**").permitAll()
	 * .requestMatchers("/api/auth/**").permitAll().anyRequest().authenticated());
	 * 
	 * return http.build(); }
	 */
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeHttpRequests((authorize) -> authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
						.requestMatchers("/api/auth/**").permitAll().anyRequest().authenticated())
				.exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
/*
 * Spring security provides 2 types of authentications 1.Form based
 * authentication 2.Basic authentication header(Username and password passed to
 * the header)
 */
