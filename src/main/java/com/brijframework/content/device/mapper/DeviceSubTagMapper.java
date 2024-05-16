package com.brijframework.content.device.mapper;

import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.device.model.UIDeviceSubTag;
import com.brijframework.content.global.entities.EOGlobalTagItem;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface DeviceSubTagMapper  extends GenericMapper<EOGlobalTagItem, UIDeviceSubTag>{

	@Override
	@Mapping(source = "globalTagGroup.id", target = "tagId")
    UIDeviceSubTag mapToDTO(EOGlobalTagItem eoGlobalTagGroup);
	
	@Override
	@Mapping(target = "globalTagGroup.id", source = "tagId")
    EOGlobalTagItem mapToDAO(UIDeviceSubTag uiDeviceMainTag);
}
