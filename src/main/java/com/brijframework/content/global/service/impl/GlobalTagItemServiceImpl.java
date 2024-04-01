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
	private GlobalTagItemRepository globalTagRepository;
	
	@Autowired
	private GlobalTagItemRequestMapper globalTagRequestMapper;
	
	@Autowired
	private GlobalTagItemResponseMapper globalTagResponseMapper;

	@Override
	public GlobalTagItemResponse saveTag(GlobalTagItemRequest uiGlobalTag) {
		EOGlobalTagItem eoGlobalTag = globalTagRequestMapper.mapToDAO(uiGlobalTag);
		eoGlobalTag=globalTagRepository.saveAndFlush(eoGlobalTag);
		return globalTagResponseMapper.mapToDTO(eoGlobalTag);
	}

	@Override
	public GlobalTagItemResponse getTag(Long id) {
	    return globalTagResponseMapper.mapToDTO(globalTagRepository.findById(id).orElse(null));
	}

	@Override
	public List<GlobalTagItemResponse> getTagList() {
		return globalTagResponseMapper.mapToDTO(globalTagRepository.findAll());
	}

	@Override
	public List<GlobalTagItemResponse> findAllByType(String typeId) {
		return globalTagResponseMapper.mapToDTO(globalTagRepository.findOneByTypeId(typeId));
	}
	
	@Override
	public boolean deleteTag(Long id) {
		globalTagRepository.deleteById(id);
		return true;
	}

}
