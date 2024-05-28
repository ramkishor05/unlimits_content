package com.brijframework.content.global.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.content.global.entities.EOGlobalPrompt;
import com.brijframework.content.global.mapper.GlobalPromptItemMapper;
import com.brijframework.content.global.model.UIGlobalPrompt;
import com.brijframework.content.global.repository.GlobalPromptItemRepository;
import com.brijframework.content.global.service.GlobalPromptItemService;

@Service
public class GlobalPromptItemServiceImpl  extends CrudServiceImpl<UIGlobalPrompt, EOGlobalPrompt, Long> implements GlobalPromptItemService {
	
	@Autowired
	private GlobalPromptItemRepository globalPromptRepository;
	
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
	
}
