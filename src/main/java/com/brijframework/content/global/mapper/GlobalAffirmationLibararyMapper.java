package com.brijframework.content.global.mapper;
import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.global.entities.EOGlobalAffirmationLibarary;
import com.brijframework.content.global.model.UIGlobalAffirmationLibarary;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GlobalAffirmationLibararyMapper extends GenericMapper<EOGlobalAffirmationLibarary, UIGlobalAffirmationLibarary>{

	@Override
	EOGlobalAffirmationLibarary mapToDAO(UIGlobalAffirmationLibarary d) ;
}
