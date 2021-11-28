package com.quick.demo.repository;

import com.quick.demo.entity.Address;

import java.util.List;

public interface UserRelationRepository {

	/**
	 * Specify user follows the other;
	 *
	 * @param userNo   specify user
	 * @param followNo the other user
	 */
	void follow(String userNo, String followNo);

	/**
	 * Specify user do not follows the other any more;
	 *
	 * @param userNo   specify user
	 * @param followNo the other user
	 */
	void unfollow(String userNo, String followNo);

	/**
	 * @param userNo user number
	 * @return user's follower
	 */
	List<String> queryFollower(String userNo);

	/**
	 * @param userNo user number
	 * @return user's following
	 */
	List<String> queryFollowing(String userNo);

	/**
	 * @param userNo  user number
	 * @param address use address with longitude and latitude  required
	 */
	void addGeo(String userNo, Address address);

	/**
	 * @param userNo      user number
	 * @param limit       max user number nearby
	 * @param maxDistance max distance nearby
	 * @return nearby users
	 */
	List<String> nearby(String userNo, int limit, double maxDistance);

	/**
	 * @param userNo     specify user
	 * @param userNoList user  list
	 * @return user friends (means user is follower or following the specify user)
	 */
	List<String> friends(String userNo, List<String> userNoList);

	/**
	 * 删除用户关注列表，粉丝列表
	 *
	 * @param userNo user
	 */
	void deleteFollow(String userNo);

	/**
	 * 删除用户geo信息
	 *
	 * @param userNo user
	 */
	void deleteGeo(String userNo);
}
