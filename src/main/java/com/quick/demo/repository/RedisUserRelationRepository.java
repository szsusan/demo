package com.quick.demo.repository;

import com.quick.demo.entity.RedisKey;
import com.quick.demo.util.Current;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RedisUserRelationRepository implements UserRelationRepository {

	@Autowired
	private RedisTemplate<String, Serializable> redisTemplate;

	@Override
	public void follow(long uid, long followId) {
		//
		// 1. followId增加follower uid,
		// 2. uid增加following followId

		String script = "redis.call('hset',KEYS[1],ARGV[1],ARGV[3]) return redis.call('hset',KEYS[2],ARGV[2],ARGV[3])";
		DefaultRedisScript<Integer> integerDefaultRedisScript = new DefaultRedisScript<>(script);
		redisTemplate.execute(integerDefaultRedisScript,
				List.of(RedisKey.Follower.toKey(followId), RedisKey.Following.toKey(uid)),
				uid, followId, Current.toMills());
	}

	@Override
	public void unfollow(long uid, long followId) {
		//
		// 1. followId删除follower uid,
		// 2. uid删除following followId
		String script = "redis.call('hdel',KEYS[1],ARGV[1]) return redis.call('hdel',KEYS[2],ARGV[2])";
		DefaultRedisScript<Integer> integerDefaultRedisScript = new DefaultRedisScript<>(script);
		redisTemplate.execute(integerDefaultRedisScript,
				List.of(RedisKey.Follower.toKey(followId), RedisKey.Following.toKey(uid)),
				uid, followId);
	}

	@Override
	public List<Long> queryFollower(long uid) {
		return redisTemplate.opsForHash().keys(RedisKey.Follower.toKey(uid))
				.stream().map(o -> (Long) o).collect(Collectors.toList());
	}

	@Override
	public List<Long> queryFollowing(Long uid) {
		return redisTemplate.opsForHash().keys(RedisKey.Following.toKey(uid))
				.stream().map(o -> (Long) o).collect(Collectors.toList());
	}
}
