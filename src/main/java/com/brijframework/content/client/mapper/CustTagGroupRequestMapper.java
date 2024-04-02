package com.brijframework.content.client.mapper;
import static com.brijframework.content.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;

import com.brijframework.content.client.entites.EOCustTagGroup;
import com.brijframework.content.client.rqrs.CustTagGroupRequest;
import com.brijframework.content.mapper.GenericMapper;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL)
public interface CustTagGroupRequestMapper  extends GenericMapper<EOCustTagGroup, CustTagGroupRequest>{

	@Override
	EOCustTagGroup mapToDAO(CustTagGroupRequest custTagRequest);
	
	@Override
	CustTagGroupRequest mapToDTO(EOCustTagGroup eoCustTag);
}
