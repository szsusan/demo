package com.quick.demo.log;

import com.quick.demo.exception.AbstractCodeException;
import com.quick.demo.exception.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionLogHandler {

	private final Logger logger = LoggerFactory.getLogger(ExceptionLogHandler.class);

	private final Map<String, Integer> statusMap;

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Object exception(Exception e, HttpServletResponse response) {
		if (e instanceof AbstractCodeException) {
			response.setStatus(statusMap.get(((AbstractCodeException) e).getCode()));
			return new ErrResponse(((AbstractCodeException) e).getCode(), e.getMessage());
		}
		logger.error("Failed.", e);
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ErrResponse(ErrorCode.system, "InternalError");

	}

	public ExceptionLogHandler() {
		statusMap = new HashMap<>();
		addStatus();
	}

	private void addStatus() {

		statusMap.put(ErrorCode.invalidField, HttpStatus.BAD_REQUEST.value());
		statusMap.put(ErrorCode.userNotFound, HttpStatus.NOT_FOUND.value());
		statusMap.put(ErrorCode.system, HttpStatus.INTERNAL_SERVER_ERROR.value());
	}
}
