package com.brijframework.content.global.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.content.global.entities.EOGlobalCategoryTag;
import com.brijframework.content.global.mapper.GlobalTagItemMapper;
import com.brijframework.content.global.model.UIGlobalTagItem;
import com.brijframework.content.global.repository.GlobalTagItemRepository;
import com.brijframework.content.global.service.GlobalTagItemService;

@Service
public class GlobalTagItemServiceImpl extends CrudServiceImpl<UIGlobalTagItem, EOGlobalCategoryTag, Long> implements GlobalTagItemService {
	
	@Autowired
	private GlobalTagItemRepository globalTagItemRepository;
	
	@Autowired
	private GlobalTagItemMapper globalTagItemMapper;

	@Override
	public JpaRepository<EOGlobalCategoryTag, Long> getRepository() {
		return globalTagItemRepository;
	}

	@Override
	public GenericMapper<EOGlobalCategoryTag, UIGlobalTagItem> getMapper() {
		return globalTagItemMapper;
	}
	
}
