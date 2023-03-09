package com.springboot.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.entity.User;


public interface UserRepository extends JpaRepository<User, Long>
{
	Optional<User> findByEmail(String email);
	
	Optional<User> findByUserNameOrEmail(String userName, String email);
	
	Optional<User> findByUserName(String userName);
	
	Boolean existsByUserName(String userName);
	
	Boolean existsByEmail(String email);


}
