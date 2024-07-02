package com.brijframework.content.config;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.unlimits.rest.context.ApiTokenContext;

import com.fasterxml.jackson.core.StreamReadConstraints;

@Configuration
public class AppConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization",  ApiTokenContext.getContext().getCurrentToken());
            return execution.execute(request, body);
        });
		return restTemplate;
	}
	
	@Bean
	Jackson2ObjectMapperBuilderCustomizer customStreamReadConstraints() {
		return (builder) -> builder.postConfigurer((objectMapper) -> objectMapper.getFactory()
			.setStreamReadConstraints(StreamReadConstraints.builder().maxStringLength(200000000).build()));
	}
}
