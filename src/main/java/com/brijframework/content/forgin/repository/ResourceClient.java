package com.brijframework.content.forgin.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.brijframework.content.forgin.model.ResourceFile;
import com.brijframework.content.resource.modal.UIResource;
import com.brijframework.content.resource.service.ResourceService;

//@FeignClient(name= "UNLIMITS-CONTENT" , url = "http://localhost:3333")
@Component
public class ResourceClient {

	@Autowired
	private ResourceService resourceService;

	//@PostMapping(value = "/resource/{type}/{name}")
	public ResourceFile add(@PathVariable String type, @PathVariable String name,@RequestBody String content) {
		UIResource uiResource=new UIResource();
		uiResource.setFileContent(content);
		uiResource.setFileName(name);
		uiResource.setFolderName(type);
		UIResource add = resourceService.add(uiResource, new HashMap<String, List<String>>());
		ResourceFile resourceFile=new ResourceFile();
		resourceFile.setFileContent(add.getFileContent());
		resourceFile.setFileName(add.getFileName());
		resourceFile.setFolderName(add.getFolderName());
		return resourceFile;
	}
	
}