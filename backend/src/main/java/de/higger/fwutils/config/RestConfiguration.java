package de.higger.fwutils.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class RestConfiguration {

	@Autowired
	private RestProperties restProperties;

	@Bean
	public CorsFilter corsFilter() {

		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedHeader("*");
		config.setAllowedOrigins(restProperties.getFrontendOrigins());
		config.setAllowedMethods(Arrays.asList("GET"));

		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

}
