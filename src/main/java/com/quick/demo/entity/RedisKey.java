package com.quick.demo.entity;

public enum RedisKey {

	Follower,
	Following,
	Geo;

	public String toKey(String identity) {
		return this.name() + "-" + identity;
	}
}
