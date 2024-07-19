package com.brijframework.content.device.mapper;

import static com.brijframework.content.constants.ClientConstants.UI_DATE_FORMAT_MMMM_DD_YYYY;
import static com.brijframework.content.constants.Constants.APP_GLOBAL_PACKAGE_IMPL;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.device.model.UIDeviceJournalLibarary;
import com.brijframework.content.global.entities.EOGlobalJournalLibarary;

@Mapper(componentModel = SPRING, implementationPackage = APP_GLOBAL_PACKAGE_IMPL)
public interface DeviceJournalLibararyMapper  extends GenericMapper<EOGlobalJournalLibarary, UIDeviceJournalLibarary>{
	
	@Override
	@Mapping(source = "journalDate", target = "journalDate" ,dateFormat = UI_DATE_FORMAT_MMMM_DD_YYYY)
	UIDeviceJournalLibarary mapToDTO(EOGlobalJournalLibarary e);
	
	@Override
	@Mapping(source = "journalDate", target = "journalDate" ,dateFormat = UI_DATE_FORMAT_MMMM_DD_YYYY)
	EOGlobalJournalLibarary mapToDAO(UIDeviceJournalLibarary d);
}
