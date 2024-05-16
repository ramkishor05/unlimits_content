package com.brijframework.content.device.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.QueryServiceImpl;

import com.brijframework.content.device.mapper.DeviceMainTagMapper;
import com.brijframework.content.device.model.UIDeviceMainTag;
import com.brijframework.content.device.service.DeviceMainTagService;
import com.brijframework.content.global.entities.EOGlobalTagGroup;
import com.brijframework.content.global.repository.GlobalTagGroupRepository;

@Service
public class DeviceMainTagServiceImpl extends QueryServiceImpl<UIDeviceMainTag, EOGlobalTagGroup, Long> implements DeviceMainTagService {
	
	@Autowired
	private GlobalTagGroupRepository globalTagGroupRepository;
	
	@Autowired
	private DeviceMainTagMapper deviceMainTagMapper;

	@Override
	public JpaRepository<EOGlobalTagGroup, Long> getRepository() {
		return globalTagGroupRepository;
	}

	@Override
	public GenericMapper<EOGlobalTagGroup, UIDeviceMainTag> getMapper() {
		return deviceMainTagMapper;
	}
	

}
