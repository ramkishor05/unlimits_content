package com.brijframework.content.global.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.content.global.entities.EOGlobalMediaItem;
import com.brijframework.content.global.mapper.GlobalMediaItemMapper;
import com.brijframework.content.global.model.UIGlobalMediaItem;
import com.brijframework.content.global.repository.GlobalMediaItemRepository;
import com.brijframework.content.global.service.GlobalMediaItemService;
import com.brijframework.content.service.ResourceService;

@Service
public class GlobalMediaItemServiceImpl extends CrudServiceImpl<UIGlobalMediaItem, EOGlobalMediaItem, Long> implements GlobalMediaItemService {
	
	@Autowired
	private GlobalMediaItemRepository globalMediaRepository;
	
	@Autowired
	private GlobalMediaItemMapper globalMediaMapper;
	
	@Autowired
	private ResourceService resourceService;

	@Override
	public JpaRepository<EOGlobalMediaItem, Long> getRepository() {
		return globalMediaRepository;
	}

	@Override
	public GenericMapper<EOGlobalMediaItem, UIGlobalMediaItem> getMapper() {
		return globalMediaMapper;
	}
	
	@Override
	protected void preUpdate(UIGlobalMediaItem dtoObject) {
		Resource resource = resourceService.addResource(dtoObject.getName(),dtoObject.getContent());
		try {
			dtoObject.setLogoUrl(new File(resource.getURL().toURI()).getPath());
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
		System.out.println(resource);
	}
	
	@Override
	protected void postFind(EOGlobalMediaItem findObject, UIGlobalMediaItem dtoObject) {
		Resource resource = resourceService.addResource(findObject.getName(),findObject.getContent());
		System.out.println(resource);
		dtoObject.setLogoUrl(resource.toString());
		findObject.setLogoUrl(resource.toString());
		globalMediaRepository.save(findObject);
	}
	
}
