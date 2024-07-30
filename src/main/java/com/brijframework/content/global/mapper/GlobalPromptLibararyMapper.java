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
    UIGlobalPromptLibarary mapToDTO(EOGlobalPromptLibarary e);
	
	@Override
	@Mapping(source = "subCategoryId", target = "subCategory.id")
	@Mapping(source = "tenureId", target = "tenure.id")
    EOGlobalPromptLibarary mapToDAO(UIGlobalPromptLibarary d) ;
}
