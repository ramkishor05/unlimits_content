package com.brijframework.content.device.mapper;

import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.device.model.UIDeviceImageLibarary;
import com.brijframework.content.device.model.UIDeviceTagModel;
import com.brijframework.content.global.entities.EOGlobalImageLibarary;
import com.brijframework.content.global.entities.EOGlobalTagImageMapping;
import com.brijframework.content.global.entities.EOGlobalTagLibarary;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface DeviceImageLibararyMapper extends GenericMapper<EOGlobalImageLibarary, UIDeviceImageLibarary> {

	@Override
	@Mapping(source = "subCategory.id", target = "subCategoryId")
	@Mapping(source = "subCategory.name", target = "subCategoryName")
	@Mapping(source = "subCategory.mainCategory.id", target = "mainCategoryId")
	@Mapping(source = "subCategory.mainCategory.name", target = "mainCategoryName")
	UIDeviceImageLibarary mapToDTO(EOGlobalImageLibarary eoGlobalTagGroup);

	@Override
	@Mapping(target = "subCategory.id", source = "subCategoryId")
	@Mapping(target = "subCategory.name", source = "subCategoryName")
	@Mapping(target = "subCategory.mainCategory.id", source = "mainCategoryId")
	@Mapping(target = "subCategory.mainCategory.name", source = "mainCategoryName")
	EOGlobalImageLibarary mapToDAO(UIDeviceImageLibarary uiDeviceMainTag);

	List<UIDeviceTagModel> tagMappingForTagList(List<EOGlobalTagImageMapping> globalTagImageMappingList);

	default UIDeviceTagModel tagMappingForTag(EOGlobalTagImageMapping globalTagImageMapping) {
		return tagMappingForTagUI(globalTagImageMapping.getTagLibarary());
	}

	@Mapping(source = "subCategory.id", target = "subCategoryId")
	@Mapping(source = "subCategory.name", target = "subCategoryName")
	@Mapping(source = "subCategory.mainCategory.id", target = "mainCategoryId")
	@Mapping(source = "subCategory.mainCategory.name", target = "mainCategoryName")
	UIDeviceTagModel tagMappingForTagUI(EOGlobalTagLibarary eoGlobalTagLibarary);
}
