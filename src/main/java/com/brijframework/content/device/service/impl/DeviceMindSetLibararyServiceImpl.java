package com.brijframework.content.device.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.QueryServiceImpl;

import com.brijframework.content.device.mapper.DeviceMindSetLibararyMapper;
import com.brijframework.content.device.model.UIDeviceMindSetLibarary;
import com.brijframework.content.device.service.DeviceMindSetLibararyService;
import com.brijframework.content.global.entities.EOGlobalMindSetLibarary;
import com.brijframework.content.global.repository.GlobalMindSetLibararyRepository;

@Service
public class DeviceMindSetLibararyServiceImpl extends QueryServiceImpl<UIDeviceMindSetLibarary, EOGlobalMindSetLibarary, Long> implements DeviceMindSetLibararyService {
	
	@Autowired
	private GlobalMindSetLibararyRepository globalMindSetLibararyRepository;
	
	@Autowired
	private DeviceMindSetLibararyMapper deviceMindSetLibararyMapper;
	
	@Override
	public JpaRepository<EOGlobalMindSetLibarary, Long> getRepository() {
		return globalMindSetLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalMindSetLibarary, UIDeviceMindSetLibarary> getMapper() {
		return deviceMindSetLibararyMapper;
	}
	
	@Override
	public List<UIDeviceMindSetLibarary> postFetch(List<EOGlobalMindSetLibarary> findObjects) {
		List<UIDeviceMindSetLibarary> uiObjects = super.postFetch(findObjects);
		uiObjects.sort((op1,op2)->op1.getOrderSequence().compareTo(op2.getOrderSequence()));
		return uiObjects;
	}
}
