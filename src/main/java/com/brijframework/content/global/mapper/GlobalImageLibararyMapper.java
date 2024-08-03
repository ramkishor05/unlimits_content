package com.brijframework.content.global.mapper;
import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.global.entities.EOGlobalImageLibarary;
import com.brijframework.content.global.model.UIGlobalImageLibarary;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface GlobalImageLibararyMapper  extends GenericMapper<EOGlobalImageLibarary, UIGlobalImageLibarary>{

	@Override
	@Mapping(source = "subCategoryId", target = "subCategory.id")
	EOGlobalImageLibarary mapToDAO(UIGlobalImageLibarary uiGlobalCategoryImage);
	
	@Override
	@Mapping(source = "subCategory.id", target = "subCategoryId")
	UIGlobalImageLibarary mapToDTO(EOGlobalImageLibarary eoGlobalCategoryImage);
}
