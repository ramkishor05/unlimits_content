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
import com.brijframework.content.global.entities.EOGlobalCategoryItem;
import com.brijframework.content.global.mapper.GlobalCategoryItemMapper;
import com.brijframework.content.global.model.UIGlobalCategoryItem;
import com.brijframework.content.global.repository.GlobalCategoryItemRepository;
import com.brijframework.content.global.service.GlobalCategoryItemService;
import com.brijframework.content.resource.service.ResourceService;

@Service
public class GlobalCategoryItemServiceImpl extends CrudServiceImpl<UIGlobalCategoryItem, EOGlobalCategoryItem, Long> implements GlobalCategoryItemService {
	
	@Autowired
	private GlobalCategoryItemRepository globalCategoryItemRepository;

	@Autowired
	private GlobalCategoryItemMapper globalCategoryItemMapper;
	
	@Autowired
	private ResourceService resourceService;


	@Override
	public JpaRepository<EOGlobalCategoryItem, Long> getRepository() {
		return globalCategoryItemRepository;
	}

	@Override
	public GenericMapper<EOGlobalCategoryItem, UIGlobalCategoryItem> getMapper() {
		return globalCategoryItemMapper;
	}

	@Override
	public Boolean delete(Long id) {
		Optional<EOGlobalCategoryItem> findById = globalCategoryItemRepository.findById(id);
		if(findById.isPresent()) {
			EOGlobalCategoryItem eoGlobalCategoryItem = findById.get();
			eoGlobalCategoryItem.setRecordState(DataStatus.DACTIVETED.getStatus());
			globalCategoryItemRepository.save(eoGlobalCategoryItem);
			return true;
		}
		return false;
	}

	@Override
	protected void preAdd(UIGlobalCategoryItem data, EOGlobalCategoryItem entity, Map<String, List<String>> headers) {
		if(data.getContent()!=null) {
			resourceService.add(data.getContent(), new HashMap<String, List<String>>());
			entity.setLogoUrl(data.getContent().getFileUrl());
		}
	}
	
	@Override
	protected void preUpdate(UIGlobalCategoryItem data, EOGlobalCategoryItem entity, Map<String, List<String>> headers) {
		if(data.getContent()!=null) {
			resourceService.add(data.getContent(), new HashMap<String, List<String>>());
			entity.setLogoUrl(data.getContent().getFileUrl());
		}
	}
}
