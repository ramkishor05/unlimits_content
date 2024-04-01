package com.brijframework.content.global.mapper;
import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;

import com.brijframework.content.global.entities.EOGlobalPromptItem;
import com.brijframework.content.global.rqrs.GlobalPromptItemRequest;
import com.brijframework.content.mapper.GenericMapper;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface GlobalPromptItemRequestMapper  extends GenericMapper<EOGlobalPromptItem, GlobalPromptItemRequest>{

	@Override
	EOGlobalPromptItem mapToDAO(GlobalPromptItemRequest globalPromptRequest);
	
	@Override
	GlobalPromptItemRequest mapToDTO(EOGlobalPromptItem eoGlobalPrompt);
}
