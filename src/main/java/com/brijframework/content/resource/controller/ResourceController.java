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

import com.brijframework.content.resource.modal.UIResourceModel;
import com.brijframework.content.resource.service.ResourceService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value="/resource")
public class ResourceController{

	@Autowired
	private ResourceService resourceService;
	
	@GetMapping(value="/{type}/{name}" , produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
	public Resource getTagsImage(@PathVariable String type, @PathVariable String name, HttpServletRequest request) {
		String test = request.getRequestURI();
		return resourceService.getResource(test.split("resource")[1]);
	}
	
	@GetMapping(value="/{type}/{name}/{param1}" , produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
	public Resource getTagsImage(@PathVariable String type, @PathVariable String name, @PathVariable String param1, HttpServletRequest request) {
		String test = request.getRequestURI();
		return resourceService.getResource(test.split("resource")[1]);
	}
	
	@GetMapping(value="/{type}/{name}/{param1}/{param2}" , produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
	public Resource getTagsImage(@PathVariable String type, @PathVariable String name, @PathVariable String param1, @PathVariable String param2, HttpServletRequest request) {
		String test = request.getRequestURI();
		return resourceService.getResource(test.split("resource")[1]);
	}
	
	@GetMapping(value="/{type}/{name}/{param1}/{param2}/{param3}" , produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
	public Resource getTagsImage(@PathVariable String type, @PathVariable String name, @PathVariable String param1 , @PathVariable String param2, @PathVariable String param3, HttpServletRequest request) {
		String test = request.getRequestURI();
		return resourceService.getResource(test.split("resource")[1]);
	}
	
	@GetMapping(value="/{type}/{name}/{param1}/{param2}/{param3}/{param4}" , produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
	public Resource getTagsImage(@PathVariable String type, @PathVariable String name, @PathVariable String param1 , @PathVariable String param2, @PathVariable String param3, @PathVariable String param4, HttpServletRequest request) {
		String test = request.getRequestURI();
		return resourceService.getResource(test.split("resource")[1]);
	}
	
	@PostMapping
	public UIResourceModel addImage(@RequestBody UIResourceModel uiResource) throws IOException {
		resourceService.add(uiResource, new HashMap<String, List<String>>(), new HashMap<String, Object>(), new HashMap<String, Object>());
		return uiResource;
	}

}