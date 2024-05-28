package com.brijframework.content.client.mapper;
import static com.brijframework.content.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.CUST_PROD_APP_ID_ENTITY;
import static com.brijframework.content.constants.Constants.CUST_PROD_APP_ID_UI;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.client.entites.EOCustPrompt;
import com.brijframework.content.client.model.UICustPromptItem;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL)
public interface CustPromptItemMapper  extends GenericMapper<EOCustPrompt, UICustPromptItem>{
	

	@Mapping(target=CUST_PROD_APP_ID_ENTITY, source=CUST_PROD_APP_ID_UI)
	@Override
	EOCustPrompt mapToDAO(UICustPromptItem uiPrompt);
	
	@Mapping(source=CUST_PROD_APP_ID_ENTITY, target=CUST_PROD_APP_ID_UI)
	@Override
	UICustPromptItem mapToDTO(EOCustPrompt eoCustPrompt);
}
