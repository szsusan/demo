package com.quick.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "hibernate.session")
@Component
public class HibernateJdbcConfig {
	private String packageScan;
	private Map<String, Object> properties;

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		Map<String, Object> map = new HashMap<>();
		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			map.put(entry.getKey().replaceAll("-", "."), entry.getValue());
		}
		this.properties = map;
	}

	public String getPackageScan() {
		return packageScan;
	}

	public void setPackageScan(String packageScan) {
		this.packageScan = packageScan;
	}
}

