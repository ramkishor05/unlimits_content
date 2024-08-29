package com.brijframework.content.global.mapper;
import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.global.entities.EOGlobalImageLibarary;
import com.brijframework.content.global.entities.EOGlobalTagImageMapping;
import com.brijframework.content.global.entities.EOGlobalTagLibarary;
import com.brijframework.content.global.model.UIGlobalImageModel;
import com.brijframework.content.global.model.UIGlobalTagLibarary;
import com.brijframework.content.global.model.UIGlobalTagModel;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface GlobalTagLibararyMapper  extends GenericMapper<EOGlobalTagLibarary, UIGlobalTagLibarary>{

	@Override
	@Mapping(target = "subCategory.id", source = "subCategoryId")
	@Mapping(target = "imageList", ignore = true)
	EOGlobalTagLibarary mapToDAO(UIGlobalTagLibarary uiGlobalTagItem);
	
	@Override
	@Mapping(target = "subCategoryId", source = "subCategory.id")
	@Mapping(target = "imageList", ignore = true)
	UIGlobalTagLibarary mapToDTO(EOGlobalTagLibarary eoGlobalTagItem);
	
	default UIGlobalImageModel uiGlobalImageLibarary(EOGlobalTagImageMapping globalTagImageMapping ) {
		return uiGlobalImageLibarary(globalTagImageMapping.getImageLibarary());
	}

	UIGlobalImageModel uiGlobalImageLibarary(EOGlobalImageLibarary tagLibarary);
	
	List<UIGlobalImageModel> tagMappingForImageList(List<EOGlobalTagImageMapping> imageMappingList);

	EOGlobalTagLibarary modelToDAO(UIGlobalTagModel uiTagLibarary);
}
