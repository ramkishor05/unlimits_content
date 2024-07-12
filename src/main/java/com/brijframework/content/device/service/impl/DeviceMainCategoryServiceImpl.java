package com.brijframework.content.device.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.QueryServiceImpl;

import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.device.mapper.DeviceMainCategoryMapper;
import com.brijframework.content.device.model.UIDeviceMainCategory;
import com.brijframework.content.device.service.DeviceMainCategoryService;
import com.brijframework.content.global.entities.EOGlobalMainCategory;
import com.brijframework.content.global.repository.GlobalMainCategoryRepository;

@Service
public class DeviceMainCategoryServiceImpl extends QueryServiceImpl<UIDeviceMainCategory, EOGlobalMainCategory, Long>
		implements DeviceMainCategoryService {
	
	@Value("${openapi.service.url}")
	private String serverUrl;

	@Autowired
	private GlobalMainCategoryRepository globalCategoryGroupRepository;

	@Autowired
	private DeviceMainCategoryMapper globalCategoryGroupMapper;

	@Override
	public JpaRepository<EOGlobalMainCategory, Long> getRepository() {
		return globalCategoryGroupRepository;
	}

	@Override
	public GenericMapper<EOGlobalMainCategory, UIDeviceMainCategory> getMapper() {
		return globalCategoryGroupMapper;
	}

	@Override
	public List<UIDeviceMainCategory> getCategoryGroupList(RecordStatus dataStatus) {
		return postFetch(globalCategoryGroupRepository.getCategoryGroupListByStatus(dataStatus.getStatusIds()));
	}

	@Override
	public void postFetch(EOGlobalMainCategory findObject, UIDeviceMainCategory dtoObject) {
		if(StringUtils.isNotEmpty(dtoObject.getLogoUrl())) {
			dtoObject.setLogoUrl(dtoObject.getLogoUrl().startsWith("/")? serverUrl+""+dtoObject.getLogoUrl() :  serverUrl+"/"+dtoObject.getLogoUrl());
		}
	}

	@Override
	public List<UIDeviceMainCategory> postFetch(List<EOGlobalMainCategory> findObjects) {
		List<UIDeviceMainCategory> uiObjects = super.postFetch(findObjects);
		uiObjects.sort((op1,op2)->op1.getOrderSequence().compareTo(op2.getOrderSequence()));
		return uiObjects;
	}
}
