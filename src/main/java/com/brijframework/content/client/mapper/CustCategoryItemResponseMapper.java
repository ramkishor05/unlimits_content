package com.brijframework.content.client.mapper;
import static com.brijframework.content.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.client.entites.EOCustCategoryItem;
import com.brijframework.content.client.rqrs.CustCategoryItemResponse;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL)
public interface CustCategoryItemResponseMapper  extends GenericMapper<EOCustCategoryItem, CustCategoryItemResponse>{

	@Override
	EOCustCategoryItem mapToDAO(CustCategoryItemResponse uiCustCategory);
	
	@Override
	CustCategoryItemResponse mapToDTO(EOCustCategoryItem eoCustCategory);
}
