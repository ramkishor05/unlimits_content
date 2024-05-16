package com.brijframework.content.device.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.QueryServiceImpl;

import com.brijframework.content.device.mapper.DeviceMindSetMapper;
import com.brijframework.content.device.model.UIDeviceMindSet;
import com.brijframework.content.device.service.DeviceMindSetItemService;
import com.brijframework.content.global.entities.EOGlobalMindSetItem;
import com.brijframework.content.global.repository.GlobalMindSetItemRepository;

@Service
public class DeviceMindSetItemServiceImpl extends QueryServiceImpl<UIDeviceMindSet, EOGlobalMindSetItem, Long> implements DeviceMindSetItemService {
	
	@Autowired
	private GlobalMindSetItemRepository globalMindSetRepository;
	
	@Autowired
	private DeviceMindSetMapper deviceMindSetMapper;
	
	@Override
	public JpaRepository<EOGlobalMindSetItem, Long> getRepository() {
		return globalMindSetRepository;
	}

	@Override
	public GenericMapper<EOGlobalMindSetItem, UIDeviceMindSet> getMapper() {
		return deviceMindSetMapper;
	}
	
}
