package com.springboot.blog.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="Comment")
public class Comment
{
	//This class is used to store comments of post in comment table. Connection is established between posts table and comment table by making post_id as foreign key in comment table
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String email;
	private String body;
	
	/*ManyToOne : Multiple records of one table(comment table) are associated with one record of another table(posts table). Multiple comments have one post
	 *FetchType.LAZY tells the hibernate to fetch only related entites from the database when you use the relationship
	 *@JoinColumn is a JPA (Java Persistence API) annotation used to specify the column that is used to join
	 * two entities in a one-to-many or many-to-many relationship. It is typically used in the "one" side of the relationship, 
	 * and it corresponds to a foreign key column in the database table that corresponds to the "many" side of the relationship.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="post_id",nullable = false)
	private Post post;
	
}
