package com.brijframework.content.global.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframework.content.global.entities.EOGlobalPromptItem;
import com.brijframework.content.global.mapper.GlobalPromptItemRequestMapper;
import com.brijframework.content.global.mapper.GlobalPromptItemResponseMapper;
import com.brijframework.content.global.repository.GlobalPromptItemRepository;
import com.brijframework.content.global.rqrs.GlobalPromptItemRequest;
import com.brijframework.content.global.rqrs.GlobalPromptItemResponse;
import com.brijframework.content.global.service.GlobalPromptItemService;

@Service
public class GlobalPromptItemServiceImpl implements GlobalPromptItemService {
	
	@Autowired
	private GlobalPromptItemRepository globalPromptRepository;
	
	@Autowired
	private GlobalPromptItemRequestMapper globalPromptRequestMapper;
	
	@Autowired
	private GlobalPromptItemResponseMapper globalPromptResponseMapper;

	@Override
	public GlobalPromptItemResponse savePrompt(GlobalPromptItemRequest uiGlobalPrompt) {
		EOGlobalPromptItem eoGlobalPrompt = globalPromptRequestMapper.mapToDAO(uiGlobalPrompt);
		eoGlobalPrompt=globalPromptRepository.saveAndFlush(eoGlobalPrompt);
		return globalPromptResponseMapper.mapToDTO(eoGlobalPrompt);
	}

	@Override
	public GlobalPromptItemResponse getPrompt(Long id) {
	    return globalPromptResponseMapper.mapToDTO(globalPromptRepository.findById(id).orElse(null));
	}

	@Override
	public List<GlobalPromptItemResponse> getPromptList() {
		return globalPromptResponseMapper.mapToDTO(globalPromptRepository.findAll());
	}

	@Override
	public List<GlobalPromptItemResponse> findAllByType(String typeId) {
		return globalPromptResponseMapper.mapToDTO(globalPromptRepository.findOneByTypeId(typeId));
	}
	
	@Override
	public boolean deletePrompt(Long id) {
		globalPromptRepository.deleteById(id);
		return true;
	}

}
