package com.quick.demo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class FollowUser {

	@ApiModelProperty(value = "user number", required = true)
	private String userNo;
	@ApiModelProperty(value = "user number followed by user", required = true)
	private String followNo;

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getFollowNo() {
		return followNo;
	}

	public void setFollowNo(String followNo) {
		this.followNo = followNo;
	}
}
