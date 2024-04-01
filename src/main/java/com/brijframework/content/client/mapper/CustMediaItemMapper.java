package com.brijframework.content.client.mapper;
import static com.brijframework.content.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.CUST_PROD_APP_ID_ENTITY;
import static com.brijframework.content.constants.Constants.CUST_PROD_APP_ID_UI;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.brijframework.content.client.entites.EOCustMediaItem;
import com.brijframework.content.client.model.UICustMediaItem;
import com.brijframework.content.mapper.GenericMapper;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL)
public interface CustMediaItemMapper  extends GenericMapper<EOCustMediaItem, UICustMediaItem>{
	

	@Mapping(target=CUST_PROD_APP_ID_ENTITY, source=CUST_PROD_APP_ID_UI)
	@Override
	EOCustMediaItem mapToDAO(UICustMediaItem uiMedia);
	
	@Mapping(source=CUST_PROD_APP_ID_ENTITY, target=CUST_PROD_APP_ID_UI)
	@Override
	UICustMediaItem mapToDTO(EOCustMediaItem eoCustMedia);
}
