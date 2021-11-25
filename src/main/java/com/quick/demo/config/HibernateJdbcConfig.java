package com.quick.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "hibernate.jdbc")
@Component
public class HibernateJdbcConfig {
	private String url;
	private String username;
	private String password;
	private String driver;
	private Map<String, Object> properties;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public void setProperties(Map<String, Object> properties) {
		Map<String, Object> map = new HashMap<>();
		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			map.put(entry.getKey().replaceAll("-", "."), entry.getValue());
		}
		this.properties = map;
	}
}

