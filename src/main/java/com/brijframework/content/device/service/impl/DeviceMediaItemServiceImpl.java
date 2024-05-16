package com.brijframework.content.device.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.QueryServiceImpl;

import com.brijframework.content.device.mapper.DeviceMediaItemMapper;
import com.brijframework.content.device.model.UIDeviceMediaItem;
import com.brijframework.content.device.service.DeviceMediaItemService;
import com.brijframework.content.global.entities.EOGlobalMediaItem;
import com.brijframework.content.global.repository.GlobalMediaItemRepository;

@Service
public class DeviceMediaItemServiceImpl extends QueryServiceImpl<UIDeviceMediaItem, EOGlobalMediaItem, Long> implements DeviceMediaItemService {
	
	@Autowired
	private GlobalMediaItemRepository globalMediaRepository;
	
	@Autowired
	private DeviceMediaItemMapper deviceMediaItemMapper;
	
	@Override
	public JpaRepository<EOGlobalMediaItem, Long> getRepository() {
		return globalMediaRepository;
	}

	@Override
	public GenericMapper<EOGlobalMediaItem, UIDeviceMediaItem> getMapper() {
		return deviceMediaItemMapper;
	}

	@Override
	public List<UIDeviceMediaItem> search(String name) {
		return deviceMediaItemMapper.mapToDTO(globalMediaRepository.filter(name));
	}
	
}
