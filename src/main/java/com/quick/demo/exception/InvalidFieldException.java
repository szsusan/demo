package com.quick.demo.exception;

public class InvalidFieldException extends AbstractCodeException {

	public InvalidFieldException(String message) {
		super(ErrorCode.invalidField, message);
	}
}
