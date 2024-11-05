package com.brijframework.content.global.mapper;

import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.global.entities.EOGlobalExampleLibarary;
import com.brijframework.content.global.model.UIGlobalExampleLibarary;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface GlobalExampleLibararyMapper extends GenericMapper<EOGlobalExampleLibarary, UIGlobalExampleLibarary>{

	@Override
	@Mapping(source = "subCategory.id", target = "subCategoryId")
	@Mapping(source = "subCategory.name", target = "subCategoryName")
	@Mapping(source = "exampleItems", target = "exampleItems", ignore = true)
	UIGlobalExampleLibarary mapToDTO(EOGlobalExampleLibarary eoGlobalTagGroup);
	
	@Override
	@Mapping(target = "subCategory.id", source = "subCategoryId")
	@Mapping(source = "exampleItems", target = "exampleItems", ignore = true)
	EOGlobalExampleLibarary mapToDAO(UIGlobalExampleLibarary uiGlobalExampleLibarary);
}
