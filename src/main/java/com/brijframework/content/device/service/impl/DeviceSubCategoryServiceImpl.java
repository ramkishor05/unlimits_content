package com.brijframework.content.device.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.QueryServiceImpl;

import com.brijframework.content.device.mapper.DeviceSubCategoryMapper;
import com.brijframework.content.device.model.UIDeviceSubCategory;
import com.brijframework.content.device.service.DeviceSubCategoryService;
import com.brijframework.content.global.entities.EOGlobalSubCategory;
import com.brijframework.content.global.repository.GlobalSubCategoryRepository;

@Service
public class DeviceSubCategoryServiceImpl extends QueryServiceImpl<UIDeviceSubCategory, EOGlobalSubCategory, Long> implements DeviceSubCategoryService {
	
	@Autowired
	private GlobalSubCategoryRepository globalCategoryItemRepository;
	
	@Autowired
	private DeviceSubCategoryMapper deviceSubCategoryMapper;
	
	@Value("${openapi.service.url}")
	private String serverUrl;

	@Override
	public JpaRepository<EOGlobalSubCategory, Long> getRepository() {
		return globalCategoryItemRepository;
	}

	@Override
	public GenericMapper<EOGlobalSubCategory, UIDeviceSubCategory> getMapper() {
		return deviceSubCategoryMapper;
	}

	@Override
	public List<UIDeviceSubCategory> findAllByMainCategoryId(Long mainCategoryId) {
		return postFetch(globalCategoryItemRepository.findAllByMainCategoryId(mainCategoryId));
	}
	
	@Override
	public void postFetch(EOGlobalSubCategory findObject, UIDeviceSubCategory dtoObject) {
		if(StringUtils.isNotEmpty(dtoObject.getLogoUrl())) {
			dtoObject.setLogoUrl(dtoObject.getLogoUrl().startsWith("/")? serverUrl+""+dtoObject.getLogoUrl() :  serverUrl+"/"+dtoObject.getLogoUrl());
		}
	}

	@Override
	public List<UIDeviceSubCategory> postFetch(List<EOGlobalSubCategory> findObjects) {
		List<UIDeviceSubCategory> uiObjects = super.postFetch(findObjects);
		uiObjects.sort((op1,op2)->op1.getOrderSequence().compareTo(op2.getOrderSequence()));
		return uiObjects;
	}
}
