package com.brijframework.content.global.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.context.ApiSecurityContext;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.global.mapper.GlobalMindSetGroupMapper;
import com.brijframework.global.mapper.GlobalMindSetItemMapper;
import com.brijframework.global.repository.GlobalMindSetGroupRepository;
import com.brijframework.global.repository.GlobalMindSetItemRepository;
import com.brijframework.content.global.entites.EOCustBusinessApp;
import com.brijframework.content.exceptions.UserNotFoundException;
import com.brijframework.content.global.entities.EOGlobalMindSetGroup;
import com.brijframework.content.global.entities.EOGlobalMindSetItem;
import com.brijframework.content.global.model.UIGlobalMindSetGroup;
import com.brijframework.content.global.model.UIGlobalMindSetItem;
import com.brijframework.content.resource.modal.UIResource;
import com.brijframework.content.resource.repository.ResourceRepository;

@Service
public class GlobalMindSetServiceImpl implements GlobalMindSetService {

	private static final String MINDSET = "mindset";

	@Autowired
	private GlobalMindSetGroupRepository globalMindSetGroupRepository;

	@Autowired
	private GlobalMindSetGroupMapper globalMindSetGroupMapper;
	
	@Autowired
	private GlobalMindSetItemRepository globalMindSetItemRepository;

	@Autowired
	private GlobalMindSetItemMapper globalMindSetItemMapper;
	
	@Autowired
	private ResourceRepository resourceRepository;
	
	@Override
	public JpaRepository<EOGlobalMindSetGroup, Long> getRepository() {
		return globalMindSetGroupRepository;
	}

	@Override
	public GenericMapper<EOGlobalMindSetGroup, UIGlobalMindSetGroup> getMapper() {
		return globalMindSetGroupMapper;
	}
	
	@Override
	public void preAdd(UIGlobalMindSetGroup data, Map<String, List<String>> headers) {
		for(UIGlobalMindSetItem MindSetItem:   data.getMindSets()) {
			UIResource resource = MindSetItem.getResource();
			if(resource!=null) {
				resource.setFolderName(MINDSET);
				resourceRepository.add(MINDSET, resource.getFileName(), resource.getFileContent());
			}
		}
	}
	
	@Override
	public void preUpdate(UIGlobalMindSetGroup data, Map<String, List<String>> headers) {
		for(UIGlobalMindSetItem MindSetItem:   data.getMindSets()) {
			UIResource resource = MindSetItem.getResource();
			if(resource!=null) {
				resource.setFolderName(MINDSET);
				resourceRepository.add(MINDSET, resource.getFileName(), resource.getFileContent());
			}
		}
	}
	
	@Override
	public void preAdd(UIGlobalMindSetGroup data, EOGlobalMindSetGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid global");
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}
	

	@Override
	public void preUpdate(UIGlobalMindSetGroup data, EOGlobalMindSetGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid global");
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}

	@Override
	public void merge(UIGlobalMindSetGroup dtoObject, EOGlobalMindSetGroup entityObject,
			UIGlobalMindSetGroup updateDtoObject, EOGlobalMindSetGroup updateEntityObject,
			Map<String, List<String>> headers) {
		List<EOGlobalMindSetItem> mindSetItems = globalMindSetItemMapper.mapToDAO(dtoObject.getMindSets());
		mindSetItems.forEach(item->item.setGroup(updateEntityObject));
		List<EOGlobalMindSetItem> mindSetItemsReturn = globalMindSetItemRepository.saveAll(mindSetItems);
		updateDtoObject.setMindSets(globalMindSetItemMapper.mapToDTO(mindSetItemsReturn));
	}

	@Override
	public List<EOGlobalMindSetGroup> repositoryFindAll(Map<String, List<String>> headers, Map<String, String> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid global");
		}
		return globalMindSetGroupRepository.findAllByCustBusinessApp(eoCustBusinessApp, Sort.by("name").descending());
	}

	@Override
	public Page<EOGlobalMindSetGroup> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable, Map<String, String> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid global");
		}
		return globalMindSetGroupRepository.findAllByCustBusinessApp(eoCustBusinessApp, pageable);
	}

	@Override
	public List<EOGlobalMindSetGroup> repositoryFindAll(Map<String, List<String>> headers, Sort sort, Map<String, String> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid global");
		}
		return globalMindSetGroupRepository.findAllByCustBusinessApp(eoCustBusinessApp, sort);
	}

}
