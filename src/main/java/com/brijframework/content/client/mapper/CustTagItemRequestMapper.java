package com.brijframework.content.client.mapper;
import static com.brijframework.content.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;

import com.brijframework.content.client.entites.EOCustTagItem;
import com.brijframework.content.client.rqrs.CustTagItemRequest;
import com.brijframework.content.mapper.GenericMapper;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL)
public interface CustTagItemRequestMapper  extends GenericMapper<EOCustTagItem, CustTagItemRequest>{

	@Override
	EOCustTagItem mapToDAO(CustTagItemRequest custTagRequest);
	
	@Override
	CustTagItemRequest mapToDTO(EOCustTagItem eoCustTag);
}
