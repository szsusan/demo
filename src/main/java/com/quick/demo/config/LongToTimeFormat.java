package com.quick.demo.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class LongToTimeFormat extends JsonSerializer<Long> {

	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	@Override
	public void serialize(Long timestamp, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		if (0L == timestamp) {
			jsonGenerator.writeString("");
			return;
		}
		jsonGenerator.writeString(formatter.format(Instant.ofEpochMilli(timestamp).atOffset(ZoneOffset.UTC)));
	}
}
