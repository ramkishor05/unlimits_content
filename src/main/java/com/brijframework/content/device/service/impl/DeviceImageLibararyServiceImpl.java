package com.brijframework.content.device.service.impl;

import java.util.ArrayList;
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
import com.brijframework.content.forgin.PexelMediaRepository;
import com.brijframework.content.global.entities.EOGlobalImageLibarary;
import com.brijframework.content.global.repository.GlobalImageLibararyRepository;

@Service
public class DeviceImageLibararyServiceImpl extends QueryServiceImpl<UIDeviceImageLibarary, EOGlobalImageLibarary, Long> implements DeviceImageLibararyService {
	
	@Autowired
	private GlobalImageLibararyRepository globalImageLibararyRepository;
	
	@Autowired
	private DeviceImageLibararyMapper deviceImageLibararyMapper;
	
	@Autowired
	private PexelMediaRepository pexelMediaRepository;
	
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
			if(eoGlobalImageLibararies.isEmpty()) {
				findByPexels(subCategoryId,tagLibararyId, name);
			}
			return postFetch(eoGlobalImageLibararies);
		} else {
			List<EOGlobalImageLibarary> eoGlobalImageLibararies = globalImageLibararyRepository.filter(subCategoryId, tagLibararyId, name);
			if(eoGlobalImageLibararies.isEmpty()) {
				findByPexels(subCategoryId,tagLibararyId, name);
			}
			return postFetch(eoGlobalImageLibararies);
		}
	}
	
	private List<UIDeviceImageLibarary> findByPexels(Long subCategoryId, Long tagLibararyId, String name) {
		List<UIDeviceImageLibarary>deviceImageLibararies=new ArrayList<UIDeviceImageLibarary>();
		/*
		 * pexelMediaRepository.getAllFiles(name).forEach(photo->{ UIDeviceImageLibarary
		 * deviceImageLibarary=new UIDeviceImageLibarary();
		 * deviceImageLibarary.setUrl(photo.getUrl());
		 * deviceImageLibararies.add(deviceImageLibarary); });
		 */
		return deviceImageLibararies;
	}

	@Override
	public List<UIDeviceImageLibarary> search(Long subCategoryId, Long tagLibararyId) {
		List<EOGlobalImageLibarary> eoGlobalImageLibararies = globalImageLibararyRepository.filter(subCategoryId, tagLibararyId);
		return postFetch(eoGlobalImageLibararies);
	}
	
	protected void postFetch(EOGlobalImageLibarary findObject, UIDeviceImageLibarary dtoObject) {
		if(StringUtils.isNotEmpty(dtoObject.getUrl())) {
			dtoObject.setUrl(dtoObject.getUrl().startsWith("/")? serverUrl+""+dtoObject.getUrl() :  serverUrl+"/"+dtoObject.getUrl());
		}
	}

}
