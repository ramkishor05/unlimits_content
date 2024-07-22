package com.brijframework.content.forgin.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.brijframework.content.resource.modal.UIResource;
import com.brijframework.content.resource.service.ResourceService;

//@FeignClient(name= "UNLIMITS-CONTENT" , url = "http://localhost:3333")
@Component
public class ResourceClient {

	@Autowired
	private ResourceService resourceService;

	//@PostMapping(value = "/resource/{type}/{name}")
	public UIResource add(UIResource uiResource) {
		return resourceService.add(uiResource, new HashMap<String, List<String>>());
	}
	
}