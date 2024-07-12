package com.brijframework.content.resource.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.content.resource.entities.EOResource;
import com.brijframework.content.resource.mapper.ResourceMapper;
import com.brijframework.content.resource.modal.UIResource;
import com.brijframework.content.resource.repository.ResourceRepository;
import com.brijframework.content.util.ResourceUtil;

import jakarta.xml.bind.DatatypeConverter;

@Service
public class ResourceServiceImpl extends CrudServiceImpl<UIResource, EOResource, Long> implements ResourceService {
	
	@Autowired
	private ResourceUtil resourceUtil;
	
	@Autowired
	private ResourceMapper resourceMapper;
	
	@Autowired
	private ResourceRepository resourceRepository;

	@Override
	public JpaRepository<EOResource, Long> getRepository() {
		return resourceRepository;
	}

	@Override
	public GenericMapper<EOResource, UIResource> getMapper() {
		return resourceMapper;
	}
	
	@Override
	public Resource getResource(String type, String url) {
		return resourceUtil.getResource(type, url);
	}
	
	@Override
	public Resource getResource(String url) {
		return resourceUtil.getResource(url);
	}
	
	@Override
	public void preAdd(UIResource uiResource, EOResource eoResource, Map<String, List<String>> headers) {
		try {
			String[] strings = uiResource.getFileContent().split(",");
			byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
			Resource resource = resourceUtil.getResource();
			File resourceFile = resource.getFile();
			if(!resourceFile.exists()) {
				resourceFile.mkdirs();
			}
			File parentFile = new File(resourceFile, uiResource.getFolderName());
			if(!parentFile.exists()) {
				parentFile.mkdirs();
			}
			File dataFile = new File(parentFile, uiResource.getFileName());
			try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(dataFile))) {
				outputStream.write(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
