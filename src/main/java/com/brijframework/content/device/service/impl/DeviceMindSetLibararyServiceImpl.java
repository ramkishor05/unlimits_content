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
import com.brijframework.content.device.mapper.DeviceMindSetLibararyMapper;
import com.brijframework.content.device.model.UIDeviceMindSetItemModel;
import com.brijframework.content.device.model.UIDeviceMindSetLibararyModel;
import com.brijframework.content.device.service.DeviceMindSetItemService;
import com.brijframework.content.device.service.DeviceMindSetLibararyService;
import com.brijframework.content.global.entities.EOGlobalMindSetLibarary;
import com.brijframework.content.global.repository.GlobalMindSetLibararyRepository;

@Service
public class DeviceMindSetLibararyServiceImpl extends QueryServiceImpl<UIDeviceMindSetLibararyModel, EOGlobalMindSetLibarary, Long> implements DeviceMindSetLibararyService {
	
	private static final String MIND_SET_ITEMS = "mindSetItems";

	private static final String MIND_SET_LIBARARY_ID = "mindSetLibararyId";

	private static final Logger LOGGER= LoggerFactory.getLogger(DeviceMindSetLibararyServiceImpl.class);

	private static final String RECORD_STATE = "recordState";
	
	@Autowired
	private GlobalMindSetLibararyRepository globalMindSetLibararyRepository;
	
	@Autowired
	private DeviceMindSetLibararyMapper deviceMindSetLibararyMapper;
	
	@Autowired
	private DeviceMindSetItemService deviceMindSetItemService;
	
	@Value("${openapi.service.url}")
	private String serverUrl;
	
	@Override
	public JpaRepository<EOGlobalMindSetLibarary, Long> getRepository() {
		return globalMindSetLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalMindSetLibarary, UIDeviceMindSetLibararyModel> getMapper() {
		return deviceMindSetLibararyMapper;
	}
	
	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		LOGGER.info("preFetch");
		filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
	}
	
	@Override
	public List<UIDeviceMindSetLibararyModel> postFetch(List<EOGlobalMindSetLibarary> findObjects, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		List<UIDeviceMindSetLibararyModel> uiObjects = super.postFetch(findObjects, headers, filters, actions);
		uiObjects.sort((op1,op2)->op1.getOrderSequence().compareTo(op2.getOrderSequence()));
		return uiObjects;
	}
	
	@Override
	public void postFetch(EOGlobalMindSetLibarary findObject, UIDeviceMindSetLibararyModel dtoObject, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		if(StringUtils.isEmpty(dtoObject.getIdenNo())) {
			dtoObject.setIdenNo(findObject.getId()+"");
		}
		if(StringUtils.isNotEmpty(dtoObject.getMusicUrl())) {
			dtoObject.setMusicUrl(dtoObject.getMusicUrl().startsWith("/")? serverUrl+""+dtoObject.getMusicUrl() :  serverUrl+"/"+dtoObject.getMusicUrl());
		}
		
		if(StringUtils.isNotEmpty(dtoObject.getPosterUrl())) {
			dtoObject.setPosterUrl(dtoObject.getPosterUrl().startsWith("/")? serverUrl+""+dtoObject.getPosterUrl() :  serverUrl+"/"+dtoObject.getPosterUrl());
		}
		
		if(!RestConstant.isExcludeKey(actions, MIND_SET_ITEMS) && RestConstant.isIncludeKey(actions, MIND_SET_ITEMS)) {
			filters.put(MIND_SET_LIBARARY_ID, findObject.getId());
			List<UIDeviceMindSetItemModel> mindSetItems = deviceMindSetItemService.findAll(headers, filters, actions);
			dtoObject.setMindSetItems(mindSetItems);
		}
	}
}
