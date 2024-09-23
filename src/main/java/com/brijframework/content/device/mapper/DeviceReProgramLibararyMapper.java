package com.brijframework.content.device.mapper;

import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.device.model.UIDeviceReProgramLibarary;
import com.brijframework.content.global.entities.EOGlobalReProgramLibarary;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface DeviceReProgramLibararyMapper  extends GenericMapper<EOGlobalReProgramLibarary, UIDeviceReProgramLibarary>{

	@Override
	@Mapping(target = "reProgramItems", ignore = true)
	UIDeviceReProgramLibarary mapToDTO(EOGlobalReProgramLibarary entityObject);
	
	@Override
	@Mapping(target = "reProgramItems", ignore = true)
	EOGlobalReProgramLibarary mapToDAO(UIDeviceReProgramLibarary dtoObject);
}
