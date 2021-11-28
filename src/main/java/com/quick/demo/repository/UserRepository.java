package com.quick.demo.repository;

import com.quick.demo.entity.User;

public interface UserRepository {

	User getByUserNo(String userNo);

	User saveOrUpdate(User user);

	void deleteByUserNo(String userNo);

	User get(Long id);

}
