package com.brijframework.content.device.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.QueryServiceImpl;

import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.device.mapper.DeviceMainCategoryMapper;
import com.brijframework.content.device.model.UIDeviceMainCategory;
import com.brijframework.content.device.service.DeviceMainCategoryService;
import com.brijframework.content.global.entities.EOGlobalCategoryGroup;
import com.brijframework.content.global.repository.GlobalCategoryGroupRepository;

@Service
public class DeviceMainCategoryServiceImpl extends QueryServiceImpl<UIDeviceMainCategory, EOGlobalCategoryGroup, Long>
		implements DeviceMainCategoryService {

	@Autowired
	private GlobalCategoryGroupRepository globalCategoryGroupRepository;

	@Autowired
	private DeviceMainCategoryMapper globalCategoryGroupMapper;

	@Override
	public JpaRepository<EOGlobalCategoryGroup, Long> getRepository() {
		return globalCategoryGroupRepository;
	}

	@Override
	public GenericMapper<EOGlobalCategoryGroup, UIDeviceMainCategory> getMapper() {
		return globalCategoryGroupMapper;
	}

	@Override
	public List<UIDeviceMainCategory> getCategoryGroupList(RecordStatus dataStatus) {
		return globalCategoryGroupMapper
				.mapToDTO(globalCategoryGroupRepository.getCategoryGroupListByStatus(dataStatus.getStatusIds()));
	}

}
