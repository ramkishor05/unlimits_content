package com.brijframework.content.device.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.QueryServiceImpl;

import com.brijframework.content.device.mapper.DeviceReProgramLibararyMapper;
import com.brijframework.content.device.model.UIDeviceReProgramLibarary;
import com.brijframework.content.device.service.DeviceReProgramLibararyService;
import com.brijframework.content.global.entities.EOGlobalReProgramLibarary;
import com.brijframework.content.global.repository.GlobalReProgramLibararyRepository;

@Service
public class DeviceReprogramLibararyServiceImpl extends QueryServiceImpl<UIDeviceReProgramLibarary, EOGlobalReProgramLibarary, Long> implements DeviceReProgramLibararyService {
	
	@Autowired
	private GlobalReProgramLibararyRepository globalReProgramLibararyRepository;
	
	@Autowired
	private DeviceReProgramLibararyMapper deviceReProgramLibararyMapper;
	
	@Value("${openapi.service.url}")
	private String serverUrl;
	
	@Override
	public JpaRepository<EOGlobalReProgramLibarary, Long> getRepository() {
		return globalReProgramLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalReProgramLibarary, UIDeviceReProgramLibarary> getMapper() {
		return deviceReProgramLibararyMapper;
	}
	
	@Override
	public List<UIDeviceReProgramLibarary> postFetch(List<EOGlobalReProgramLibarary> findObjects) {
		List<UIDeviceReProgramLibarary> uiObjects = super.postFetch(findObjects);
		uiObjects.sort((op1,op2)->op1.getOrderSequence().compareTo(op2.getOrderSequence()));
		return uiObjects;
	}
	
	@Override
	public void postFetch(EOGlobalReProgramLibarary findObject, UIDeviceReProgramLibarary dtoObject) {
		if(StringUtils.isEmpty(dtoObject.getIdenNo())) {
			dtoObject.setIdenNo(findObject.getId()+"");
		}
		if(StringUtils.isNotEmpty(dtoObject.getUrl())) {
			dtoObject.setUrl(dtoObject.getUrl().startsWith("/")? serverUrl+""+dtoObject.getUrl() :  serverUrl+"/"+dtoObject.getUrl());
		}
	}
}