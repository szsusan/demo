package com.quick.demo.config;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.format.DateTimeFormatter;

public class DateFormatValidator implements ConstraintValidator<DateTimeFormat, String> {

	private String format;

	@Override
	public void initialize(DateTimeFormat constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
		this.format = constraintAnnotation.format();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		try {
			formatter.parse(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
