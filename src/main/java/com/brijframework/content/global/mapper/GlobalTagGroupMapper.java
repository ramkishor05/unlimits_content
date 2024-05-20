package com.brijframework.content.global.mapper;
import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.global.entities.EOGlobalTagGroup;
import com.brijframework.content.global.model.UIGlobalTagGroup;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface GlobalTagGroupMapper  extends GenericMapper<EOGlobalTagGroup, UIGlobalTagGroup>{

	@Override
	@Mapping(target  = "subCategoryId" , source = "globalCategoryItem.id" )
	UIGlobalTagGroup mapToDTO(EOGlobalTagGroup e) ;
	
	@Override
	@Mapping(source  = "subCategoryId" , target = "globalCategoryItem.id" )
	EOGlobalTagGroup mapToDAO(UIGlobalTagGroup d);
}
