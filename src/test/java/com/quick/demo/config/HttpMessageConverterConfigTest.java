package com.quick.demo.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quick.demo.log.OperationLog;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class HttpMessageConverterConfigTest {

	@Test
	public void formatTime() throws JsonProcessingException {

		HttpMessageConverterConfig config = new HttpMessageConverterConfig();

		ObjectMapper objectMapper = config.formatTime();

		OperationLog operationLog = new OperationLog();
		operationLog.setResult(null);
		operationLog.setTimestamp(LocalDateTime.of(2021, 10, 1, 1, 1).toInstant(ZoneOffset.UTC).toEpochMilli());
		String value = objectMapper.writeValueAsString(operationLog);
		Assert.assertEquals("{\"timestamp\":\"2021-10-01 01:01\"}",value);
	}
}