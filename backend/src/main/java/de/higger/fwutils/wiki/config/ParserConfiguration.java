package de.higger.fwutils.wiki.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "parser")
public class ParserConfiguration {

	private String userAgent;

	private int timeout;

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(final String userAgent) {
		this.userAgent = userAgent;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(final int timeout) {
		this.timeout = timeout;
	}

}
