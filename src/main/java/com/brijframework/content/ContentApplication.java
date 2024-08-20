package com.brijframework.content;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class ContentApplication {

	public static void main(String[] args) throws IOException {
		System.setProperty("spring.devtools.restart.enabled", "false");
		SpringApplication.run(ContentApplication.class, args);
	}

}
