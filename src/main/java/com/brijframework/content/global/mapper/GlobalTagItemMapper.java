package com.brijframework.content.global.mapper;
import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.brijframework.content.global.entities.EOGlobalTagItem;
import com.brijframework.content.global.model.UIGlobalTagItem;
import com.brijframework.content.mapper.GenericMapper;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface GlobalTagItemMapper  extends GenericMapper<EOGlobalTagItem, UIGlobalTagItem>{

	@Override
	@Mapping(target = "globalTagGroup.id", source = "groupId")
	EOGlobalTagItem mapToDAO(UIGlobalTagItem uiGlobalTagItem);
	
	@Override
	@Mapping(target = "groupId", source = "globalTagGroup.id")
	UIGlobalTagItem mapToDTO(EOGlobalTagItem eoGlobalTagItem);
}
