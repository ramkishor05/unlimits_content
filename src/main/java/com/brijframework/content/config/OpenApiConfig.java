package com.brijframework.content.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

	/**
	 * 
	 */
	private  String openApiServer = "http://localhost:8080/content";
	private final String moduleName = "Auth";
	private final String apiVersion = "1.1";

	@Bean
	public OpenAPI customOpenAPI() {
		final String securitySchemeName = com.brijframework.content.constants.Constants.AUTHORIZATION;
		final String apiTitle = String.format("%s API", StringUtils.capitalize(moduleName));

		return new OpenAPI().addServersItem(new Server().url(openApiServer))
				.components(
						new Components().addSecuritySchemes(securitySchemeName,
								new SecurityScheme().name(securitySchemeName).type(SecurityScheme.Type.HTTP)
										.scheme("bearer").bearerFormat("JWT")))
				
				.info(new Info().title(apiTitle).version(apiVersion));
	}
}
