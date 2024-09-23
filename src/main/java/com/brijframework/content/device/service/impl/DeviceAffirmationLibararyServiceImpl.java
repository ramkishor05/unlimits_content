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
import com.brijframework.content.device.mapper.DeviceAffirmationLibararyMapper;
import com.brijframework.content.device.model.UIDeviceAffirmationModel;
import com.brijframework.content.device.service.DeviceAffirmationLibararyService;
import com.brijframework.content.global.entities.EOGlobalAffirmationLibarary;
import com.brijframework.content.global.repository.GlobalAffirmationLibararyRepository;

@Service
public class DeviceAffirmationLibararyServiceImpl extends QueryServiceImpl<UIDeviceAffirmationModel, EOGlobalAffirmationLibarary, Long> implements DeviceAffirmationLibararyService {
	private static final Logger LOGGER= LoggerFactory.getLogger(DeviceAffirmationLibararyServiceImpl.class);

	private static final String RECORD_STATE = "recordState";
	
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
	public GenericMapper<EOGlobalAffirmationLibarary, UIDeviceAffirmationModel> getMapper() {
		return deviceAffirmationLibararyMapper;
	}
	
	@Override
	public List<UIDeviceAffirmationModel> postFetch(List<EOGlobalAffirmationLibarary> findObjects, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		LOGGER.warn("postFetch");
		List<UIDeviceAffirmationModel> uiObjects = super.postFetch(findObjects, headers, filters, actions);
		uiObjects.sort((op1,op2)->op1.getOrderSequence().compareTo(op2.getOrderSequence()));
		return uiObjects;
	}
	
	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
	}
	
	@Override
	public void postFetch(EOGlobalAffirmationLibarary findObject, UIDeviceAffirmationModel dtoObject, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		if(StringUtils.isEmpty(dtoObject.getIdenNo())) {
			dtoObject.setIdenNo(findObject.getId()+"");
		}
		if(StringUtils.isNotEmpty(dtoObject.getMusicUrl())) {
			dtoObject.setMusicUrl(dtoObject.getMusicUrl().startsWith("/")? serverUrl+""+dtoObject.getMusicUrl() :  serverUrl+"/"+dtoObject.getMusicUrl());
		}
		
		if(StringUtils.isNotEmpty(dtoObject.getPosterUrl())) {
			dtoObject.setPosterUrl(dtoObject.getPosterUrl().startsWith("/")? serverUrl+""+dtoObject.getPosterUrl() :  serverUrl+"/"+dtoObject.getPosterUrl());
		}
	}
}
