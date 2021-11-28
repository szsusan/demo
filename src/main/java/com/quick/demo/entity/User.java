package com.quick.demo.entity;

import javax.persistence.*;

@Entity
@Table(indexes = @Index(columnList = "user_no"))
public class User {

	@Id()
	@Column()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "user_No", unique = true)
	private String userNo;
	private String name;
	private String dob;
	@Embedded
	private Address address;
	@Column(name = "created_at")
	private long createdAt;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userId) {
		this.userNo = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}
}
