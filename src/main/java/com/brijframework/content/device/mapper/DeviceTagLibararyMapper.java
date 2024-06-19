package com.brijframework.content.device.mapper;

import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.device.model.UIDeviceTagLibarary;
import com.brijframework.content.global.entities.EOGlobalTagLibarary;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface DeviceTagLibararyMapper  extends GenericMapper<EOGlobalTagLibarary, UIDeviceTagLibarary>{

	@Override
	@Mapping(source = "subCategory.id", target = "subCategoryId")
    UIDeviceTagLibarary mapToDTO(EOGlobalTagLibarary eoGlobalTagGroup);
	
	@Override
	@Mapping(target = "subCategory.id", source = "subCategoryId")
    EOGlobalTagLibarary mapToDAO(UIDeviceTagLibarary uiDeviceMainTag);
}
