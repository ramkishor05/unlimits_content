package com.brijframework.content.global.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.global.entities.EOGlobalCategoryGroup;
import com.brijframework.content.global.mapper.GlobalCategoryGroupMapper;
import com.brijframework.content.global.model.UIGlobalCategoryGroup;
import com.brijframework.content.global.repository.GlobalCategoryGroupRepository;
import com.brijframework.content.global.service.GlobalCategoryGroupService;

@Service
public class GlobalCategoryGroupServiceImpl implements GlobalCategoryGroupService {
	
	@Autowired
	private GlobalCategoryGroupRepository globalCategoryGroupRepository;
	
	@Autowired
	private GlobalCategoryGroupMapper globalCategoryGroupMapper;
	
	@Override
	public UIGlobalCategoryGroup saveCategoryGroup(UIGlobalCategoryGroup unitGroup) {
		EOGlobalCategoryGroup eoCategoryGroup=globalCategoryGroupMapper.mapToDAO(unitGroup);
		globalCategoryGroupRepository.save(eoCategoryGroup);
		return globalCategoryGroupMapper.mapToDTO(eoCategoryGroup);
	}

	@Override
	public UIGlobalCategoryGroup getCategoryGroup(long id) {
		return globalCategoryGroupMapper.mapToDTO(globalCategoryGroupRepository.getReferenceById(id));
	}

	@Override
	public List<UIGlobalCategoryGroup> getCategoryGroupList() {
		return globalCategoryGroupMapper.mapToDTO(globalCategoryGroupRepository.findAll());
	}
	
	@Override
	public List<UIGlobalCategoryGroup> getCategoryGroupList(RecordStatus dataStatus) {
		List<EOGlobalCategoryGroup> findAllByStatus = globalCategoryGroupRepository.getCategoryGroupListByStatus(dataStatus.getStatusIds());
		System.out.println("findAllByStatus="+findAllByStatus);
		return globalCategoryGroupMapper.mapToDTO(findAllByStatus);
	}

	@Override
	public List<UIGlobalCategoryGroup> getCategoryGroup(String typeId) {
		return globalCategoryGroupMapper.mapToDTO(globalCategoryGroupRepository.findAllByTypeId(typeId));
	}

	@Override
	public boolean deleteCategoryGroup(Long id) {
		EOGlobalCategoryGroup eoGlobalCategoryGroup = globalCategoryGroupRepository.getReferenceById(id);
		eoGlobalCategoryGroup.setRecordState(RecordStatus.DACTIVETED.getStatus());
		globalCategoryGroupRepository.save(eoGlobalCategoryGroup);
		return true;
	}

}
