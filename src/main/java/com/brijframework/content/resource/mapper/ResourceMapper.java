package com.brijframework.content.resource.mapper;
import static com.brijframework.content.constants.Constants.APP_RESOURCE_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.resource.entities.EOResource;
import com.brijframework.content.resource.modal.UIResource;

@Mapper(componentModel = SPRING, implementationPackage = APP_RESOURCE_PACKAGE_IMPL)
public interface ResourceMapper  extends GenericMapper<EOResource, UIResource>{

}
