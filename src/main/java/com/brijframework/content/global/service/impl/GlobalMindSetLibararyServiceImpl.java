package com.brijframework.content.global.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.forgin.model.ResourceFile;
import com.brijframework.content.forgin.repository.ResourceClient;
import com.brijframework.content.global.entities.EOGlobalMindSetLibarary;
import com.brijframework.content.global.mapper.GlobalMindSetLibararyMapper;
import com.brijframework.content.global.model.UIGlobalMindSetLibarary;
import com.brijframework.content.global.repository.GlobalMindSetLibararyRepository;
import com.brijframework.content.global.service.GlobalMindSetLibararyService;
import com.brijframework.content.resource.modal.UIResource;

@Service
public class GlobalMindSetLibararyServiceImpl implements GlobalMindSetLibararyService {

	private static final String MINDSET = "mindset";

	@Autowired
	private GlobalMindSetLibararyRepository globalMindSetLibararyRepository;

	@Autowired
	private GlobalMindSetLibararyMapper globalMindSetLibararyMapper;
	
	@Autowired
	private ResourceClient resourceClient;
	
	@Override
	public JpaRepository<EOGlobalMindSetLibarary, Long> getRepository() {
		return globalMindSetLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalMindSetLibarary, UIGlobalMindSetLibarary> getMapper() {
		return globalMindSetLibararyMapper;
	}
	
	@Override
	public void preAdd(UIGlobalMindSetLibarary data, Map<String, List<String>> headers) {
		UIResource resource = data.getContent();
		if(resource!=null) {
			resource.setFolderName(MINDSET);
			ResourceFile resourceFile =resourceClient.add(MINDSET, resource.getFileName(), resource.getFileContent());
			data.setUrl(resourceFile.getFileUrl());
		}
	}
	
	@Override
	public void preUpdate(UIGlobalMindSetLibarary data, Map<String, List<String>> headers) {
		UIResource resource = data.getContent();
		if(resource!=null) {
			resource.setFolderName(MINDSET);
			ResourceFile resourceFile = resourceClient.add(MINDSET, resource.getFileName(), resource.getFileContent());
			data.setUrl(resourceFile.getFileUrl());
		}
	}
	
}
