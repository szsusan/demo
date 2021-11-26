package com.quick.demo.service;

import com.quick.demo.dto.UserDTO;
import com.quick.demo.entity.User;
import com.quick.demo.exception.UserNotFoundException;
import com.quick.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User getByUserId(String userId) {
		return userRepository.getByUserId(userId);
	}

	public User save(UserDTO userDTO) {
		User user = new User();
		user.setUserId(UUID.randomUUID().toString());
		user.setDob(userDTO.getDob());
		user.setName(userDTO.getName());
		user.setCreatedAt(Instant.now().toEpochMilli());
		return userRepository.saveOrUpdate(user);

	}

	@Transactional
	public User update(UserDTO userDTO) {

		User user = userRepository.getByUserId(userDTO.getId());
		if (user == null) {
			throw new UserNotFoundException(userDTO.getId());
		}

		user.setUserId(userDTO.getId());
		user.setName(user.getName());
		user.setAddress(user.getAddress());
		return userRepository.saveOrUpdate(user);

	}

	public void deleteByUserId(String id) {
		userRepository.deleteByUserId(id);
	}
}
