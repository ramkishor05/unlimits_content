package com.brijframework.content.global.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.content.global.entities.EOGlobalMindSetItem;
import com.brijframework.content.global.mapper.GlobalMindSetItemMapper;
import com.brijframework.content.global.model.UIGlobalMindSetItem;
import com.brijframework.content.global.repository.GlobalMindSetItemRepository;
import com.brijframework.content.global.service.GlobalMindSetItemService;
import com.brijframework.content.resource.modal.UIResource;
import com.brijframework.content.resource.service.ResourceService;

@Service
public class GlobalMindSetItemServiceImpl extends CrudServiceImpl<UIGlobalMindSetItem, EOGlobalMindSetItem, Long> implements GlobalMindSetItemService {
	/**
	 * 
	 */
	private static final String MIND_SET_VEDIOS = "mind_set_vedio";
	
	@Autowired
	private GlobalMindSetItemRepository globalMindSetRepository;
	
	@Autowired
	private GlobalMindSetItemMapper globalMindSetMapper;

	@Autowired
	private ResourceService resourceService;
	
	@Override
	public JpaRepository<EOGlobalMindSetItem, Long> getRepository() {
		return globalMindSetRepository;
	}

	@Override
	public GenericMapper<EOGlobalMindSetItem, UIGlobalMindSetItem> getMapper() {
		return globalMindSetMapper;
	}
	
	@Override
	protected void preAdd(UIGlobalMindSetItem data, EOGlobalMindSetItem entity, Map<String, List<String>> headers) {
		if(data.getContent()!=null) {
			UIResource uiResource=new UIResource();
			uiResource.setFileContent(data.getContent());
			uiResource.setFileName(data.getName());
			uiResource.setFolderName(MIND_SET_VEDIOS);
			resourceService.add(uiResource, new HashMap<String, List<String>>());
			entity.setUrl(uiResource.getFileUrl());
			entity.setContent(null);
		}
	}
	
	@Override
	protected void preUpdate(UIGlobalMindSetItem data, EOGlobalMindSetItem entity, Map<String, List<String>> headers) {
		if(data.getContent()!=null) {
			UIResource uiResource=new UIResource();
			uiResource.setFileContent(data.getContent());
			uiResource.setFileName(data.getName());
			uiResource.setFolderName(MIND_SET_VEDIOS);
			resourceService.add(uiResource, new HashMap<String, List<String>>());
			entity.setUrl(uiResource.getFileUrl());
			entity.setContent(null);
		}
	}
	
}
