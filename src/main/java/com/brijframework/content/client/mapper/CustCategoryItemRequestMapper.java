package com.brijframework.content.client.mapper;
import static com.brijframework.content.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.brijframework.content.client.entites.EOCustCategoryItem;
import com.brijframework.content.client.rqrs.CustCategoryItemRequest;
import com.brijframework.content.mapper.GenericMapper;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL)
public interface CustCategoryItemRequestMapper  extends GenericMapper<EOCustCategoryItem, CustCategoryItemRequest>{

	@Override
	@Mapping(target = "custCategoryGroup.id", source = "custCategoryGroupId")
	EOCustCategoryItem mapToDAO(CustCategoryItemRequest custCategoryRequest);
	
	@Override
	@Mapping(source = "custCategoryGroup.id", target = "custCategoryGroupId")
	CustCategoryItemRequest mapToDTO(EOCustCategoryItem eoCustCategory);
}
