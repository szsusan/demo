package com.quick.demo.exception;

public abstract class AbstractCodeException extends RuntimeException {

	private String code;

	public AbstractCodeException(String code) {
		super();
		this.code = code;
	}

	public AbstractCodeException(String code, String message) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
