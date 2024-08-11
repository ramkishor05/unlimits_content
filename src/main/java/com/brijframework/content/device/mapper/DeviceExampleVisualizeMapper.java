package com.brijframework.content.device.mapper;

import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.constants.ClientConstants;
import com.brijframework.content.device.model.UIDeviceExampleVisualize;
import com.brijframework.content.global.entities.EOGlobalExampleVisualize;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface DeviceExampleVisualizeMapper extends GenericMapper<EOGlobalExampleVisualize, UIDeviceExampleVisualize>{
	
	@Override
	@Mapping( source = "visualizeDate", target = "visualizeDate",  dateFormat = ClientConstants.UI_DATE_FORMAT_MMMM_DD_YYYY)
    UIDeviceExampleVisualize mapToDTO(EOGlobalExampleVisualize e);
	
	@Override
	@Mapping( source = "visualizeDate", target = "visualizeDate",  dateFormat = ClientConstants.UI_DATE_FORMAT_MMMM_DD_YYYY)
	EOGlobalExampleVisualize mapToDAO(UIDeviceExampleVisualize d);

}
