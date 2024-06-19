package com.brijframework.content.global.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.content.global.entities.EOGlobalMindSetLibarary;
import com.brijframework.content.global.mapper.GlobalMindSetLibararyMapper;
import com.brijframework.content.global.model.UIGlobalMindSetLibarary;
import com.brijframework.content.global.repository.GlobalMindSetLibararyRepository;
import com.brijframework.content.global.service.GlobalMindSetLibararyService;
import com.brijframework.content.resource.service.ResourceService;

@Service
public class GlobalMindSetLibararyServiceImpl extends CrudServiceImpl<UIGlobalMindSetLibarary, EOGlobalMindSetLibarary, Long> implements GlobalMindSetLibararyService {
	/**
	 * 
	 */
	private static final String MIND_SET_VEDIOS = "mind_set_vedio";
	
	@Autowired
	private GlobalMindSetLibararyRepository globalMindSetLibararyRepository;
	
	@Autowired
	private GlobalMindSetLibararyMapper globalMindSetLibararyMapper;

	@Autowired
	private ResourceService resourceService;
	
	@Override
	public JpaRepository<EOGlobalMindSetLibarary, Long> getRepository() {
		return globalMindSetLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalMindSetLibarary, UIGlobalMindSetLibarary> getMapper() {
		return globalMindSetLibararyMapper;
	}
	
	@Override
	protected void preAdd(UIGlobalMindSetLibarary data, EOGlobalMindSetLibarary entity, Map<String, List<String>> headers) {
		if(data.getContent()!=null) {
			data.getContent().setFolderName(MIND_SET_VEDIOS);
			resourceService.add(data.getContent(), new HashMap<String, List<String>>());
			entity.setUrl(data.getContent().getFileUrl());
		}
	}
	
	@Override
	protected void preUpdate(UIGlobalMindSetLibarary data, EOGlobalMindSetLibarary entity, Map<String, List<String>> headers) {
		if(data.getContent()!=null) {
			data.getContent().setFolderName(MIND_SET_VEDIOS);
			resourceService.add(data.getContent(), new HashMap<String, List<String>>());
			entity.setUrl(data.getContent().getFileUrl());
		}
	}
	
}
