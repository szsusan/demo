package com.quick.demo.repository;

import com.quick.demo.entity.User;

public interface UserRepository {

	User getByUserId(String userId);

	User saveOrUpdate(User user);

	void deleteByUserId(String userId);

}
