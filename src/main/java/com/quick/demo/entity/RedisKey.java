package com.quick.demo.entity;

public enum RedisKey {

	Follower,
	Following;

	public String toKey(String userNo) {
		return this.name() + "-" + userNo;
	}
}
