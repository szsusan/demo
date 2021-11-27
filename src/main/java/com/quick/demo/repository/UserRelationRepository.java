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
}
