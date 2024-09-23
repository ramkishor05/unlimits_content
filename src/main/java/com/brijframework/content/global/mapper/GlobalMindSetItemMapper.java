package com.brijframework.content.global.mapper;

import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.global.entities.EOGlobalMindSetItem;
import com.brijframework.content.global.model.UIGlobalMindSetItem;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface GlobalMindSetItemMapper  extends GenericMapper<EOGlobalMindSetItem, UIGlobalMindSetItem>{

	@Mapping(source = "mindSetLibarary.id", target = "mindSetLibararyId")
	@Override
	UIGlobalMindSetItem mapToDTO(EOGlobalMindSetItem entityObject) ;
	
	@Mapping(source = "mindSetLibararyId", target = "mindSetLibarary.id")
	@Override
	EOGlobalMindSetItem mapToDAO(UIGlobalMindSetItem dtoObject);
}
