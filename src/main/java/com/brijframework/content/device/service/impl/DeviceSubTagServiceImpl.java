package com.brijframework.content.device.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.QueryServiceImpl;

import com.brijframework.content.device.mapper.DeviceSubTagMapper;
import com.brijframework.content.device.model.UIDeviceSubTag;
import com.brijframework.content.device.service.DeviceSubTagService;
import com.brijframework.content.global.entities.EOGlobalTagItem;
import com.brijframework.content.global.repository.GlobalTagItemRepository;

@Service
public class DeviceSubTagServiceImpl extends QueryServiceImpl<UIDeviceSubTag, EOGlobalTagItem, Long> implements DeviceSubTagService {
	
	@Autowired
	private GlobalTagItemRepository globalTagItemRepository;
	
	@Autowired
	private DeviceSubTagMapper deviceSubTagMapper;

	@Override
	public JpaRepository<EOGlobalTagItem, Long> getRepository() {
		return globalTagItemRepository;
	}

	@Override
	public GenericMapper<EOGlobalTagItem, UIDeviceSubTag> getMapper() {
		return deviceSubTagMapper;
	}

	@Override
	public List<UIDeviceSubTag> findAllByTagId(Long tagId) {
		return deviceSubTagMapper.mapToDTO(globalTagItemRepository.findAllByGroupId(tagId));
	}
}
