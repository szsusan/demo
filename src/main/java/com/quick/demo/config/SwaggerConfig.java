package com.quick.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi
public class SwaggerConfig {

	@Bean
	public Docket petApi() {
		return new Docket(DocumentationType.OAS_30)
				.apiInfo(apiInfo())
				.groupName("userApi")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.quick.demo.controller.user"))
				.paths(PathSelectors.any())
				.build();

	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("user api").version("0.0.1-SNAPSHOT").build();
	}
}
