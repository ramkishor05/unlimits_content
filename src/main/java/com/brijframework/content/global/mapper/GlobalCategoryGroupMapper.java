package com.brijframework.content.global.mapper;
import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;

import com.brijframework.content.global.entities.EOGlobalCategoryGroup;
import com.brijframework.content.global.model.UIGlobalCategoryGroup;
import com.brijframework.content.mapper.GenericMapper;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface GlobalCategoryGroupMapper  extends GenericMapper<EOGlobalCategoryGroup, UIGlobalCategoryGroup>{

	@Override
	EOGlobalCategoryGroup mapToDAO(UIGlobalCategoryGroup eoRoleDTO);
	
	@Override
	UIGlobalCategoryGroup mapToDTO(EOGlobalCategoryGroup eoRole);
}
