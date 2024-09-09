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
import com.brijframework.content.global.model.UIGlobalImageLibarary;
import com.brijframework.content.global.model.UIGlobalImageModel;
import com.brijframework.content.global.model.UIGlobalTagModel;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface GlobalImageLibararyMapper  extends GenericMapper<EOGlobalImageLibarary, UIGlobalImageLibarary>{

	@Override
	@Mapping(source = "subCategoryId", target = "subCategory.id")
	@Mapping(target = "tagList", ignore = true)
	EOGlobalImageLibarary mapToDAO(UIGlobalImageLibarary uiGlobalCategoryImage);
	
	@Override
	@Mapping(source = "subCategory.id", target = "subCategoryId")
	@Mapping(source = "subCategory.name", target = "subCategoryName")
	UIGlobalImageLibarary mapToDTO(EOGlobalImageLibarary eoGlobalCategoryImage);
	
	default UIGlobalTagModel uiGlobalTagLibarary(EOGlobalTagImageMapping globalTagImageMapping ) {
		return uiGlobalTagLibarary(globalTagImageMapping.getTagLibarary());
	}

	UIGlobalTagModel uiGlobalTagLibarary(EOGlobalTagLibarary tagLibarary);

	List<UIGlobalTagModel> tagMappingForImageList(List<EOGlobalTagImageMapping> tagImageMappings);

	EOGlobalImageLibarary modelToDAO(UIGlobalImageModel uiImageModel);

}
