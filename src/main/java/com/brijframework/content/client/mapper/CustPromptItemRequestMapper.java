package com.brijframework.content.client.mapper;
import static com.brijframework.content.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;

import com.brijframework.content.client.entites.EOCustPromptItem;
import com.brijframework.content.client.rqrs.CustPromptItemRequest;
import com.brijframework.content.mapper.GenericMapper;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL)
public interface CustPromptItemRequestMapper  extends GenericMapper<EOCustPromptItem, CustPromptItemRequest>{

	@Override
	EOCustPromptItem mapToDAO(CustPromptItemRequest custPromptRequest);
	
	@Override
	CustPromptItemRequest mapToDTO(EOCustPromptItem eoCustPrompt);
}
