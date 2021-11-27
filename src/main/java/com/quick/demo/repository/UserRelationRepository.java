package com.quick.demo.repository;

import java.util.List;

public interface UserRelationRepository {

	/**
	 * Specify user follows the other;
	 *
	 * @param uid      specify user
	 * @param followId the other user
	 */
	void follow(long uid, long followId);

	/**
	 * Specify user do not follows the other any more;
	 *
	 * @param uid      specify user
	 * @param followId the other user
	 */
	void unfollow(long uid, long followId);

	/**
	 * @param uid user id
	 * @return user's follower
	 */
	List<Long> queryFollower(long uid);

	/**
	 * @param uid user id
	 * @return user's following
	 */
	List<Long> queryFollowing(Long uid);
}
