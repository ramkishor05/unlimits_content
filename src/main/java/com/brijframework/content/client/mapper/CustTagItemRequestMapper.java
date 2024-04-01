package com.brijframework.content.client.mapper;
import static com.brijframework.content.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.brijframework.content.client.entites.EOCustTagItem;
import com.brijframework.content.client.rqrs.CustTagItemRequest;
import com.brijframework.content.mapper.GenericMapper;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL)
public interface CustTagItemRequestMapper  extends GenericMapper<EOCustTagItem, CustTagItemRequest>{

	@Override
	@Mapping(target = "custTagGroup.id", source = "custTagGroupId")
	EOCustTagItem mapToDAO(CustTagItemRequest custTagRequest);
	
	@Override
	@Mapping(source = "custTagGroup.id", target = "custTagGroupId")
	CustTagItemRequest mapToDTO(EOCustTagItem eoCustTag);
}
