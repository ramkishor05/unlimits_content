package com.brijframework.content.client.mapper;
import static com.brijframework.content.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.client.entites.EOCustMediaItem;
import com.brijframework.content.client.rqrs.CustMediaItemResponse;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL)
public interface CustMediaItemResponseMapper  extends GenericMapper<EOCustMediaItem, CustMediaItemResponse>{

	@Override
	EOCustMediaItem mapToDAO(CustMediaItemResponse uiCustMedia);
	
	@Override
	CustMediaItemResponse mapToDTO(EOCustMediaItem eoCustMedia);
}
