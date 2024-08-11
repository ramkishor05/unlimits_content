package com.brijframework.content.global.mapper;

import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.global.entities.EOGlobalExampleItem;
import com.brijframework.content.global.model.UIGlobalExampleItem;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface GlobalExampleItemMapper extends GenericMapper<EOGlobalExampleItem, UIGlobalExampleItem>{

	@Override
	@Mapping( source = "imageLibarary.id", target = "imageLibararyId")
	@Mapping( source = "tagLibarary.id", target = "tagLibararyId")
    UIGlobalExampleItem mapToDTO(EOGlobalExampleItem e);
	
}
