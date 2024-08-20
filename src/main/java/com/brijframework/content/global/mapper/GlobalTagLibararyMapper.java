package com.brijframework.content.global.mapper;
import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.global.entities.EOGlobalTagImageMapping;
import com.brijframework.content.global.entities.EOGlobalTagLibarary;
import com.brijframework.content.global.model.UIGlobalImageLibarary;
import com.brijframework.content.global.model.UIGlobalTagLibarary;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface GlobalTagLibararyMapper  extends GenericMapper<EOGlobalTagLibarary, UIGlobalTagLibarary>{

	@Override
	@Mapping(target = "subCategory.id", source = "subCategoryId")
	@Mapping(target = "imageList", ignore = true)
	EOGlobalTagLibarary mapToDAO(UIGlobalTagLibarary uiGlobalTagItem);
	
	@Override
	@Mapping(target = "subCategoryId", source = "subCategory.id")
	UIGlobalTagLibarary mapToDTO(EOGlobalTagLibarary eoGlobalTagItem);
	
	default UIGlobalImageLibarary uiGlobalImageLibarary(EOGlobalTagImageMapping globalTagImageMapping ) {
		return uiGlobalImageLibarary(globalTagImageMapping.getTagLibarary());
	}

	UIGlobalImageLibarary uiGlobalImageLibarary(EOGlobalTagLibarary tagLibarary);
}
