package com.quick.demo.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class HttpMessageConverterConfig implements WebMvcConfigurer {

	@Bean
	public ObjectMapper formatTime() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

		SimpleModule simpleModule = new SimpleModule();
		LongToTimeFormat longToTimeFormat = new LongToTimeFormat();
		simpleModule.addSerializer(Long.class, longToTimeFormat);
		simpleModule.addSerializer(Long.TYPE, longToTimeFormat);
		objectMapper.registerModule(simpleModule);
		return objectMapper;
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converters.add(0, converter);

		ArrayList<MediaType> supportedMediaTypes = new ArrayList<>();
		supportedMediaTypes.add(MediaType.APPLICATION_JSON);
		converter.setSupportedMediaTypes(supportedMediaTypes);

		converter.setObjectMapper(formatTime());
	}
}
