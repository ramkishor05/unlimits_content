package com.brijframework.content.resource.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brijframework.content.resource.modal.UIResource;
import com.brijframework.content.resource.service.ResourceService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/resource")
public class ResourceController{

	@Autowired
	private ResourceService resourceService;
	
	
	@GetMapping("/{type}/{name}")
	public Resource getTagsImage(@PathVariable String type, @PathVariable String name, HttpServletRequest request) {
		String test = request.getRequestURI();
		System.out.println("test URL :"+test);
		return resourceService.getResource(test.split("resource")[1]);
	}
	
	@GetMapping("/{type}/{name}/{param1}")
	public Resource getTagsImage(@PathVariable String type, @PathVariable String name, @PathVariable String param1, HttpServletRequest request) {
		String test = request.getRequestURI();
		System.out.println("test URL :"+test);
		return resourceService.getResource(test.split("resource")[1]);
	}
	
	@GetMapping("/{type}/{name}/{param1}/{param2}")
	public Resource getTagsImage(@PathVariable String type, @PathVariable String name, @PathVariable String param1, @PathVariable String param2, HttpServletRequest request) {
		String test = request.getRequestURI();
		System.out.println("test URL :"+test);
		return resourceService.getResource(test.split("resource")[1]);
	}
	
	@GetMapping("/{type}/{name}/{param1}/{param2}/{param3}")
	public Resource getTagsImage(@PathVariable String type, @PathVariable String name, @PathVariable String param1 , @PathVariable String param2, @PathVariable String param3, HttpServletRequest request) {
		String test = request.getRequestURI();
		System.out.println("test URL :"+test);
		return resourceService.getResource(test.split("resource")[1]);
	}
	
	@GetMapping("/{type}/{name}/{param1}/{param2}/{param3}/{param4}")
	public Resource getTagsImage(@PathVariable String type, @PathVariable String name, @PathVariable String param1 , @PathVariable String param2, @PathVariable String param3, @PathVariable String param4, HttpServletRequest request) {
		String test = request.getRequestURI();
		System.out.println("test URL :"+test);
		return resourceService.getResource(test.split("resource")[1]);
	}
	
	@PostMapping(value = "/{type}/{name}", consumes = {MediaType.TEXT_PLAIN_VALUE}, produces = {MediaType.TEXT_PLAIN_VALUE})
	public String addImage(@PathVariable String type, @PathVariable String name,@RequestBody String content) throws IOException {
		UIResource uiResource=new UIResource();
		uiResource.setFileContent(content);
		uiResource.setFileName(name);
		uiResource.setFolderName(type);
		resourceService.add(uiResource, new HashMap<String, List<String>>());
		return uiResource.getFileUrl();
	}
	

}