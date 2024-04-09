package com.brijframework.content.global.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframework.content.constants.DataStatus;
import com.brijframework.content.global.entities.EOGlobalCategoryItem;
import com.brijframework.content.global.mapper.GlobalCategoryItemMapper;
import com.brijframework.content.global.mapper.GlobalCategoryItemRequestMapper;
import com.brijframework.content.global.mapper.GlobalCategoryItemResponseMapper;
import com.brijframework.content.global.model.UIGlobalCategoryItem;
import com.brijframework.content.global.repository.GlobalCategoryItemRepository;
import com.brijframework.content.global.rqrs.GlobalCategoryItemResponse;
import com.brijframework.content.global.service.GlobalCategoryItemService;

@Service
public class GlobalCategoryItemServiceImpl implements GlobalCategoryItemService {
	
	@Autowired
	private GlobalCategoryItemRepository globalCategoryRepository;
	
	@Autowired
	private GlobalCategoryItemRequestMapper globalCategoryRequestMapper;
	
	@Autowired
	private GlobalCategoryItemResponseMapper globalCategoryResponseMapper;
	
	@Autowired
	private GlobalCategoryItemMapper globalCategoryMapper;

	@Override
	public UIGlobalCategoryItem saveCategory(UIGlobalCategoryItem uiGlobalCategory) {
		EOGlobalCategoryItem eoGlobalCategory = globalCategoryMapper.mapToDAO(uiGlobalCategory);
		eoGlobalCategory.setRecordState(DataStatus.ACTIVETED.getStatus());
		eoGlobalCategory=globalCategoryRepository.saveAndFlush(eoGlobalCategory);
		return globalCategoryMapper.mapToDTO(eoGlobalCategory);
	}

	@Override
	public GlobalCategoryItemResponse getCategory(Long id) {
	    return globalCategoryResponseMapper.mapToDTO(globalCategoryRepository.findById(id).orElse(null));
	}

	@Override
	public List<UIGlobalCategoryItem> getCategoryList() {
		return globalCategoryMapper.mapToDTO(globalCategoryRepository.findAllByStatus(DataStatus.ACTIVETED.getStatusList()));
	}

	@Override
	public List<GlobalCategoryItemResponse> findAllByType(String typeId) {
		return globalCategoryResponseMapper.mapToDTO(globalCategoryRepository.findOneByTypeId(typeId));
	}
	
	@Override
	public boolean deleteCategory(Long id) {
		Optional<EOGlobalCategoryItem> findById = globalCategoryRepository.findById(id);
		if(findById.isPresent()) {
			EOGlobalCategoryItem eoGlobalCategoryItem = findById.get();
			eoGlobalCategoryItem.setRecordState(DataStatus.DACTIVETED.getStatus());
			globalCategoryRepository.save(eoGlobalCategoryItem);
			return true;
		}
		return false;
	}

}
