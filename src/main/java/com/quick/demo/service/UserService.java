package com.quick.demo.service;

import com.quick.demo.dto.UserDTO;
import com.quick.demo.entity.User;
import com.quick.demo.exception.UserNotFoundException;
import com.quick.demo.repository.UserRepository;
import com.quick.demo.util.Current;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public UserDTO getByUserNo(String userNo) {
		return toDTO(userRepository.getByUserNo(userNo));
	}

	public UserDTO save(UserDTO userDTO) {
		User user = new User();
		user.setUserNo(Current.uniqueId());
		user.setDob(userDTO.getDob());
		user.setName(userDTO.getName());
		user.setCreatedAt(Current.toMills());
		return toDTO(userRepository.saveOrUpdate(user));

	}

	@Transactional
	public UserDTO update(UserDTO userDTO) {

		User user = userRepository.getByUserNo(userDTO.getId());
		if (user == null) {
			throw new UserNotFoundException(userDTO.getId());
		}

		user.setUserNo(userDTO.getId());
		user.setName(user.getName());
		user.setAddress(user.getAddress());
		return toDTO(userRepository.saveOrUpdate(user));

	}

	public void deleteByUserNo(String userNo) {
		userRepository.deleteByUserNo(userNo);
	}

	public UserDTO getById(Long id) {
		return toDTO(userRepository.get(id));
	}

	private UserDTO toDTO(User user) {
		if (user == null) {
			return null;
		}
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getUserNo());
		userDTO.setDob(user.getDob());
		userDTO.setName(user.getName());
		userDTO.setAddress(user.getAddress());
		userDTO.setCreateAt(user.getCreatedAt());
		return userDTO;
	}
}
