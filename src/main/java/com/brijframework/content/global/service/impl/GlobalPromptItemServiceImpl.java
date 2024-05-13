package com.brijframework.content.global.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.content.global.entities.EOGlobalPromptItem;
import com.brijframework.content.global.mapper.GlobalPromptItemMapper;
import com.brijframework.content.global.model.UIGlobalPromptItem;
import com.brijframework.content.global.repository.GlobalPromptItemRepository;
import com.brijframework.content.global.service.GlobalPromptItemService;

@Service
public class GlobalPromptItemServiceImpl  extends CrudServiceImpl<UIGlobalPromptItem, EOGlobalPromptItem, Long> implements GlobalPromptItemService {
	
	@Autowired
	private GlobalPromptItemRepository globalPromptRepository;
	
	@Autowired
	private GlobalPromptItemMapper globalPromptMapper;
	
	@Override
	public List<UIGlobalPromptItem> findAllByType(String typeId) {
		return globalPromptMapper.mapToDTO(globalPromptRepository.findOneByTypeId(typeId));
	}

	@Override
	public JpaRepository<EOGlobalPromptItem, Long> getRepository() {
		return globalPromptRepository;
	}

	@Override
	public GenericMapper<EOGlobalPromptItem, UIGlobalPromptItem> getMapper() {
		return globalPromptMapper;
	}
	
}
