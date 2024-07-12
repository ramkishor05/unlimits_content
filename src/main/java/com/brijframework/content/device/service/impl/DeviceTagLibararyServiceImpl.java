package com.brijframework.content.device.service.impl;

import java.util.List;

import org.brijframework.util.text.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.QueryServiceImpl;

import com.brijframework.content.device.mapper.DeviceTagLibararyMapper;
import com.brijframework.content.device.model.UIDeviceTagLibarary;
import com.brijframework.content.device.service.DeviceTagLibararyService;
import com.brijframework.content.global.entities.EOGlobalTagLibarary;
import com.brijframework.content.global.repository.GlobalTagLibararyRepository;

@Service
public class DeviceTagLibararyServiceImpl extends QueryServiceImpl<UIDeviceTagLibarary, EOGlobalTagLibarary, Long> implements DeviceTagLibararyService {
	
	@Autowired
	private GlobalTagLibararyRepository globalTagLibararyRepository;
	
	@Autowired
	private DeviceTagLibararyMapper deviceTagLibararyMapper;

	@Override
	public JpaRepository<EOGlobalTagLibarary, Long> getRepository() {
		return globalTagLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalTagLibarary, UIDeviceTagLibarary> getMapper() {
		return deviceTagLibararyMapper;
	}

	@Override
	public List<UIDeviceTagLibarary> findAllBySubCategoryId(Long subCategoryId) {
		return postFetch(globalTagLibararyRepository.findAllBSubCategoryId(subCategoryId));
	}

	@Override
	public List<UIDeviceTagLibarary> search(Long subCategoryId, String name) {
		if(StringUtil.isNonEmpty(name)) {
			return postFetch(globalTagLibararyRepository.search(subCategoryId, name));
		} else {
			return postFetch(globalTagLibararyRepository.findAllBSubCategoryId(subCategoryId));
		}
	}

	@Override
	public List<UIDeviceTagLibarary> postFetch(List<EOGlobalTagLibarary> findObjects) {
		List<UIDeviceTagLibarary> uiObjects = super.postFetch(findObjects);
		uiObjects.sort((op1,op2)->op1.getOrderSequence().compareTo(op2.getOrderSequence()));
		return uiObjects;
	}
}
