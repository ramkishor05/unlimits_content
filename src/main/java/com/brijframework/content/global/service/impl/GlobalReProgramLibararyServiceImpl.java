/**
 * 
 */
package com.brijframework.content.global.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.content.forgin.model.ResourceFile;
import com.brijframework.content.forgin.repository.ResourceClient;
import com.brijframework.content.global.entities.EOGlobalReProgramLibarary;
import com.brijframework.content.global.mapper.GlobalReProgramLibararyMapper;
import com.brijframework.content.global.model.UIGlobalReProgramLibarary;
import com.brijframework.content.global.repository.GlobalReProgramLibararyRepository;
import com.brijframework.content.global.service.GlobalReProgramLibararyService;
import com.brijframework.content.resource.modal.UIResource;

/**
 * @author omnie
 */
@Service
public class GlobalReProgramLibararyServiceImpl extends CrudServiceImpl<UIGlobalReProgramLibarary, EOGlobalReProgramLibarary, Long>
		implements GlobalReProgramLibararyService {

	private static final String REPROGRAM = "reprogram";

	@Autowired
	private GlobalReProgramLibararyRepository globalReProgramRepository;

	@Autowired
	private GlobalReProgramLibararyMapper globalReProgramLibararyMapper;
	
	@Autowired
	private ResourceClient resourceClient;

	@Override
	public JpaRepository<EOGlobalReProgramLibarary, Long> getRepository() {
		return globalReProgramRepository;
	}

	@Override
	public GenericMapper<EOGlobalReProgramLibarary, UIGlobalReProgramLibarary> getMapper() {
		return globalReProgramLibararyMapper;
	}
	
	@Override
	public void preAdd(UIGlobalReProgramLibarary data, Map<String, List<String>> headers) {
		UIResource resource = data.getContent();
		if(resource!=null) {
			resource.setFolderName(REPROGRAM);
			ResourceFile resourceFile =resourceClient.add(REPROGRAM, resource.getFileName(), resource.getFileContent());
			data.setUrl(resourceFile.getFileUrl());
		}
	}
	
	@Override
	public void preUpdate(UIGlobalReProgramLibarary data, Map<String, List<String>> headers) {
		UIResource resource = data.getContent();
		if(resource!=null) {
			resource.setFolderName(REPROGRAM);
			ResourceFile resourceFile = resourceClient.add(REPROGRAM, resource.getFileName(), resource.getFileContent());
			data.setUrl(resourceFile.getFileUrl());
		}
	}


}
