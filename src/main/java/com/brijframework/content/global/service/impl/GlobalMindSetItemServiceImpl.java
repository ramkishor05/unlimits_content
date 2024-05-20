package com.brijframework.content.global.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.content.global.entities.EOGlobalMindSetItem;
import com.brijframework.content.global.mapper.GlobalMindSetItemMapper;
import com.brijframework.content.global.model.UIGlobalMindSetItem;
import com.brijframework.content.global.repository.GlobalMindSetItemRepository;
import com.brijframework.content.global.service.GlobalMindSetItemService;

@Service
public class GlobalMindSetItemServiceImpl extends CrudServiceImpl<UIGlobalMindSetItem, EOGlobalMindSetItem, Long> implements GlobalMindSetItemService {
	
	@Autowired
	private GlobalMindSetItemRepository globalMindSetRepository;
	
	@Autowired
	private GlobalMindSetItemMapper globalMindSetMapper;
	
	@Override
	public JpaRepository<EOGlobalMindSetItem, Long> getRepository() {
		return globalMindSetRepository;
	}

	@Override
	public GenericMapper<EOGlobalMindSetItem, UIGlobalMindSetItem> getMapper() {
		return globalMindSetMapper;
	}
	
}
