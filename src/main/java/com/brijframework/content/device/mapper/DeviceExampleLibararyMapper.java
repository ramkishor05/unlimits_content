package com.brijframework.content.device.mapper;

import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.device.model.UIDeviceExampleModel;
import com.brijframework.content.global.entities.EOGlobalExampleLibarary;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface DeviceExampleLibararyMapper extends GenericMapper<EOGlobalExampleLibarary, UIDeviceExampleModel>{

	@Override
	@Mapping(source = "subCategory.id", target = "subCategoryId")
	UIDeviceExampleModel mapToDTO(EOGlobalExampleLibarary eoGlobalTagGroup);
	
	@Override
	@Mapping(target = "subCategory.id", source = "subCategoryId")
	EOGlobalExampleLibarary mapToDAO(UIDeviceExampleModel uiDeviceMainTag);
}
