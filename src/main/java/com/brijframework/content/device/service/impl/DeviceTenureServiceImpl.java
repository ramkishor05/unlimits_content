package com.brijframework.content.device.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.QueryServiceImpl;

import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.device.mapper.DeviceTenureMapper;
import com.brijframework.content.device.model.UIDeviceTenure;
import com.brijframework.content.device.service.DeviceTenureService;
import com.brijframework.content.global.entities.EOGlobalTenure;
import com.brijframework.content.global.repository.GlobalTenureRepository;

@Service
public class DeviceTenureServiceImpl extends QueryServiceImpl<UIDeviceTenure, EOGlobalTenure, Long> implements DeviceTenureService {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(DeviceTenureServiceImpl.class);

	private static final String RECORD_STATE = "recordState";
	
	@Autowired
	private GlobalTenureRepository globalTenureRepository;
	
	@Autowired
	private DeviceTenureMapper deviceTenureMapper;
	
	@Override
	public JpaRepository<EOGlobalTenure, Long> getRepository() {
		return globalTenureRepository;
	}

	@Override
	public GenericMapper<EOGlobalTenure, UIDeviceTenure> getMapper() {
		return deviceTenureMapper;
	}
	
	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		LOGGER.warn("preFetch");
		filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
	}
	
	@Override
	public List<UIDeviceTenure> postFetch(List<EOGlobalTenure> findObjects, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		List<UIDeviceTenure> uiObjects = super.postFetch(findObjects, headers, filters, actions);
		uiObjects.sort((op1,op2)->op1.getOrderSequence().compareTo(op2.getOrderSequence()));
		return uiObjects;
	}

}
