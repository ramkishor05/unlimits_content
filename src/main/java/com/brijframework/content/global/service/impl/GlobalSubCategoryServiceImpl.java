package com.brijframework.content.global.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.content.constants.DataStatus;
import com.brijframework.content.global.entities.EOGlobalSubCategory;
import com.brijframework.content.global.mapper.GlobalSubCategoryMapper;
import com.brijframework.content.global.model.UIGlobalSubCategory;
import com.brijframework.content.global.repository.GlobalSubCategoryRepository;
import com.brijframework.content.global.service.GlobalSubCategoryService;
import com.brijframework.content.resource.service.ResourceService;

@Service
public class GlobalSubCategoryServiceImpl extends CrudServiceImpl<UIGlobalSubCategory, EOGlobalSubCategory, Long> implements GlobalSubCategoryService {
	
	@Autowired
	private GlobalSubCategoryRepository globalSubCategoryRepository;

	@Autowired
	private GlobalSubCategoryMapper globalSubCategoryMapper;
	
	@Autowired
	private ResourceService resourceService;


	@Override
	public JpaRepository<EOGlobalSubCategory, Long> getRepository() {
		return globalSubCategoryRepository;
	}

	@Override
	public GenericMapper<EOGlobalSubCategory, UIGlobalSubCategory> getMapper() {
		return globalSubCategoryMapper;
	}

	@Override
	public Boolean delete(Long id) {
		Optional<EOGlobalSubCategory> findById = globalSubCategoryRepository.findById(id);
		if(findById.isPresent()) {
			EOGlobalSubCategory eoGlobalSubCategory = findById.get();
			eoGlobalSubCategory.setRecordState(DataStatus.DACTIVETED.getStatus());
			globalSubCategoryRepository.save(eoGlobalSubCategory);
			return true;
		}
		return false;
	}

	@Override
	public void preAdd(UIGlobalSubCategory data, EOGlobalSubCategory entity, Map<String, List<String>> headers) {
		if(data.getContent()!=null) {
			resourceService.add(data.getContent(), new HashMap<String, List<String>>());
			entity.setLogoUrl(data.getContent().getFileUrl());
		}
	}

	@Override
	public void preUpdate(UIGlobalSubCategory data, EOGlobalSubCategory entity, Map<String, List<String>> headers) {
		if(data.getContent()!=null) {
			resourceService.add(data.getContent(), new HashMap<String, List<String>>());
			entity.setLogoUrl(data.getContent().getFileUrl());
		}
	}
}
