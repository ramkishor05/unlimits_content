package com.brijframework.content.device.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.QueryServiceImpl;

import com.brijframework.content.device.mapper.DeviceTenureMapper;
import com.brijframework.content.device.model.UIDeviceTenure;
import com.brijframework.content.device.service.DeviceTenureService;
import com.brijframework.content.global.entities.EOGlobalTenure;
import com.brijframework.content.global.repository.GlobalTenureRepository;

@Service
public class DeviceTenureServiceImpl extends QueryServiceImpl<UIDeviceTenure, EOGlobalTenure, Long> implements DeviceTenureService {
	
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

}
