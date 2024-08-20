package com.brijframework.content.device.mapper;

import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.device.model.UIDeviceImageModel;
import com.brijframework.content.device.model.UIDeviceTagLibarary;
import com.brijframework.content.global.entities.EOGlobalImageLibarary;
import com.brijframework.content.global.entities.EOGlobalTagImageMapping;
import com.brijframework.content.global.entities.EOGlobalTagLibarary;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface DeviceTagLibararyMapper  extends GenericMapper<EOGlobalTagLibarary, UIDeviceTagLibarary>{

	@Override
	@Mapping(source = "subCategory.id", target = "subCategoryId")
	@Mapping(source = "subCategory.name", target = "subCategoryName")
	@Mapping(source = "subCategory.mainCategory.id", target = "mainCategoryId")
	@Mapping(source = "subCategory.mainCategory.name", target = "mainCategoryName")
    UIDeviceTagLibarary mapToDTO(EOGlobalTagLibarary eoGlobalTagGroup);
	
	@Override
	@Mapping(target = "subCategory.id", source = "subCategoryId")
	@Mapping(target = "subCategory.name", source = "subCategoryName")
	@Mapping(target = "subCategory.mainCategory.id", source = "mainCategoryId")
	@Mapping(target = "subCategory.mainCategory.name", source = "mainCategoryName")
    EOGlobalTagLibarary mapToDAO(UIDeviceTagLibarary uiDeviceMainTag);
	
	List<UIDeviceImageModel> tagMappingForImageList(List<EOGlobalTagImageMapping> globalTagImageMappingList);
	
	default UIDeviceImageModel tagMappingForImage(EOGlobalTagImageMapping globalTagImageMapping) {
		return tagMappingForImageUI(globalTagImageMapping.getImageLibarary());
	}
	
	@Mapping(source = "subCategory.id", target = "subCategoryId")
	@Mapping(source = "subCategory.name", target = "subCategoryName")
	@Mapping(source = "subCategory.mainCategory.id", target = "mainCategoryId")
	@Mapping(source = "subCategory.mainCategory.name", target = "mainCategoryName")
	UIDeviceImageModel tagMappingForImageUI(EOGlobalImageLibarary eoGlobalImageLibarary);
}
