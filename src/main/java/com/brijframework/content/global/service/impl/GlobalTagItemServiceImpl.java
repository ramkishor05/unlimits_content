package com.brijframework.content.global.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframework.content.global.entities.EOGlobalTagItem;
import com.brijframework.content.global.mapper.GlobalTagItemRequestMapper;
import com.brijframework.content.global.mapper.GlobalTagItemResponseMapper;
import com.brijframework.content.global.repository.GlobalTagItemRepository;
import com.brijframework.content.global.rqrs.GlobalTagItemRequest;
import com.brijframework.content.global.rqrs.GlobalTagItemResponse;
import com.brijframework.content.global.service.GlobalTagItemService;

@Service
public class GlobalTagItemServiceImpl implements GlobalTagItemService {
	
	@Autowired
	private GlobalTagItemRepository globalTagItemRepository;
	
	@Autowired
	private GlobalTagItemRequestMapper globalTagItemRequestMapper;
	
	@Autowired
	private GlobalTagItemResponseMapper globalTagItemResponseMapper;

	@Override
	public GlobalTagItemResponse saveTagItem(GlobalTagItemRequest uiGlobalTag) {
		EOGlobalTagItem eoGlobalTag = globalTagItemRequestMapper.mapToDAO(uiGlobalTag);
		eoGlobalTag=globalTagItemRepository.saveAndFlush(eoGlobalTag);
		return globalTagItemResponseMapper.mapToDTO(eoGlobalTag);
	}

	@Override
	public GlobalTagItemResponse getTagItem(Long id) {
	    return globalTagItemResponseMapper.mapToDTO(globalTagItemRepository.findById(id).orElse(null));
	}

	@Override
	public List<GlobalTagItemResponse> getTagItemList() {
		return globalTagItemResponseMapper.mapToDTO(globalTagItemRepository.findAll());
	}

	@Override
	public List<GlobalTagItemResponse> findAllByType(String typeId) {
		return globalTagItemResponseMapper.mapToDTO(globalTagItemRepository.findOneByTypeId(typeId));
	}
	
	@Override
	public boolean deleteTagItem(Long id) {
		globalTagItemRepository.deleteById(id);
		return true;
	}

}
