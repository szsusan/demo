package com.quick.demo.entity;

import javax.persistence.*;

@Entity
@Table(indexes = @Index(columnList = "userId"))
public class User {

	@Id()
	@Column()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(unique = true)
	private String userId;
	private String name;
	private String dob;
	@Embedded
	private Address address;
	private long createdAt;

}
