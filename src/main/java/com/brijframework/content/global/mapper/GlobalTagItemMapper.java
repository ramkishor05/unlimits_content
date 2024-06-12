package com.brijframework.content.global.mapper;
import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.global.entities.EOGlobalCategoryTag;
import com.brijframework.content.global.model.UIGlobalTagItem;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface GlobalTagItemMapper  extends GenericMapper<EOGlobalCategoryTag, UIGlobalTagItem>{

	@Override
	@Mapping(target = "globalCategoryItem.id", source = "groupId")
	EOGlobalCategoryTag mapToDAO(UIGlobalTagItem uiGlobalTagItem);
	
	@Override
	@Mapping(target = "groupId", source = "globalCategoryItem.id")
	UIGlobalTagItem mapToDTO(EOGlobalCategoryTag eoGlobalTagItem);
}
