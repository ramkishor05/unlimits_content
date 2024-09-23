package com.brijframework.content.device.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.QueryServiceImpl;

import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.device.mapper.DeviceMainCategoryMapper;
import com.brijframework.content.device.model.UIDeviceMainCategoryModel;
import com.brijframework.content.device.service.DeviceMainCategoryService;
import com.brijframework.content.global.entities.EOGlobalMainCategory;
import com.brijframework.content.global.repository.GlobalMainCategoryRepository;

@Service
public class DeviceMainCategoryServiceImpl extends QueryServiceImpl<UIDeviceMainCategoryModel, EOGlobalMainCategory, Long>
		implements DeviceMainCategoryService {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(DeviceMainCategoryServiceImpl.class);

	private static final String RECORD_STATE = "recordState";
	
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
	public GenericMapper<EOGlobalMainCategory, UIDeviceMainCategoryModel> getMapper() {
		return globalCategoryGroupMapper;
	}

	@Override
	public List<UIDeviceMainCategoryModel> getCategoryGroupList(RecordStatus dataStatus, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		return postFetch(globalCategoryGroupRepository.getCategoryGroupListByStatus(dataStatus.getStatusIds()), headers, filters, actions);
	}
	
	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		LOGGER.info("preFetch");
		filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
	}
	
	@Override
	public void postFetch(EOGlobalMainCategory findObject, UIDeviceMainCategoryModel dtoObject, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		if(StringUtils.isNotEmpty(dtoObject.getLogoUrl())) {
			dtoObject.setLogoUrl(dtoObject.getLogoUrl().startsWith("/")? serverUrl+""+dtoObject.getLogoUrl() :  serverUrl+"/"+dtoObject.getLogoUrl());
		}
	}

	@Override
	public List<UIDeviceMainCategoryModel> postFetch(List<EOGlobalMainCategory> findObjects, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		List<UIDeviceMainCategoryModel> uiObjects = super.postFetch(findObjects, headers, filters, actions);
		uiObjects.sort((op1,op2)->op1.getOrderSequence().compareTo(op2.getOrderSequence()));
		return uiObjects;
	}
}
