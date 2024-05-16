package com.brijframework.content.device.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.QueryServiceImpl;

import com.brijframework.content.device.mapper.DeviceSubCategoryMapper;
import com.brijframework.content.device.model.UIDeviceSubCategory;
import com.brijframework.content.device.service.DeviceSubCategoryService;
import com.brijframework.content.global.entities.EOGlobalCategoryItem;
import com.brijframework.content.global.repository.GlobalCategoryItemRepository;

@Service
public class DeviceSubCategoryServiceImpl extends QueryServiceImpl<UIDeviceSubCategory, EOGlobalCategoryItem, Long> implements DeviceSubCategoryService {
	
	@Autowired
	private GlobalCategoryItemRepository globalCategoryItemRepository;
	
	@Autowired
	private DeviceSubCategoryMapper deviceSubCategoryMapper;

	@Override
	public JpaRepository<EOGlobalCategoryItem, Long> getRepository() {
		return globalCategoryItemRepository;
	}

	@Override
	public GenericMapper<EOGlobalCategoryItem, UIDeviceSubCategory> getMapper() {
		return deviceSubCategoryMapper;
	}

	@Override
	public List<UIDeviceSubCategory> findAllByCategoryId(Long categoryId) {
		return deviceSubCategoryMapper.mapToDTO(globalCategoryItemRepository.findAllByGroupId(categoryId));
	}

}
