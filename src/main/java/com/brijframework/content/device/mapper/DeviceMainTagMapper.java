package com.brijframework.content.device.mapper;

import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.device.model.UIDeviceMainTag;
import com.brijframework.content.global.entities.EOGlobalTagGroup;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface DeviceMainTagMapper  extends GenericMapper<EOGlobalTagGroup, UIDeviceMainTag>{

	@Override
	@Mapping(target  = "subCategoryId" , source = "globalCategoryItem.id" )
	UIDeviceMainTag mapToDTO(EOGlobalTagGroup e) ;
	
	@Override
	@Mapping(source  = "subCategoryId" , target = "globalCategoryItem.id" )
	EOGlobalTagGroup mapToDAO(UIDeviceMainTag d);
}
