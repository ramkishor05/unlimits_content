package com.brijframework.content.global.mapper;
import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.global.entities.EOGlobalPromptLibarary;
import com.brijframework.content.global.model.UIGlobalPromptLibarary;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface GlobalPromptLibararyMapper  extends GenericMapper<EOGlobalPromptLibarary, UIGlobalPromptLibarary>{

	@Override
	@Mapping(target = "subCategoryId", source = "subCategory.id")
	@Mapping(target = "tenureId", source = "tenure.id")
	@Mapping(target = "subCategoryName", source = "subCategory.name")
	@Mapping(target = "tenureYear", source = "tenure.year")
    UIGlobalPromptLibarary mapToDTO(EOGlobalPromptLibarary e);
	
	@Override
    EOGlobalPromptLibarary mapToDAO(UIGlobalPromptLibarary d) ;
}
