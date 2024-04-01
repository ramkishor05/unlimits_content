package com.brijframework.content.client.mapper;
import static com.brijframework.content.constants.Constants.*;

import org.mapstruct.Mapper;

import com.brijframework.content.client.entites.EOCustPromptItem;
import com.brijframework.content.client.rqrs.CustPromptItemResponse;
import com.brijframework.content.mapper.GenericMapper;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL)
public interface CustPromptItemResponseMapper  extends GenericMapper<EOCustPromptItem, CustPromptItemResponse>{

	@Override
	EOCustPromptItem mapToDAO(CustPromptItemResponse uiCustPrompt);
	
	@Override
	CustPromptItemResponse mapToDTO(EOCustPromptItem eoCustPrompt);
}
