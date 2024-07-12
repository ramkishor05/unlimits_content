package com.brijframework.content.global.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.global.entities.EOGlobalMainCategory;
import com.brijframework.content.global.mapper.GlobalMainCategoryMapper;
import com.brijframework.content.global.model.UIGlobalMainCategory;
import com.brijframework.content.global.repository.GlobalMainCategoryRepository;
import com.brijframework.content.global.service.GlobalMainCategoryService;
import com.brijframework.content.resource.service.ResourceService;

@Service
public class GlobalMainCategoryServiceImpl extends CrudServiceImpl<UIGlobalMainCategory, EOGlobalMainCategory, Long> implements GlobalMainCategoryService {
	
	@Autowired
	private GlobalMainCategoryRepository globalMainCategoryRepository;
	
	@Autowired
	private GlobalMainCategoryMapper globalMainCategoryMapper;
	
	@Autowired
	private ResourceService resourceService;

	@Override
	public JpaRepository<EOGlobalMainCategory, Long> getRepository() {
		return globalMainCategoryRepository;
	}

	@Override
	public GenericMapper<EOGlobalMainCategory, UIGlobalMainCategory> getMapper() {
		return globalMainCategoryMapper;
	}

	@Override
	public List<UIGlobalMainCategory> getMainCategoryList(RecordStatus dataStatus) {
		return globalMainCategoryMapper.mapToDTO(globalMainCategoryRepository.getCategoryGroupListByStatus(dataStatus.getStatusIds()));
	}
	
	@Override
	public void preAdd(UIGlobalMainCategory data, EOGlobalMainCategory entity, Map<String, List<String>> headers) {
		if(data.getContent()!=null) {
			resourceService.add(data.getContent(), new HashMap<String, List<String>>());
			entity.setLogoUrl(data.getContent().getFileUrl());
		}
	}
	
	@Override
	public void preUpdate(UIGlobalMainCategory data, EOGlobalMainCategory entity, Map<String, List<String>> headers) {
		if(data.getContent()!=null) {
			resourceService.add(data.getContent(), new HashMap<String, List<String>>());
			entity.setLogoUrl(data.getContent().getFileUrl());
		}
	}
	
}
