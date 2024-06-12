package com.brijframework.content.device.mapper;

import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.device.model.UIDeviceSubTag;
import com.brijframework.content.global.entities.EOGlobalCategoryTag;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface DeviceSubTagMapper  extends GenericMapper<EOGlobalCategoryTag, UIDeviceSubTag>{

	@Override
	@Mapping(source = "globalCategoryItem.id", target = "subCategoryId")
    UIDeviceSubTag mapToDTO(EOGlobalCategoryTag eoGlobalTagGroup);
	
	@Override
	@Mapping(target = "globalCategoryItem.id", source = "subCategoryId")
    EOGlobalCategoryTag mapToDAO(UIDeviceSubTag uiDeviceMainTag);
}
