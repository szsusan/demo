package com.quick.demo.config;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.format.DateTimeFormatter;

public class DateFormatValidator implements ConstraintValidator<DateTimeFormat, String> {

	private final ThreadLocal<DateTimeFormat> local = new ThreadLocal<>();

	@Override
	public void initialize(DateTimeFormat constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
		local.set(constraintAnnotation);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(local.get().format());
		try {
			formatter.parse(value);
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			local.remove();
		}
	}
}
