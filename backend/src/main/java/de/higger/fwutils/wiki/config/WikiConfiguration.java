package de.higger.fwutils.wiki.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "wiki")
public class WikiConfiguration {

	private String url;

	private Categories categories;

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public Categories getCategories() {
		return categories;
	}

	public void setCategories(final Categories categories) {
		this.categories = categories;
	}

}
