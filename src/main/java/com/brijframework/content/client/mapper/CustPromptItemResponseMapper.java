package com.brijframework.content.client.mapper;
import static com.brijframework.content.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.client.entites.EOCustPrompt;
import com.brijframework.content.client.rqrs.CustPromptItemResponse;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL)
public interface CustPromptItemResponseMapper  extends GenericMapper<EOCustPrompt, CustPromptItemResponse>{

	@Override
	EOCustPrompt mapToDAO(CustPromptItemResponse uiCustPrompt);
	
	@Override
	CustPromptItemResponse mapToDTO(EOCustPrompt eoCustPrompt);
}
