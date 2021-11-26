package com.quick.demo.controller.user;

import com.quick.demo.dto.UserDTO;
import com.quick.demo.entity.User;
import com.quick.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "get/{id}", method = RequestMethod.GET)
	public UserDTO get(@PathVariable(name = "id") String id) {
		return Optional.ofNullable(userService.getByUserId(id)).map(this::toDTO).orElseGet(UserDTO::new);
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public UserDTO create(@RequestBody @Validated UserDTO userDTO, HttpServletResponse response) {
		User user = userService.save(userDTO);
		response.setStatus(HttpStatus.CREATED.value());
		return toDTO(user);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable(name = "id") String id) {
		userService.deleteByUserId(id);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public UserDTO update(@RequestBody UserDTO userDTO) {
		return toDTO(userService.update(userDTO));
	}

	private UserDTO toDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getUserId());
		userDTO.setDob(user.getDob());
		userDTO.setName(user.getName());
		userDTO.setAddress(user.getAddress());
		userDTO.setCreateAt(user.getCreatedAt());
		return userDTO;
	}
}
