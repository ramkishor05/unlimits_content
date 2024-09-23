package com.brijframework.content.global.mapper;

import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.global.entities.EOGlobalMindSetLibarary;
import com.brijframework.content.global.model.UIGlobalMindSetLibarary;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface GlobalMindSetLibararyMapper  extends GenericMapper<EOGlobalMindSetLibarary, UIGlobalMindSetLibarary>{

	@Override
	@Mapping(target = "mindSetItems", ignore = true)
	EOGlobalMindSetLibarary mapToDAO(UIGlobalMindSetLibarary dtoObject);
	
	@Override
	@Mapping(target = "mindSetItems", ignore = true)
	UIGlobalMindSetLibarary mapToDTO(EOGlobalMindSetLibarary entityObject);
}
