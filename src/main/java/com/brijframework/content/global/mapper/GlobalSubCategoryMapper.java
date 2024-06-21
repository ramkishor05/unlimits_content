package com.brijframework.content.global.mapper;
import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.global.entities.EOGlobalSubCategory;
import com.brijframework.content.global.model.UIGlobalSubCategory;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface GlobalSubCategoryMapper  extends GenericMapper<EOGlobalSubCategory, UIGlobalSubCategory>{

	@Override
	@Mapping(source = "mainCategoryId", target = "mainCategory.id")
	EOGlobalSubCategory mapToDAO(UIGlobalSubCategory uiGlobalCategoryItem);
	
	@Override
	@Mapping(source = "mainCategory.id", target = "mainCategoryId")
	UIGlobalSubCategory mapToDTO(EOGlobalSubCategory eoGlobalCategoryItem);
}
