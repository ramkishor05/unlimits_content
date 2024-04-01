package com.brijframework.content.global.mapper;
import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;

import com.brijframework.content.global.entities.EOGlobalCategoryItem;
import com.brijframework.content.global.model.UIGlobalCategoryItem;
import com.brijframework.content.mapper.GenericMapper;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface GlobalCategoryItemMapper  extends GenericMapper<EOGlobalCategoryItem, UIGlobalCategoryItem>{

	@Override
	EOGlobalCategoryItem mapToDAO(UIGlobalCategoryItem uiGlobalCategoryItem);
	
	@Override
	UIGlobalCategoryItem mapToDTO(EOGlobalCategoryItem eoGlobalCategoryItem);
}
