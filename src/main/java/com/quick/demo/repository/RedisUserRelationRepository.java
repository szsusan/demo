package com.quick.demo.repository;

import com.quick.demo.entity.RedisKey;
import com.quick.demo.util.Current;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RedisUserRelationRepository implements UserRelationRepository {

	@Autowired
	private RedisTemplate<String, Serializable> redisTemplate;

	private DefaultRedisScript<Long> followScript;
	private DefaultRedisScript<Long> unfollowScript;

	@Override
	public void follow(String userNo, String followNo) {
		redisTemplate.execute(followScript,
				List.of(RedisKey.Follower.toKey(followNo), RedisKey.Following.toKey(userNo)),
				userNo, followNo, String.valueOf(Current.toMills()));
	}

	@Override
	public void unfollow(String userNo, String followNo) {
		redisTemplate.execute(unfollowScript,
				List.of(RedisKey.Follower.toKey(followNo), RedisKey.Following.toKey(userNo)),
				userNo, followNo);
	}

	@Override
	public List<String> queryFollower(String userNo) {
		return redisTemplate.opsForHash().keys(RedisKey.Follower.toKey(userNo))
				.stream().map(String::valueOf).collect(Collectors.toList());
	}

	@Override
	public List<String> queryFollowing(String userNo) {
		return redisTemplate.opsForHash().keys(RedisKey.Following.toKey(userNo))
				.stream().map(String::valueOf).collect(Collectors.toList());
	}

	@PostConstruct
	public void script() {

		// 1. followId增加follower uid,
		// 2. uid增加following followId
		followScript = new DefaultRedisScript<>("redis.call('hset',KEYS[1],ARGV[1],ARGV[3]) return redis.call" +
				"('hset',KEYS[2],ARGV[2],ARGV[3])", Long.class);

		// 1. followId删除follower uid,
		// 2. uid删除following followId
		unfollowScript = new DefaultRedisScript<>("redis.call('hdel',KEYS[1],ARGV[1]) return redis.call('hdel'," +
				"KEYS[2],ARGV[2])", Long.class);
	}
}
