package com.brijframework.content.global.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.content.global.entities.EOGlobalPrompt;
import com.brijframework.content.global.mapper.GlobalPromptItemMapper;
import com.brijframework.content.global.model.UIGlobalPrompt;
import com.brijframework.content.global.repository.GlobalPromptRepository;
import com.brijframework.content.global.service.GlobalPromptService;

@Service
public class GlobalPromptServiceImpl  extends CrudServiceImpl<UIGlobalPrompt, EOGlobalPrompt, Long> implements GlobalPromptService {
	
	@Autowired
	private GlobalPromptRepository globalPromptRepository;
	
	@Autowired
	private GlobalPromptItemMapper globalPromptMapper;
	

	@Override
	public JpaRepository<EOGlobalPrompt, Long> getRepository() {
		return globalPromptRepository;
	}

	@Override
	public GenericMapper<EOGlobalPrompt, UIGlobalPrompt> getMapper() {
		return globalPromptMapper;
	}

	@Override
	public List<UIGlobalPrompt> findAllByType(String type, MultiValueMap<String, String> headers) {
		return getMapper().mapToDTO(globalPromptRepository.findAllByType(type));
	}
	
}
