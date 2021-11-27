package com.quick.demo.controller.user;

import com.quick.demo.dto.FollowUser;
import com.quick.demo.dto.UserDTO;
import com.quick.demo.exception.UserNotFoundException;
import com.quick.demo.repository.UserRelationRepository;
import com.quick.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRelationRepository relationRepository;

	@RequestMapping(value = "get/{id}", method = RequestMethod.GET)
	public ResponseEntity<UserDTO> get(@PathVariable(name = "id") String userNo) {
		return ok(validateUser(userNo));
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<UserDTO> create(@RequestBody @Validated UserDTO userDTO) {

		return ok(userService.save(userDTO));
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable(name = "id") String userNo) {
		userService.deleteByUserNo(userNo);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO) {
		return ok(userService.update(userDTO));
	}

	@RequestMapping(value = "/follow", method = RequestMethod.POST)
	public void follow(@RequestBody FollowUser followUser) {
		UserDTO user = validateUser(followUser.getUserNo());
		UserDTO follow = validateUser(followUser.getFollowNo());
		relationRepository.follow(user.getId(), follow.getId());
	}

	@RequestMapping(value = "/unfollow", method = RequestMethod.POST)
	public void unfollow(@RequestBody FollowUser followUser) {
		UserDTO user = validateUser(followUser.getUserNo());
		UserDTO follow = validateUser(followUser.getFollowNo());
		relationRepository.unfollow(user.getId(), follow.getId());
	}

	@RequestMapping(value = "/follower/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> follower(@PathVariable(name = "id") String userNo) {
		List<String> follower = relationRepository.queryFollower(userNo);
		List<UserDTO> collect =
				follower.stream().map(f -> userService.getByUserNo(f)).filter(Objects::nonNull).collect(Collectors.toList());
		return ok(collect);
	}

	@RequestMapping(value = "/following/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> following(@PathVariable(name = "id") String userNo) {
		List<String> follower = relationRepository.queryFollowing(userNo);
		List<UserDTO> collect =
				follower.stream().map(f -> userService.getByUserNo(f)).filter(Objects::nonNull).collect(Collectors.toList());
		return ok(collect);
	}

	@RequestMapping(value = "/nearby/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> nearby(@PathVariable(name = "id") String userNo) {
		return ok(userService.nearby(userNo, 100, 3000.0));
	}

	private <R> ResponseEntity<R> ok(R rtn) {
		return new ResponseEntity<>(rtn, HttpStatus.OK);
	}

	private UserDTO validateUser(String userId) {
		UserDTO user = userService.getByUserNo(userId);
		if (user == null) {
			throw new UserNotFoundException(userId);
		}
		return user;
	}

}
