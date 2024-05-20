package com.brijframework.content.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brijframework.content.service.ResourceService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class ResourceController {

	@Autowired
	private ResourceService resourceService;

	@GetMapping("/images/{url}")
	public Resource getImage(@PathVariable String url) {
		return resourceService.getResource(url);
	}
	
	@PostMapping(value = "/images/{name}", consumes = {MediaType.TEXT_PLAIN_VALUE}, produces = {MediaType.TEXT_PLAIN_VALUE})
	public String addImage(@PathVariable String name,@RequestBody String content) {
		resourceService.addResource(name,content);
		return "/images/"+name;
	}

}