package com.brijframework.content.global.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.brijframework.content.resource.modal.UIResource;
import com.brijframework.content.resource.service.ResourceService;

@Service
public class GlobalMainCategoryServiceImpl extends CrudServiceImpl<UIGlobalMainCategory, EOGlobalMainCategory, Long> implements GlobalMainCategoryService {
	
	@Autowired
	private GlobalMainCategoryRepository globalMainCategoryRepository;
	
	@Autowired
	private GlobalMainCategoryMapper globalMainCategoryMapper;

	@Value("${openapi.service.url}")
	private String serverUrl;
	
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
			UIResource add = resourceService.add(data.getContent(), new HashMap<String, List<String>>());
			entity.setLogoUrl(add.getFileUrl());
		}
	}
	
	@Override
	public void preUpdate(UIGlobalMainCategory data, EOGlobalMainCategory entity, Map<String, List<String>> headers) {
		if(data.getContent()!=null) {
			UIResource add = resourceService.add(data.getContent(), new HashMap<String, List<String>>());
			entity.setLogoUrl(add.getFileUrl());
		}
	}
	
	@Override
	public void postFetch(EOGlobalMainCategory findObject, UIGlobalMainCategory dtoObject) {
		if(StringUtils.isEmpty(dtoObject.getIdenNo())) {
			dtoObject.setIdenNo(findObject.getId()+"");
		}
		if(StringUtils.isNotEmpty(dtoObject.getLogoUrl())) {
			dtoObject.setLogoUrl(dtoObject.getLogoUrl().startsWith("/")? serverUrl+""+dtoObject.getLogoUrl() :  serverUrl+"/"+dtoObject.getLogoUrl());
		}
	}
}
