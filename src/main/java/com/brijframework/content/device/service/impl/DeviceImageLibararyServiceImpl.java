package com.brijframework.content.device.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.QueryServiceImpl;

import com.brijframework.content.device.mapper.DeviceImageLibararyMapper;
import com.brijframework.content.device.model.UIDeviceImageLibarary;
import com.brijframework.content.device.service.DeviceImageLibararyService;
import com.brijframework.content.global.entities.EOGlobalImageLibarary;
import com.brijframework.content.global.repository.GlobalImageLibararyRepository;

@Service
public class DeviceImageLibararyServiceImpl extends QueryServiceImpl<UIDeviceImageLibarary, EOGlobalImageLibarary, Long> implements DeviceImageLibararyService {
	
	@Autowired
	private GlobalImageLibararyRepository globalImageLibararyRepository;
	
	@Autowired
	private DeviceImageLibararyMapper deviceImageLibararyMapper;
	
	@Override
	public JpaRepository<EOGlobalImageLibarary, Long> getRepository() {
		return globalImageLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalImageLibarary, UIDeviceImageLibarary> getMapper() {
		return deviceImageLibararyMapper;
	}
	
	@Override
	public List<UIDeviceImageLibarary> search(Long subCategoryId, Long tagLibararyId) {
		return deviceImageLibararyMapper.mapToDTO(globalImageLibararyRepository.filter(subCategoryId, tagLibararyId));
	}

	@Override
	public List<UIDeviceImageLibarary> search(Long subCategoryId, Long tagLibararyId, String name) {
		return deviceImageLibararyMapper.mapToDTO(globalImageLibararyRepository.filter(subCategoryId, tagLibararyId, name));
	}
	
}
