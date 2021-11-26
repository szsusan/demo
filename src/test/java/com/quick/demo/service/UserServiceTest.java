package com.quick.demo.service;

import com.quick.demo.dto.UserDTO;
import com.quick.demo.entity.User;
import com.quick.demo.exception.UserNotFoundException;
import com.quick.demo.repository.UserRepository;
import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EasyMockRunner.class)
public class UserServiceTest {

	@TestSubject
	private UserService userService = new UserService();

	@Mock
	private UserRepository userRepository;

	@Test
	public void update() {
		User user = new User();
		EasyMock.expect(userRepository.getByUserId(EasyMock.anyString())).andReturn(null).andReturn(user);
		EasyMock.expect(userRepository.saveOrUpdate(user)).andReturn(user);
		EasyMock.replay(userRepository);

		UserDTO userDTO = new UserDTO();
		Assert.assertThrows("", UserNotFoundException.class, () -> {
			userService.update(userDTO);
		});

		Assert.assertSame(user, userService.update(userDTO));
		EasyMock.verify(userRepository);

	}

}