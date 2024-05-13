package com.brijframework.content.client.mapper;
import static com.brijframework.content.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.client.entites.EOCustTagGroup;
import com.brijframework.content.client.rqrs.CustTagGroupResponse;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL)
public interface CustTagGroupResponseMapper  extends GenericMapper<EOCustTagGroup, CustTagGroupResponse>{

	@Override
	EOCustTagGroup mapToDAO(CustTagGroupResponse uiCustTag);
	
	@Override
	CustTagGroupResponse mapToDTO(EOCustTagGroup eoCustTag);
}
