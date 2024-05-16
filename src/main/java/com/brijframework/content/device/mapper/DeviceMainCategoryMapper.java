package com.brijframework.content.device.mapper;

import static com.brijframework.content.constants.Constants.APP_DEVICE_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.device.model.UIDeviceMainCategory;
import com.brijframework.content.global.entities.EOGlobalCategoryGroup;

@Mapper(componentModel = SPRING, implementationPackage = APP_DEVICE_PACKAGE_IMPL)
public interface DeviceMainCategoryMapper  extends GenericMapper<EOGlobalCategoryGroup, UIDeviceMainCategory>{

	@Override
    UIDeviceMainCategory mapToDTO(EOGlobalCategoryGroup eoGlobalCategoryGroup);
	
	@Override
    EOGlobalCategoryGroup mapToDAO(UIDeviceMainCategory uiDeviceMainCategory);
}
