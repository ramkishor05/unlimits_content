package com.brijframework.content.client.mapper;
import static com.brijframework.content.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.client.entites.EOCustCategoryGroup;
import com.brijframework.content.client.model.UICustCategoryGroup;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL)
public interface CustCategoryGroupMapper  extends GenericMapper<EOCustCategoryGroup, UICustCategoryGroup>{

	@Override
	EOCustCategoryGroup mapToDAO(UICustCategoryGroup uiCustCategoryGroup);
	
	@Override
	UICustCategoryGroup mapToDTO(EOCustCategoryGroup eoCustCategoryGroup);
}
