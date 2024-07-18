package com.brijframework.content.global.mapper;

import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.global.entities.EOGlobalReProgramLibarary;
import com.brijframework.content.global.model.UIGlobalReProgramLibarary;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GlobalReProgramLibararyMapper extends GenericMapper<EOGlobalReProgramLibarary, UIGlobalReProgramLibarary>{

	@Override
	EOGlobalReProgramLibarary mapToDAO(UIGlobalReProgramLibarary d);
}
