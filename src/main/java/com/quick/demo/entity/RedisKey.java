package com.quick.demo.entity;

public enum RedisKey {

	Follower,
	Following;

	public String toKey(long uid) {
		return this.name() + "-" + uid;
	}
}
