package com.brijframework.content.global.mapper;

import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.constants.ClientConstants;
import com.brijframework.content.global.entities.EOGlobalExampleVisualize;
import com.brijframework.content.global.model.UIGlobalExampleVisualize;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface GlobalExampleVisualizeMapper extends GenericMapper<EOGlobalExampleVisualize, UIGlobalExampleVisualize>{
	
	@Override
	@Mapping( source = "visualizeDate", target = "visualizeDate",  dateFormat = ClientConstants.UI_DATE_FORMAT_MMMM_DD_YYYY)
    UIGlobalExampleVisualize mapToDTO(EOGlobalExampleVisualize e);
	
	@Override
	@Mapping( source = "visualizeDate", target = "visualizeDate",  dateFormat = ClientConstants.UI_DATE_FORMAT_MMMM_DD_YYYY)
    EOGlobalExampleVisualize mapToDAO(UIGlobalExampleVisualize d);

}
