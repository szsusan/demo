package com.quick.demo.exception;

public class UserNotFoundException extends AbstractCodeException {

	public UserNotFoundException(String message) {
		super(ErrorCode.userNotFound, message);
	}
}
