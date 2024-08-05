package com.brijframework.content.device.mapper;

import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.device.model.UIDeviceExampleLibarary;
import com.brijframework.content.global.entities.EOGlobalExampleLibarary;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface DeviceExampleLibararyMapper extends GenericMapper<EOGlobalExampleLibarary, UIDeviceExampleLibarary>{

}
