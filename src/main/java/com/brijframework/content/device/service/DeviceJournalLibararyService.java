package com.brijframework.content.device.service;

import java.util.List;

import org.unlimits.rest.crud.service.QueryService;

import com.brijframework.content.device.model.UIDeviceJournalModel;
import com.brijframework.content.global.entities.EOGlobalJournalLibarary;


public interface DeviceJournalLibararyService extends QueryService<UIDeviceJournalModel, EOGlobalJournalLibarary, Long>{

	List<UIDeviceJournalModel> findTodayJournalLibarary();

	List<UIDeviceJournalModel> findYesterdayJournalLibarary();

}
