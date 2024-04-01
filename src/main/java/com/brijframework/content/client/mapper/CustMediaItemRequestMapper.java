package com.brijframework.content.client.mapper;
import static com.brijframework.content.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;

import com.brijframework.content.client.entites.EOCustMediaItem;
import com.brijframework.content.client.rqrs.CustMediaItemRequest;
import com.brijframework.content.mapper.GenericMapper;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL)
public interface CustMediaItemRequestMapper  extends GenericMapper<EOCustMediaItem, CustMediaItemRequest>{

	@Override
	EOCustMediaItem mapToDAO(CustMediaItemRequest custMediaRequest);
	
	@Override
	CustMediaItemRequest mapToDTO(EOCustMediaItem eoCustMedia);
}
