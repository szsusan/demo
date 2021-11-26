package com.quick.demo.log;

public class ErrResponse {

	private String code;
	private String message;

	public ErrResponse(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}

