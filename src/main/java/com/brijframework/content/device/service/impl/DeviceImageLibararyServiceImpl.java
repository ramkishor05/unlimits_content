package com.brijframework.content.device.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${openapi.service.url}")
	private String serverUrl;
	
	@Override
	public JpaRepository<EOGlobalImageLibarary, Long> getRepository() {
		return globalImageLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalImageLibarary, UIDeviceImageLibarary> getMapper() {
		return deviceImageLibararyMapper;
	}
	
	@Override
	public List<UIDeviceImageLibarary> search(Long subCategoryId, Long tagLibararyId, String name) {
		if(StringUtils.isEmpty(name)) {
			List<EOGlobalImageLibarary> eoGlobalImageLibararies = globalImageLibararyRepository.filter(subCategoryId, tagLibararyId);
			List<UIDeviceImageLibarary> deviceImageLibararies = deviceImageLibararyMapper.mapToDTO(eoGlobalImageLibararies);
			preFetch(deviceImageLibararies);
			return deviceImageLibararies;
		} else {
			List<EOGlobalImageLibarary> eoGlobalImageLibararies = globalImageLibararyRepository.filter(subCategoryId, tagLibararyId, name);
			List<UIDeviceImageLibarary> deviceImageLibararies = deviceImageLibararyMapper.mapToDTO(eoGlobalImageLibararies);
			preFetch(deviceImageLibararies);
			return deviceImageLibararies;
		}
		
	}
	
	public void preFetch(List<UIDeviceImageLibarary> list) {
		list.forEach(image->{
			if(StringUtils.isNotEmpty(image.getUrl())) {
				image.setUrl(image.getUrl().startsWith("/")? serverUrl+""+image.getUrl() :  serverUrl+"/"+image.getUrl());
			}
		});
	}
	
}
