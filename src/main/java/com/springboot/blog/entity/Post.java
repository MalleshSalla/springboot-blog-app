package com.springboot.blog.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "posts", uniqueConstraints = { @UniqueConstraint(columnNames = { "title" }) })
public class Post {
	
	//This class is used to Map data to the database
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "title", nullable = false)
	private String title;
	@Column(name = "description", nullable = false)
	private String description;
	@Column(name = "content", nullable = false)
	private String content;
	
	/* Set does not allow duplicate comments. It stores only unique comments that why we are using set here
	 *CascadeType.All remove data(comments) of particular id when we removing id in Posts table
	 *orphanRemoval=true specifies when ever parent record is removed child record also remove from the child table
	 *OneToMany: One record of one table(posts table) is associated with multiple records of another table(comments table). One post can have multiple comments*/
	 @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,orphanRemoval = true)
	private Set<Comment> comments=new HashSet<>();
}
