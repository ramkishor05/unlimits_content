package com.brijframework.content.device.mapper;

import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.device.model.UIDeviceMindSetItemModel;
import com.brijframework.content.global.entities.EOGlobalMindSetItem;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface DeviceMindSetItemMapper  extends GenericMapper<EOGlobalMindSetItem, UIDeviceMindSetItemModel>{

	@Override
	@Mapping(source = "mindSetLibarary.id", target = "mindSetLibararyId")
	UIDeviceMindSetItemModel mapToDTO(EOGlobalMindSetItem entityObject);
	
	@Override
	@Mapping(source = "mindSetLibararyId", target = "mindSetLibarary.id")
	EOGlobalMindSetItem mapToDAO(UIDeviceMindSetItemModel dtoObject);
}
