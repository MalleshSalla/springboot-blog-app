package com.springboot.blog.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.springboot.blog.exception.BlogApiException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	// This class is used to generate Jwt token.

	// Retrieving properties from application.properties
	@Value("${app.jwt-secret}")
	private String jwtSecret;

	@Value("${app-jwt-expiration-milliseconds}")
	private long jwtExpirationDate;

	// generate token
	// authentication object consisting of username,password, granted authorities
	public String generateToken(Authentication authentication) {
		// getName() method is used to get userName or email from authentication object
		String userName = authentication.getName();

		// Set expiration time to the token
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);
		
		String token = Jwts.builder().setSubject(userName).setHeaderParam("type", "JWT").setIssuedAt(new Date())
				.setExpiration(expireDate).signWith(key()).compact();
		return token;
	}

	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	// get username from jwt
	// This method is used to claim-(read or parse) Jwt details by providing 2
	// inputs 1.secret key 2.token
	// At the time of reading or parsing signature is only verified first. 
	// If signature is valid then only remaining will be read(header,payload)
	public String getUserName(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
		String userName = claims.getSubject();
		return userName;
	}

	// Validate Jwt token
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
			return true;
		} catch (MalformedJwtException ex) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "JWT claims string is empired");
		}
	}
}
/*
 * 1.@Value annotation is used to read values from application.properties. In
 * other words we can assign default values to the variables
 *
 * 2.Authentication The authentication interface in Spring Security is an
 * interface that defines the contract for authentication-related operations
 * Authentication interface provides methods to get information about the
 * authentication request such as the principal, credentials, and granted
 * authorities. Implementations of this interface are used to store
 * authentication-related information in Spring Security's security context,
 * which is used to make authorization decisions.
 *
 * 3.Jwts is a class it consisting of static method builder(). builder() method
 * is used to create a token with specific claims and options builder() method
 * allows you to specify the claims that you want to include in the token(such
 * as subject,expiration time) as well as many options we want to use when
 * creating token(signing algorithm)
 *
 * 4.signWith(key()): This method is used to sign the JWT with a specified
 * cryptographic key()(jwtSecret), ensuring the integrity and authenticity of
 * the token and its claims. This method will take Key as argument
 *
 * 5.key() : This method is used to get the cryptographic key(decoded jwt
 * secret) used to sign and verify the signature of a JWT. The method may return
 * a public or private key, depending on the use case and the implementation
 *
 * 6.compact(): This method encode(Converted to unreadable format) the Jwt token
 * as string. Return type of compact() method is String This String token
 * consisting of 3 parts seperated by dot(.) a.Header : It contains two parts
 * 1.Which Algorithm is used for signing the token 2.Type of algorithm(i.e Jwt)
 * b.Payload: It contains claims(username,Issue date,expiration time). Two types
 * of claims 1.Registered claims: These are set of predefined claims such as
 * Issuer, subject and expiration time that are standardized by the Jwt
 * specification. 2.Private claims: These are custom claims that are specific to
 * the application and can be used to store any additional information that is
 * relevant to the application c.Signature: It is Base64encoded Header+
 * Base64encoded Payload+Jwt secret
 *
 * 7.parserBuilder():This method is used to create JWT parser instance , which
 * returns a builder object that can be used to configure the parser. JWT parser
 * instance is an object that is used to parse JSON Web Tokens (JWTs) and
 * retrieve the claims from them
 *
 * 8.setSigningKey(key()): parser is configured with a signing key by calling
 * this method.key() method returns the signing key that will be used for
 * verification.
 *
 * 9.build(): Once the parser is configured, it is built using the build()
 * method.
 *
 * 10.parseClaimsJws(token): The parseClaimsJws() method verifies the signature
 * of the JWT and returns the claims.
 *
 * 11.getBody : This method in JWT (JSON Web Tokens) is used to retrieve the
 * payload or the claims contained in a JWT.
 *
 * 12.getSubject(): The getSubject() method in JSON Web Tokens (JWT) is used to
 * retrieve the "subject" claim from a JWT. The "subject" claim represents the
 * principal that is the subject of the JWT. It is typically used to carry
 * information such as the user ID, username, or email address of the
 * authenticated user. The getSubject() method is called on a Claims object,
 * which is the result of parsing a JWT using a JWT parser. The JWT parser is
 * responsible for verifying the signature of the JWT and decoding the payload
 * to extract the claims. The Claims object is a map-like structure that
 * contains the claims from the JWT, including the "subject" claim. The
 * getSubject() method retrieves the "subject" claim from the Claims object and
 * returns its value as a string. This string can then be used to retrieve
 * additional information about the authenticated user, such as the user's
 * profile information or authorization roles.
 *
 * compact() : This method takes the header(type of token and the signing
 * algorithm(takes implicitly)) and payload(setSubject, setIssuedAt, and
 * setExpiration methods. ) components of a JWT and signs them with a specified
 * cryptographic algorithm(jwt secret), creating a string that can be
 * transmitted between parties.
 */

/*
 * Encode refers to the process of converting data into a specific format or
 * encoding scheme, usually to make it more compact or secure. For example,
 * encoding a password before storing it in a database. Decode refers to the
 * reverse process of converting encoded data back into its original format. For
 * example, decoding a password from a database to check it against user input
 * during the login process.
 **/

/*
 */
