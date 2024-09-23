package com.brijframework.content.device.mapper;

import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.device.model.UIDeviceReProgramItem;
import com.brijframework.content.global.entities.EOGlobalReProgramItem;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface DeviceReProgramItemMapper  extends GenericMapper<EOGlobalReProgramItem, UIDeviceReProgramItem>{

	@Mapping(source = "reProgramLibararyId", target = "reProgramLibarary.id")
	@Override
	EOGlobalReProgramItem mapToDAO(UIDeviceReProgramItem dtoObject);
	
	@Mapping(source = "reProgramLibarary.id", target = "reProgramLibararyId")
	@Override
	UIDeviceReProgramItem mapToDTO(EOGlobalReProgramItem entityObject);
}
