package com.brijframework.content.global.mapper;
import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.brijframework.content.global.entities.EOGlobalMediaItem;
import com.brijframework.content.global.rqrs.GlobalMediaItemRequest;
import com.brijframework.content.mapper.GenericMapper;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface GlobalMediaItemRequestMapper  extends GenericMapper<EOGlobalMediaItem, GlobalMediaItemRequest>{

	@Override
	@Mapping(target = "globalMediaGroup.id", source = "glbMediaGroupId")
	EOGlobalMediaItem mapToDAO(GlobalMediaItemRequest globalMediaRequest);
	
	@Override
	@Mapping(source = "globalMediaGroup.id", target = "glbMediaGroupId")
	GlobalMediaItemRequest mapToDTO(EOGlobalMediaItem eoGlobalMedia);
}
