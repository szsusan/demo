package com.quick.demo.repository;

import com.quick.demo.entity.Address;
import com.quick.demo.entity.RedisKey;
import com.quick.demo.util.Current;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class RedisUserRelationRepository implements UserRelationRepository {

	@Autowired
	private RedisTemplate<String, Serializable> redisTemplate;

	private DefaultRedisScript<Long> followScript;
	private DefaultRedisScript<Long> unfollowScript;
	private DefaultRedisScript<List<Long>> friendScript;

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

	@Override
	public List<String> friends(String userNo, List<String> userNoList) {

		String followerKey = RedisKey.Follower.toKey(userNo);
		String followingKey = RedisKey.Following.toKey(userNo);

		List<Long> execute = redisTemplate.execute(friendScript,
				userNoList, followerKey, followingKey);
		if (execute == null) {
			return Collections.emptyList();
		}
		Validate.isTrue(execute.size() == userNoList.size());
		Long SUCCESS = 1L;
		List<String> strings = new ArrayList<>();
		for (int i = 0; i < userNoList.size(); i++) {
			if (SUCCESS.equals(execute.get(i))) {
				strings.add(userNoList.get(i));
			}
		}
		return strings;
	}

	@Override
	public void addGeo(String userNo, Address address) {
		Point point = new Point(Double.parseDouble(address.getLongitude()), Double.parseDouble(address.getLatitude()));
		redisTemplate.boundGeoOps(geoKey()).add(point, userNo);
	}

	@Override
	public List<String> nearby(String userNo, int limit, double maxDistance) {
		RedisGeoCommands.GeoRadiusCommandArgs args =
				RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().sortAscending().limit(limit);
		RedisGeoCommands.DistanceUnit meters = RedisGeoCommands.DistanceUnit.METERS;
		GeoResults<RedisGeoCommands.GeoLocation<Serializable>> geoResults = redisTemplate.opsForGeo()
				.radius(geoKey(), userNo, new Distance(maxDistance, meters), args);
		return Optional.ofNullable(geoResults)
				.map(GeoResults::getContent)
				.orElseGet(Collections::emptyList)
				.stream().map(GeoResult::getContent)
				.map(r -> r.getName().toString())
				.filter(r -> !userNo.equals(r))
				.collect(Collectors.toList());
	}

	private String geoKey() {
		return RedisKey.Geo.toKey("USER");
	}

	@PostConstruct
	public void script() throws IOException {

		// 1. followId增加follower uid,
		// 2. uid增加following followId
		followScript = new DefaultRedisScript<>("redis.call('hset',KEYS[1],ARGV[1],ARGV[3]) return redis.call" +
				"('hset',KEYS[2],ARGV[2],ARGV[3])", Long.class);

		// 1. followId删除follower uid,
		// 2. uid删除following followId
		unfollowScript = new DefaultRedisScript<>("redis.call('hdel',KEYS[1],ARGV[1]) return redis.call('hdel'," +
				"KEYS[2],ARGV[2])", Long.class);
		//load lua
		loadLua();
	}

	private void loadLua() throws IOException {

		byte[] bytes = Files.readAllBytes(ResourceUtils.getFile("classpath:friend.lua").toPath());
		friendScript = new DefaultRedisScript(new String(bytes), List.class);
	}
}
