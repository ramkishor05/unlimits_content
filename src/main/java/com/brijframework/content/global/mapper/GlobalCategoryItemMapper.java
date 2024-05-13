package com.brijframework.content.global.mapper;
import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.global.entities.EOGlobalCategoryItem;
import com.brijframework.content.global.model.UIGlobalCategoryItem;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface GlobalCategoryItemMapper  extends GenericMapper<EOGlobalCategoryItem, UIGlobalCategoryItem>{

	@Override
	@Mapping(source = "groupId", target = "globalCategoryGroup.id")
	EOGlobalCategoryItem mapToDAO(UIGlobalCategoryItem uiGlobalCategoryItem);
	
	@Override
	@Mapping(source = "globalCategoryGroup.id", target = "groupId")
	UIGlobalCategoryItem mapToDTO(EOGlobalCategoryItem eoGlobalCategoryItem);
}
