package com.brijframework.content.global.mapper;

import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.global.entities.EOGlobalExampleItem;
import com.brijframework.content.global.entities.EOGlobalExampleLibarary;
import com.brijframework.content.global.entities.EOGlobalImageLibarary;
import com.brijframework.content.global.entities.EOGlobalTagLibarary;
import com.brijframework.content.global.model.UIGlobalExampleItem;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface GlobalExampleItemMapper extends GenericMapper<EOGlobalExampleItem, UIGlobalExampleItem>{

	@Override
	@Mapping( source = "imageLibarary.id", target = "imageLibararyId")
	@Mapping( source = "tagLibarary.id", target = "tagLibararyId")
	@Mapping( source = "exampleLibarary.id", target = "exampleLibararyId")
    UIGlobalExampleItem mapToDTO(EOGlobalExampleItem e);
	
	@Override
	@Mapping( target = "imageLibarary", expression = "java(imageLibarary(dtoObject.getImageLibararyId()))")
	@Mapping( target = "tagLibarary", expression = "java(tagLibarary(dtoObject.getTagLibararyId()))")
	@Mapping( target = "exampleLibarary", expression = "java(exampleLibarary(dtoObject.getExampleLibararyId()))")
	EOGlobalExampleItem mapToDAO(UIGlobalExampleItem dtoObject) ;
	
	default EOGlobalImageLibarary imageLibarary(Long imageLibararyId) {
		if(imageLibararyId==null) {
			return null;
		}
		EOGlobalImageLibarary eoGlobalImageLibarary=new EOGlobalImageLibarary();
		eoGlobalImageLibarary.setId(imageLibararyId);
		return eoGlobalImageLibarary;
	}
	
	default EOGlobalTagLibarary tagLibarary(Long tagLibararyId) {
		if(tagLibararyId==null) {
			return null;
		}
		EOGlobalTagLibarary eoGlobalTagLibarary=new EOGlobalTagLibarary();
		eoGlobalTagLibarary.setId(tagLibararyId);
		return eoGlobalTagLibarary;
	}
	
	default EOGlobalExampleLibarary exampleLibarary(Long exampleLibararyId) {
		if(exampleLibararyId==null) {
			return null;
		}
		EOGlobalExampleLibarary eoGlobalExampleLibarary=new EOGlobalExampleLibarary();
		eoGlobalExampleLibarary.setId(exampleLibararyId);
		return eoGlobalExampleLibarary;
	}
	
}
