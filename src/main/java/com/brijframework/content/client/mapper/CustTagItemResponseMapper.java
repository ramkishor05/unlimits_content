package com.brijframework.content.client.mapper;
import static com.brijframework.content.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.client.entites.EOCustTagItem;
import com.brijframework.content.client.rqrs.CustTagItemResponse;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL)
public interface CustTagItemResponseMapper  extends GenericMapper<EOCustTagItem, CustTagItemResponse>{

	@Override
	EOCustTagItem mapToDAO(CustTagItemResponse uiCustTag);
	
	@Override
	CustTagItemResponse mapToDTO(EOCustTagItem eoCustTag);
}
