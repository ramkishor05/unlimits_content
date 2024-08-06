package com.brijframework.content;

import java.util.List;

import org.brijframework.json.schema.factories.JsonSchemaDataFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.brijframework.content.global.entities.EOGlobalExampleLibarary;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class ContentApplication {

	public static void main(String[] args) {
		System.setProperty("spring.devtools.restart.enabled", "false");
		SpringApplication.run(ContentApplication.class, args);
		JsonSchemaDataFactory instance = JsonSchemaDataFactory.getInstance();

		List<EOGlobalExampleLibarary> eoGlobalExampleItemJson = instance.getAll(EOGlobalExampleLibarary.class);

		System.out.println("eoGlobalExampleItemJson="+eoGlobalExampleItemJson);
	}

}
