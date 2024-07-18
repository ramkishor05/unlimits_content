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

import com.brijframework.client.mapper.GlobalAffirmationGroupMapper;
import com.brijframework.client.mapper.GlobalAffirmationItemMapper;
import com.brijframework.client.repository.GlobalAffirmationGroupRepository;
import com.brijframework.client.repository.GlobalAffirmationItemRepository;
import com.brijframework.content.client.entites.EOCustBusinessApp;
import com.brijframework.content.exceptions.UserNotFoundException;
import com.brijframework.content.global.entities.EOGlobalAffirmationGroup;
import com.brijframework.content.global.entities.EOGlobalAffirmationItem;
import com.brijframework.content.global.model.UIGlobalAffirmationGroup;
import com.brijframework.content.global.model.UIGlobalAffirmationItem;
import com.brijframework.content.global.service.DeviceGlobalAffirmationService;
import com.brijframework.content.global.service.GlobalAffirmationService;
import com.brijframework.content.resource.modal.UIResource;
import com.brijframework.content.resource.repository.ResourceRepository;

@Service
public class GlobalAffirmationServiceImpl implements GlobalAffirmationService {

	private static final String AFFIRMATION = "affirmation";

	@Autowired
	private GlobalAffirmationGroupRepository clientAffirmationGroupRepository;
	
	@Autowired
	private GlobalAffirmationItemRepository clientAffirmationItemRepository;

	@Autowired
	private GlobalAffirmationGroupMapper clientAffirmationGroupMapper;
	
	@Autowired
	private GlobalAffirmationItemMapper clientAffirmationItemMapper;
	
	@Autowired
	private ResourceRepository resourceRepository;
	
	@Override
	public JpaRepository<EOGlobalAffirmationGroup, Long> getRepository() {
		return clientAffirmationGroupRepository;
	}

	@Override
	public GenericMapper<EOGlobalAffirmationGroup, UIGlobalAffirmationGroup> getMapper() {
		return clientAffirmationGroupMapper;
	}
	
	@Override
	public void preAdd(UIGlobalAffirmationGroup data, Map<String, List<String>> headers) {
		for(UIGlobalAffirmationItem AffirmationItem:   data.getAffirmations()) {
			UIResource resource = AffirmationItem.getResource();
			if(resource!=null) {
				resource.setFolderName(AFFIRMATION);
				resourceRepository.add(AFFIRMATION, resource.getFileName(), resource.getFileContent());
			}
		}
	}
	
	@Override
	public void preUpdate(UIGlobalAffirmationGroup data, Map<String, List<String>> headers) {
		for(UIGlobalAffirmationItem AffirmationItem:   data.getAffirmations()) {
			UIResource resource = AffirmationItem.getResource();
			if(resource!=null) {
				resource.setFolderName(AFFIRMATION);
				resourceRepository.add(AFFIRMATION, resource.getFileName(), resource.getFileContent());
			}
		}
	}

	@Override
	public void preAdd(UIGlobalAffirmationGroup data, EOGlobalAffirmationGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}
	
	@Override
	public void preUpdate(UIGlobalAffirmationGroup data, EOGlobalAffirmationGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}
	
	
	@Override
	public void merge(UIGlobalAffirmationGroup dtoObject, EOGlobalAffirmationGroup entityObject,
			UIGlobalAffirmationGroup updateDtoObject, EOGlobalAffirmationGroup updateEntityObject,
			Map<String, List<String>> headers) {
		List<EOGlobalAffirmationItem> affirmationItems = clientAffirmationItemMapper.mapToDAO(dtoObject.getAffirmations());
		affirmationItems.forEach(item->item.setGroup(updateEntityObject));
		List<EOGlobalAffirmationItem> affirmationItemsReturn = clientAffirmationItemRepository.saveAll(affirmationItems);
		updateDtoObject.setAffirmations(clientAffirmationItemMapper.mapToDTO(affirmationItemsReturn));
	}


	@Override
	public List<EOGlobalAffirmationGroup> repositoryFindAll(Map<String, List<String>> headers, Map<String, String> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientAffirmationGroupRepository.findAllByCustBusinessApp(eoCustBusinessApp, Sort.by("name").descending());
	}

	@Override
	public Page<EOGlobalAffirmationGroup> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable, Map<String, String> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientAffirmationGroupRepository.findAllByCustBusinessApp(eoCustBusinessApp, pageable);
	}

	@Override
	public List<EOGlobalAffirmationGroup> repositoryFindAll(Map<String, List<String>> headers, Sort sort, Map<String, String> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientAffirmationGroupRepository.findAllByCustBusinessApp(eoCustBusinessApp, sort);
	}

}
