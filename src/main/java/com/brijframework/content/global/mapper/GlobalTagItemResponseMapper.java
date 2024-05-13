package com.brijframework.content.global.mapper;
import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.global.entities.EOGlobalTagItem;
import com.brijframework.content.global.rqrs.GlobalTagItemResponse;


@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface GlobalTagItemResponseMapper  extends GenericMapper<EOGlobalTagItem, GlobalTagItemResponse>{

}
