package com.quick.demo.repository;

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
}
