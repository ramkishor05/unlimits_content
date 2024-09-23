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
import org.unlimits.rest.constants.RestConstant;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.QueryServiceImpl;

import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.device.mapper.DeviceReProgramLibararyMapper;
import com.brijframework.content.device.model.UIDeviceReProgramItem;
import com.brijframework.content.device.model.UIDeviceReProgramLibarary;
import com.brijframework.content.device.service.DeviceReProgramItemService;
import com.brijframework.content.device.service.DeviceReProgramLibararyService;
import com.brijframework.content.global.entities.EOGlobalReProgramLibarary;
import com.brijframework.content.global.repository.GlobalReProgramLibararyRepository;

@Service
public class DeviceReprogramLibararyServiceImpl extends QueryServiceImpl<UIDeviceReProgramLibarary, EOGlobalReProgramLibarary, Long> implements DeviceReProgramLibararyService {
	
	private static final String RE_PROGRAM_ITEMS = "reProgramItems";

	private static final String RE_PROGRAM_LIBARARY_ID = "reProgramLibararyId";

	private static final Logger LOGGER= LoggerFactory.getLogger(DeviceReprogramLibararyServiceImpl.class);

	private static final String RECORD_STATE = "recordState";
	
	@Autowired
	private GlobalReProgramLibararyRepository globalReProgramLibararyRepository;
	
	@Autowired
	private DeviceReProgramLibararyMapper deviceReProgramLibararyMapper;
	
	@Autowired
	private DeviceReProgramItemService deviceReProgramItemService;
	
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
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions ){
		LOGGER.warn("preFetch");
		filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
	}
	
	@Override
	public List<UIDeviceReProgramLibarary> postFetch(List<EOGlobalReProgramLibarary> findObjects, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		List<UIDeviceReProgramLibarary> uiObjects = super.postFetch(findObjects, headers, filters, actions);
		uiObjects.sort((op1,op2)->op1.getOrderSequence().compareTo(op2.getOrderSequence()));
		return uiObjects;
	}
	
	@Override
	public void postFetch(EOGlobalReProgramLibarary findObject, UIDeviceReProgramLibarary dtoObject, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		if(StringUtils.isEmpty(dtoObject.getIdenNo())) {
			dtoObject.setIdenNo(findObject.getId()+"");
		}
		if(StringUtils.isNotEmpty(dtoObject.getMusicUrl())) {
			dtoObject.setMusicUrl(dtoObject.getMusicUrl().startsWith("/")? serverUrl+""+dtoObject.getMusicUrl() :  serverUrl+"/"+dtoObject.getMusicUrl());
		}
		
		if(StringUtils.isNotEmpty(dtoObject.getPosterUrl())) {
			dtoObject.setPosterUrl(dtoObject.getPosterUrl().startsWith("/")? serverUrl+""+dtoObject.getPosterUrl() :  serverUrl+"/"+dtoObject.getPosterUrl());
		}
		if(!RestConstant.isExcludeKey(actions, RE_PROGRAM_ITEMS) && RestConstant.isIncludeKey(actions, RE_PROGRAM_ITEMS) ) {
			filters.put(RE_PROGRAM_LIBARARY_ID, findObject.getId());
			List<UIDeviceReProgramItem> reProgramItems = deviceReProgramItemService.findAll(headers, filters, actions);
			dtoObject.setReProgramItems(reProgramItems);
		}
	}
}
