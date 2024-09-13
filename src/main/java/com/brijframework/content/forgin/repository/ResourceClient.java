package com.brijframework.content.forgin.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.brijframework.content.resource.modal.UIResourceModel;
import com.brijframework.content.resource.service.ResourceService;

//@FeignClient(name= "UNLIMITS-CONTENT" , url = "http://${server.eureka.host}:${server.eureka.port}/client")
@Component
public class ResourceClient {

	@Autowired
	private ResourceService resourceService;

	//@PostMapping(value = "/resource/{type}/{name}")
	public UIResourceModel add(UIResourceModel uiResource) {
		return resourceService.add(uiResource, new HashMap<String, List<String>>());
	}
	
}