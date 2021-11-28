package com.quick.demo.service;

import com.quick.demo.dto.UserDTO;
import com.quick.demo.entity.Address;
import com.quick.demo.entity.User;
import com.quick.demo.exception.UserNotFoundException;
import com.quick.demo.repository.UserRelationRepository;
import com.quick.demo.repository.UserRepository;
import com.quick.demo.util.Current;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRelationRepository relationRepository;

	public UserDTO getByUserNo(String userNo) {
		return toDTO(userRepository.getByUserNo(userNo));
	}

	public UserDTO save(UserDTO userDTO) {
		User user = new User();
		user.setUserNo(Current.uniqueId());
		user.setDob(userDTO.getDob());
		user.setName(userDTO.getName());
		user.setCreatedAt(Current.toMills());
		user.setAddress(userDTO.getAddress());
		addGeo(user);
		return toDTO(userRepository.saveOrUpdate(user));

	}

	@Transactional
	public UserDTO update(UserDTO userDTO) {

		User user = userRepository.getByUserNo(userDTO.getId());
		if (user == null) {
			throw new UserNotFoundException(userDTO.getId());
		}
		user.setName(userDTO.getName());
		user.setDob(userDTO.getDob());
		user.setAddress(userDTO.getAddress());
		addGeo(user);
		return toDTO(userRepository.saveOrUpdate(user));

	}

	public void deleteByUserNo(String userNo) {
		userRepository.deleteByUserNo(userNo);
		relationRepository.deleteFollow(userNo);
		relationRepository.deleteGeo(userNo);
	}

	public List<UserDTO> nearby(String userNo, int limit, double maxDistance) {
		int maxLimit = Math.min(limit, 100);
		double maxDis = Math.min(maxDistance, 3000.0);
		List<String> nearbyList = relationRepository.nearby(userNo, maxLimit, maxDis);
		List<String> friends = relationRepository.friends(userNo, nearbyList);
		return friends.stream().map(this::getByUserNo).filter(Objects::nonNull).collect(Collectors.toList());
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

	private void addGeo(User user) {
		String latitude = Optional.of(user).map(User::getAddress).map(Address::getLatitude).orElse(null);
		String longitude = Optional.of(user).map(User::getAddress).map(Address::getLongitude).orElse(null);
		if (StringUtils.isBlank(latitude) || StringUtils.isBlank(longitude)) {
			return;
		}
		relationRepository.addGeo(user.getUserNo(), user.getAddress());
	}
}
