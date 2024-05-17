package com.brijframework.content.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brijframework.content.service.ResourceService;

@RestController
public class ResourceController {

	@Autowired
	private ResourceService resourceService;

	@GetMapping("/images/{url}")
	public Resource getImage(@PathVariable String url) {
		return resourceService.getResource(url);
	}
	
	@PostMapping("/images/{name}/{content}")
	public Resource addImage(@PathVariable String name,@PathVariable String content) {
		return resourceService.addResource(name,content);
	}

}