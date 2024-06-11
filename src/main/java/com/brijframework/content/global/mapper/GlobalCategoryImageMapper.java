package com.brijframework.content.global.mapper;
import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.global.entities.EOGlobalCategoryImage;
import com.brijframework.content.global.model.UIGlobalCategoryImage;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface GlobalCategoryImageMapper  extends GenericMapper<EOGlobalCategoryImage, UIGlobalCategoryImage>{

	@Override
	@Mapping(source = "groupId", target = "globalCategoryItem.id")
	EOGlobalCategoryImage mapToDAO(UIGlobalCategoryImage uiGlobalCategoryImage);
	
	@Override
	@Mapping(source = "globalCategoryItem.id", target = "groupId")
	UIGlobalCategoryImage mapToDTO(EOGlobalCategoryImage eoGlobalCategoryImage);
}
