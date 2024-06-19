package com.brijframework.content.device.service;

import java.util.List;

import org.unlimits.rest.crud.service.QueryService;

import com.brijframework.content.device.model.UIDeviceJournalLibarary;
import com.brijframework.content.global.entities.EOGlobalJournalLibarary;


public interface DeviceJournalLibararyService extends QueryService<UIDeviceJournalLibarary, EOGlobalJournalLibarary, Long>{

	List<UIDeviceJournalLibarary> findTodayJournalLibarary();

	List<UIDeviceJournalLibarary> findYesterdayJournalLibarary();

}
