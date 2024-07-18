package com.brijframework.content.device.mapper;

import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.device.model.UIDeviceReProgramLibarary;
import com.brijframework.content.global.entities.EOGlobalReProgramLibarary;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface DeviceReProgramLibararyMapper  extends GenericMapper<EOGlobalReProgramLibarary, UIDeviceReProgramLibarary>{

}
