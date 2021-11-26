package com.quick.demo.repository;

public interface UserRelationRepository {

	/**
	 * Specify user follows the other;
	 *
	 * @param uid      specify user
	 * @param followId the other user
	 */
	void follow(long uid, long followId);
}
