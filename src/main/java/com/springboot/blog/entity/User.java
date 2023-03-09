package com.springboot.blog.entity;




import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name="users")
public class User 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	
	@Column(nullable = false,unique = true)
	private String userName;
	
	@Column(nullable = false,unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
   /*ManyToMany relationship between User and Role Jpa entity(Connection between User and Role table). ManyToMany : Multiple records in one table are associated with multiple records in another table
	*FetchType.EAGER  It is used for whenever we load User  along with it fetches Roles
	*CascadeType.ALL  Whenever we perform any action(save,update,delete) on parent(User) it is applicable to child(Role)
	*@JoinTable annotation is used to create a join table(users_roles table) between two entities in a many-to-many relationship. The join table is used to store the relationships between the entities. @JoinTable annotation has several attributes that can be used to configure the join table, one of them is the "inverseJoinColumns" attribute.
	*Primary key of User table(id) becomes foreign key in Role table
	*Here we are creating seperate users_roles table with 2 columns by establishing connection with users table and roles table. First column of the users_roles table is id column(primary key) of users table. Second column of users_roles table is id column(primary key) of roles table. 
	*@JoinTable annotation has several attributes that can be used to configure the join table, two of them are the "joinColumns" and "inverseJoinColumns" attributes
	*joinColumns: The "joinColumns" attribute is used to specify the foreign key column(s) in the join table(users_roles table) that references the primary key of the entity(users table) on the "owning" side of the relationship. It defines the columns of the table on which the join will be based on. Owing side table is User table because in User class we are providing implementation for creating "users_roles" table
	*"inverseJoinColumns" attribute is used to specify the foreign key column(s) in the join table(users_roles table) that references the primary key of the entity(roles table) on the "inverse" side of the relationship. It defines the columns of the table on the other side of the join. Inverse side is roles table because we are not providing implementation for creating users_roles table in roles class. we are declaring Role entity as variable in User class so to specify role_id as foreign key in(which is "id" column in roles table) users_roles table using inverseJoinColumn attribute. 
	*/
	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(name="users_roles", joinColumns = @JoinColumn(name="user_id",referencedColumnName="id"),
	                              inverseJoinColumns=@JoinColumn(name="role_id", referencedColumnName="id"))
	private Set<Role> roles;
	//Set is used to store only unique values it doesn't allows duplicate user_id and role_id
}
