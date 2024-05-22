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
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.resource.entities.EOResource;
import com.brijframework.content.resource.modal.UIResource;
import com.brijframework.content.resource.service.ResourceService;

@RestController
@RequestMapping("/resource")
public class ResourceController extends CrudController<UIResource, EOResource, Long>{

	@Autowired
	private ResourceService resourceService;
	
	@Override
	public CrudService<UIResource, EOResource, Long> getService() {
		return resourceService;
	}
	
	@GetMapping("/{type}/{url}")
	public Resource getImage(@PathVariable String type, @PathVariable String url) {
		return resourceService.getResource(type, url);
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