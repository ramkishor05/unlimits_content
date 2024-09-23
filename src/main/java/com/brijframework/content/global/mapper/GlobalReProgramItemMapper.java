package com.brijframework.content.global.mapper;

import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.global.entities.EOGlobalReProgramItem;
import com.brijframework.content.global.model.UIGlobalReProgramItem;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GlobalReProgramItemMapper extends GenericMapper<EOGlobalReProgramItem, UIGlobalReProgramItem>{

	@Mapping(source = "reProgramLibararyId", target = "reProgramLibarary.id")
	@Override
	EOGlobalReProgramItem mapToDAO(UIGlobalReProgramItem dtoObject);
	
	@Mapping(source = "reProgramLibarary.id", target = "reProgramLibararyId")
	@Override
    UIGlobalReProgramItem mapToDTO(EOGlobalReProgramItem entityObject);
}
