package com.brijframework.content.global.mapper;
import static com.brijframework.content.constants.ClientConstants.UI_DATE_FORMAT_MM_DD_YY;
import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.global.entities.EOGlobalJournalLibarary;
import com.brijframework.content.global.model.UIGlobalJournalLibarary;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface GlobalJournalLibararyMapper  extends GenericMapper<EOGlobalJournalLibarary, UIGlobalJournalLibarary>{

	@Override
	@Mapping(source = "journalDate", target = "journalDate" ,dateFormat = UI_DATE_FORMAT_MM_DD_YY)
	UIGlobalJournalLibarary mapToDTO(EOGlobalJournalLibarary e);
	
	@Override
	@Mapping(source = "journalDate", target = "journalDate" ,dateFormat = UI_DATE_FORMAT_MM_DD_YY)
	EOGlobalJournalLibarary mapToDAO(UIGlobalJournalLibarary d);
	
	
}
