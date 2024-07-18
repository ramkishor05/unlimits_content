/**
 * 
 */
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
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.client.mapper.GlobalReProgramGroupMapper;
import com.brijframework.client.mapper.GlobalReProgramItemMapper;
import com.brijframework.client.repository.GlobalReProgramGroupRepository;
import com.brijframework.client.repository.GlobalReProgramItemRepository;
import com.brijframework.client.unlimits.entities.EOGlobalReProgramGroup;
import com.brijframework.client.unlimits.entities.EOGlobalReProgramItem;
import com.brijframework.client.unlimits.model.UIGlobalReProgramGroup;
import com.brijframework.client.unlimits.model.UIGlobalReProgramItem;
import com.brijframework.content.client.entites.EOCustBusinessApp;
import com.brijframework.content.exceptions.UserNotFoundException;
import com.brijframework.content.global.service.GlobalReProgramService;
import com.brijframework.content.resource.modal.UIResource;
import com.brijframework.content.resource.repository.ResourceRepository;

/**
 * @author omnie
 */
@Service
public class GlobalReProgramServiceImpl extends CrudServiceImpl<UIGlobalReProgramGroup, EOGlobalReProgramGroup, Long>
		implements GlobalReProgramService {

	private static final String REPROGRAM = "reprogram";

	@Autowired
	private GlobalReProgramGroupRepository clientReProgramGroupRepository;

	@Autowired
	private GlobalReProgramGroupMapper clientReProgramGroupMapper;
	
	@Autowired
	private GlobalReProgramItemRepository clientReProgramItemRepository;

	@Autowired
	private GlobalReProgramItemMapper clientReProgramItemMapper;

	@Autowired
	private ResourceRepository resourceRepository;

	@Override
	public JpaRepository<EOGlobalReProgramGroup, Long> getRepository() {
		return clientReProgramGroupRepository;
	}

	@Override
	public GenericMapper<EOGlobalReProgramGroup, UIGlobalReProgramGroup> getMapper() {
		return clientReProgramGroupMapper;
	}
	
	@Override
	public void preAdd(UIGlobalReProgramGroup data, Map<String, List<String>> headers) {
		for(UIGlobalReProgramItem reProgramItem:   data.getReprograms()) {
			UIResource resource = reProgramItem.getResource();
			if(resource!=null) {
				resource.setFolderName(REPROGRAM);
				resourceRepository.add(REPROGRAM, resource.getFileName(), resource.getFileContent());
			}
		}
	}
	
	@Override
	public void preUpdate(UIGlobalReProgramGroup data, Map<String, List<String>> headers) {
		for(UIGlobalReProgramItem reProgramItem:   data.getReprograms()) {
			UIResource resource = reProgramItem.getResource();
			if(resource!=null) {
				resource.setFolderName(REPROGRAM);
				resourceRepository.add(REPROGRAM, resource.getFileName(), resource.getFileContent());
			}
		}
	}

	@Override
	public void preAdd(UIGlobalReProgramGroup data, EOGlobalReProgramGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}
	
	@Override
	public void preUpdate(UIGlobalReProgramGroup data, EOGlobalReProgramGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}
	
	@Override
	public void merge(UIGlobalReProgramGroup dtoObject, EOGlobalReProgramGroup entityObject,
			UIGlobalReProgramGroup updateDtoObject, EOGlobalReProgramGroup updateEntityObject,
			Map<String, List<String>> headers) {
		List<EOGlobalReProgramItem> reProgramItems = clientReProgramItemMapper.mapToDAO(dtoObject.getReprograms());
		reProgramItems.forEach(item->item.setGroup(updateEntityObject));
		List<EOGlobalReProgramItem> reProgramItemsReturn = clientReProgramItemRepository.saveAll(reProgramItems);
		updateDtoObject.setReprograms(clientReProgramItemMapper.mapToDTO(reProgramItemsReturn));
	}


	@Override
	public List<EOGlobalReProgramGroup> repositoryFindAll(Map<String, List<String>> headers, Map<String, String> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientReProgramGroupRepository.findAllByCustBusinessApp(eoCustBusinessApp, Sort.by("name").descending());
	}

	@Override
	public Page<EOGlobalReProgramGroup> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable, Map<String, String> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientReProgramGroupRepository.findAllByCustBusinessApp(eoCustBusinessApp, pageable);
	}

	@Override
	public List<EOGlobalReProgramGroup> repositoryFindAll(Map<String, List<String>> headers, Sort sort, Map<String, String> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientReProgramGroupRepository.findAllByCustBusinessApp(eoCustBusinessApp, sort);
	}

}
