package com.quick.demo.repository;

import com.quick.demo.entity.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.time.Instant;

@Repository
public class RedisUserRelationRepository implements UserRelationRepository {

	@Autowired
	private RedisTemplate<String, Serializable> redisTemplate;

	@Override
	public void follow(long uid, long followId) {
		//
		// 1. followId增加follower uid,
		// 2. uid增加following followId

		long epochSecond = Instant.now().getEpochSecond();
		redisTemplate.opsForHash().put(RedisKey.Following.toKey(uid), followId, epochSecond);
		redisTemplate.opsForHash().put(RedisKey.Follower.toKey(followId), uid, epochSecond);
	}
}
