package com.brijframework.content.global.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.forgin.model.ResourceFile;
import com.brijframework.content.forgin.repository.ResourceClient;
import com.brijframework.content.global.entities.EOGlobalAffirmationLibarary;
import com.brijframework.content.global.mapper.GlobalAffirmationLibararyMapper;
import com.brijframework.content.global.model.UIGlobalAffirmationLibarary;
import com.brijframework.content.global.repository.GlobalAffirmationLibararyRepository;
import com.brijframework.content.global.service.GlobalAffirmationLibararyService;
import com.brijframework.content.resource.modal.UIResource;

@Service
public class GlobalAffirmationLibararyServiceImpl implements GlobalAffirmationLibararyService {

	private static final String AFFIRMATION = "affirmation";

	@Autowired
	private GlobalAffirmationLibararyRepository clientAffirmationLibararyRepository;
	
	@Autowired
	private GlobalAffirmationLibararyMapper clientAffirmationLibararyMapper;
	
	@Autowired
	private ResourceClient resourceClient;
	
	@Override
	public JpaRepository<EOGlobalAffirmationLibarary, Long> getRepository() {
		return clientAffirmationLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalAffirmationLibarary, UIGlobalAffirmationLibarary> getMapper() {
		return clientAffirmationLibararyMapper;
	}
	
	@Override
	public void preAdd(UIGlobalAffirmationLibarary data, Map<String, List<String>> headers) {
		UIResource resource = data.getContent();
		if(resource!=null) {
			resource.setFolderName(AFFIRMATION);
			ResourceFile resourceFile = resourceClient.add(AFFIRMATION, resource.getFileName(), resource.getFileContent());
			data.setUrl(resourceFile.getFileUrl());
		}
	}
	
	@Override
	public void preUpdate(UIGlobalAffirmationLibarary data, Map<String, List<String>> headers) {
		UIResource resource = data.getContent();
		if(resource!=null) {
			resource.setFolderName(AFFIRMATION);
			ResourceFile resourceFile =resourceClient.add(AFFIRMATION, resource.getFileName(), resource.getFileContent());
			data.setUrl(resourceFile.getFileUrl());
		}
	}

}
