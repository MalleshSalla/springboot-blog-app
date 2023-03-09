package com.springboot.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.entity.Post;
/*We dont need to add @Repository here because JpaRepository internally implements SimpleJpaRepository 
 * which is annotated with @Repository. So we dont need to add @Repository */
public interface PostRepository extends JpaRepository<Post, Long>
{
	
}
