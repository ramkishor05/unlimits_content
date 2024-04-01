package com.brijframework.content.global.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframework.content.global.entities.EOGlobalCategoryItem;
import com.brijframework.content.global.mapper.GlobalCategoryItemRequestMapper;
import com.brijframework.content.global.mapper.GlobalCategoryItemResponseMapper;
import com.brijframework.content.global.repository.GlobalCategoryItemRepository;
import com.brijframework.content.global.rqrs.GlobalCategoryItemRequest;
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

	@Override
	public GlobalCategoryItemResponse saveCategory(GlobalCategoryItemRequest uiGlobalCategory) {
		EOGlobalCategoryItem eoGlobalCategory = globalCategoryRequestMapper.mapToDAO(uiGlobalCategory);
		eoGlobalCategory=globalCategoryRepository.saveAndFlush(eoGlobalCategory);
		return globalCategoryResponseMapper.mapToDTO(eoGlobalCategory);
	}

	@Override
	public GlobalCategoryItemResponse getCategory(Long id) {
	    return globalCategoryResponseMapper.mapToDTO(globalCategoryRepository.findById(id).orElse(null));
	}

	@Override
	public List<GlobalCategoryItemResponse> getCategoryList() {
		return globalCategoryResponseMapper.mapToDTO(globalCategoryRepository.findAll());
	}

	@Override
	public List<GlobalCategoryItemResponse> findAllByType(String typeId) {
		return globalCategoryResponseMapper.mapToDTO(globalCategoryRepository.findOneByTypeId(typeId));
	}
	
	@Override
	public boolean deleteCategory(Long id) {
		globalCategoryRepository.deleteById(id);
		return true;
	}

}
