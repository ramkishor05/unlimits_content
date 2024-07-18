package com.brijframework.content.device.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.QueryServiceImpl;

import com.brijframework.content.device.mapper.DeviceAffirmationLibararyMapper;
import com.brijframework.content.device.model.UIDeviceAffirmationLibarary;
import com.brijframework.content.device.service.DeviceAffirmationLibararyService;
import com.brijframework.content.global.entities.EOGlobalAffirmationLibarary;
import com.brijframework.content.global.repository.GlobalAffirmationLibararyRepository;

@Service
public class DeviceAffirmationLibararyServiceImpl extends QueryServiceImpl<UIDeviceAffirmationLibarary, EOGlobalAffirmationLibarary, Long> implements DeviceAffirmationLibararyService {
	
	@Autowired
	private GlobalAffirmationLibararyRepository globalAffirmationLibararyRepository;
	
	@Autowired
	private DeviceAffirmationLibararyMapper deviceAffirmationLibararyMapper;
	
	@Value("${openapi.service.url}")
	private String serverUrl;
	
	@Override
	public JpaRepository<EOGlobalAffirmationLibarary, Long> getRepository() {
		return globalAffirmationLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalAffirmationLibarary, UIDeviceAffirmationLibarary> getMapper() {
		return deviceAffirmationLibararyMapper;
	}
	
	@Override
	public List<UIDeviceAffirmationLibarary> postFetch(List<EOGlobalAffirmationLibarary> findObjects) {
		List<UIDeviceAffirmationLibarary> uiObjects = super.postFetch(findObjects);
		uiObjects.sort((op1,op2)->op1.getOrderSequence().compareTo(op2.getOrderSequence()));
		return uiObjects;
	}
	
	@Override
	public void postFetch(EOGlobalAffirmationLibarary findObject, UIDeviceAffirmationLibarary dtoObject) {
		if(StringUtils.isEmpty(dtoObject.getIdenNo())) {
			dtoObject.setIdenNo(findObject.getId()+"");
		}
		if(StringUtils.isNotEmpty(dtoObject.getUrl())) {
			dtoObject.setUrl(dtoObject.getUrl().startsWith("/")? serverUrl+""+dtoObject.getUrl() :  serverUrl+"/"+dtoObject.getUrl());
		}
	}
}
