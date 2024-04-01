package com.brijframework.content.global.mapper;
import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.brijframework.content.global.entities.EOGlobalCategoryItem;
import com.brijframework.content.global.rqrs.GlobalCategoryItemRequest;
import com.brijframework.content.mapper.GenericMapper;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface GlobalCategoryItemRequestMapper  extends GenericMapper<EOGlobalCategoryItem, GlobalCategoryItemRequest>{

	@Override
	@Mapping(target = "globalCategoryGroup.id", source = "glbCategoryGroupId")
	EOGlobalCategoryItem mapToDAO(GlobalCategoryItemRequest globalCategoryRequest);
	
	@Override
	@Mapping(source = "globalCategoryGroup.id", target = "glbCategoryGroupId")
	GlobalCategoryItemRequest mapToDTO(EOGlobalCategoryItem eoGlobalCategory);
}
