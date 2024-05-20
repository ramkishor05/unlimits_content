package com.brijframework.content.global.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.global.entities.EOGlobalCategoryGroup;
import com.brijframework.content.global.mapper.GlobalCategoryGroupMapper;
import com.brijframework.content.global.model.UIGlobalCategoryGroup;
import com.brijframework.content.global.repository.GlobalCategoryGroupRepository;
import com.brijframework.content.global.service.GlobalCategoryGroupService;

@Service
public class GlobalCategoryGroupServiceImpl extends CrudServiceImpl<UIGlobalCategoryGroup, EOGlobalCategoryGroup, Long> implements GlobalCategoryGroupService {
	
	@Autowired
	private GlobalCategoryGroupRepository globalCategoryGroupRepository;
	
	@Autowired
	private GlobalCategoryGroupMapper globalCategoryGroupMapper;

	@Override
	public JpaRepository<EOGlobalCategoryGroup, Long> getRepository() {
		return globalCategoryGroupRepository;
	}

	@Override
	public GenericMapper<EOGlobalCategoryGroup, UIGlobalCategoryGroup> getMapper() {
		return globalCategoryGroupMapper;
	}

	@Override
	public List<UIGlobalCategoryGroup> getCategoryGroupList(RecordStatus dataStatus) {
		return globalCategoryGroupMapper.mapToDTO(globalCategoryGroupRepository.getCategoryGroupListByStatus(dataStatus.getStatusIds()));
	}

}
