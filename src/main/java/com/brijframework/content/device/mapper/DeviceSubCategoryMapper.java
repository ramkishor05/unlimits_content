package com.brijframework.content.device.mapper;

import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.device.model.UIDeviceSubCategory;
import com.brijframework.content.global.entities.EOGlobalCategoryItem;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface DeviceSubCategoryMapper  extends GenericMapper<EOGlobalCategoryItem, UIDeviceSubCategory>{

	@Override
	@Mapping(source = "globalCategoryGroup.id", target = "categoryId")
    UIDeviceSubCategory mapToDTO(EOGlobalCategoryItem eoGlobalCategoryGroup);
	
	@Override
	@Mapping(target = "globalCategoryGroup.id", source = "categoryId")
    EOGlobalCategoryItem mapToDAO(UIDeviceSubCategory uiDeviceMainCategory);
}
