package com.quick.demo.log;

import com.quick.demo.exception.AbstractCodeException;
import com.quick.demo.exception.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestControllerAdvice
public class ExceptionLogHandler {

	private final Logger logger = LoggerFactory.getLogger(ExceptionLogHandler.class);

	private final Map<String, HttpStatus> statusMap;

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<ErrResponse> invalidField(MethodArgumentNotValidException e) {
		String field = Optional.of(e)
				.map(BindException::getBindingResult)
				.map(Errors::getFieldError)
				.map(FieldError::getField)
				.orElse(null);

		return new ResponseEntity<>(new ErrResponse(ErrorCode.invalidField, field),
				statusMap.get(ErrorCode.invalidField));

	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ErrResponse> exception(Exception e, HttpServletResponse response) {
		if (e instanceof AbstractCodeException) {
			String code = ((AbstractCodeException) e).getCode();
			return new ResponseEntity<>(new ErrResponse(code, e.getMessage()), statusMap.get(code));
		}
		logger.error("Failed.", e);
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<>(new ErrResponse(ErrorCode.system, e.getMessage()),
				statusMap.get(ErrorCode.system));

	}

	public ExceptionLogHandler() {
		statusMap = new HashMap<>();
		addStatus();
	}

	private void addStatus() {

		statusMap.put(ErrorCode.invalidField, HttpStatus.BAD_REQUEST);
		statusMap.put(ErrorCode.userNotFound, HttpStatus.NOT_FOUND);
		statusMap.put(ErrorCode.system, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
