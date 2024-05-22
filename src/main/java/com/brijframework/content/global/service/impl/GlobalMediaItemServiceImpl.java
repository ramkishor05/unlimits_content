package com.brijframework.content.global.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.content.global.entities.EOGlobalMediaItem;
import com.brijframework.content.global.mapper.GlobalMediaItemMapper;
import com.brijframework.content.global.model.UIGlobalMediaItem;
import com.brijframework.content.global.repository.GlobalMediaItemRepository;
import com.brijframework.content.global.service.GlobalMediaItemService;

@Service
public class GlobalMediaItemServiceImpl extends CrudServiceImpl<UIGlobalMediaItem, EOGlobalMediaItem, Long> implements GlobalMediaItemService {
	
	@Autowired
	private GlobalMediaItemRepository globalMediaRepository;
	
	@Autowired
	private GlobalMediaItemMapper globalMediaMapper;

	@Override
	public JpaRepository<EOGlobalMediaItem, Long> getRepository() {
		return globalMediaRepository;
	}

	@Override
	public GenericMapper<EOGlobalMediaItem, UIGlobalMediaItem> getMapper() {
		return globalMediaMapper;
	}
	
}
