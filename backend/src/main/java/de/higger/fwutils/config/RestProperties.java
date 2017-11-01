package de.higger.fwutils.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "rest")
public class RestProperties {

	private List<String> frontendOrigins;

	public List<String> getFrontendOrigins() {
		return frontendOrigins;
	}

	public void setFrontendOrigins(final List<String> frontendOrigins) {
		this.frontendOrigins = frontendOrigins;
	}

}
