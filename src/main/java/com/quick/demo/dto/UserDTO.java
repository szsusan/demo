package com.quick.demo.dto;

import com.quick.demo.config.DateTimeFormat;
import com.quick.demo.entity.Address;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotBlank;

@ApiModel
public class UserDTO {

	private String id;
	private Address address;
	private long createAt;
	@NotBlank
	private String name;
	@DateTimeFormat(format = "yyyy-MM-dd", message = "invalid date format")
	private String dob;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public long getCreateAt() {
		return createAt;
	}

	public void setCreateAt(long createAt) {
		this.createAt = createAt;
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
}
