package com.brijframework.content.global.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.brijframework.content.global.entities.EOGlobalTagItem;
import com.brijframework.content.global.mapper.GlobalTagItemMapper;
import com.brijframework.content.global.model.UIGlobalTagItem;
import com.brijframework.content.global.repository.GlobalTagItemRepository;
import com.brijframework.content.global.service.GlobalTagItemService;
import com.brijframework.content.mapper.GenericMapper;
import com.brijframework.content.service.CrudServiceImpl;

@Service
public class GlobalTagItemServiceImpl extends CrudServiceImpl<UIGlobalTagItem, EOGlobalTagItem, Long> implements GlobalTagItemService {
	
	@Autowired
	private GlobalTagItemRepository globalTagItemRepository;
	
	@Autowired
	private GlobalTagItemMapper globalTagItemMapper;

	@Override
	public List<UIGlobalTagItem> findAllByType(String typeId) {
		return globalTagItemMapper.mapToDTO(globalTagItemRepository.findOneByTypeId(typeId));
	}

	@Override
	public JpaRepository<EOGlobalTagItem, Long> getRepository() {
		return globalTagItemRepository;
	}

	@Override
	public GenericMapper<EOGlobalTagItem, UIGlobalTagItem> getMapper() {
		return globalTagItemMapper;
	}
	
}
