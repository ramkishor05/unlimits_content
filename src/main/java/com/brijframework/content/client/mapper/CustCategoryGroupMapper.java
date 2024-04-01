package com.brijframework.content.client.mapper;
import static com.brijframework.content.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;

import com.brijframework.content.client.entites.EOCustCategoryGroup;
import com.brijframework.content.client.model.UICustCategoryGroup;
import com.brijframework.content.mapper.GenericMapper;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL)
public interface CustCategoryGroupMapper  extends GenericMapper<EOCustCategoryGroup, UICustCategoryGroup>{

	@Override
	EOCustCategoryGroup mapToDAO(UICustCategoryGroup uiCustCategoryGroup);
	
	@Override
	UICustCategoryGroup mapToDTO(EOCustCategoryGroup eoCustCategoryGroup);
}
