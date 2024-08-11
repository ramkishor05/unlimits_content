package com.brijframework.content.device.mapper;

import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.device.model.UIDeviceImageModel;
import com.brijframework.content.global.entities.EOGlobalImageLibarary;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface DeviceImageLibararyMapper  extends GenericMapper<EOGlobalImageLibarary, UIDeviceImageModel>{

	@Override
	@Mapping(source = "subCategory.id", target = "subCategoryId")
	UIDeviceImageModel mapToDTO(EOGlobalImageLibarary eoGlobalTagGroup);
	
	@Override
	@Mapping(target = "subCategory.id", source = "subCategoryId")
	EOGlobalImageLibarary mapToDAO(UIDeviceImageModel uiDeviceMainTag);
}
