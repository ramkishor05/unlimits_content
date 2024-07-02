package com.brijframework.content.device.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.QueryServiceImpl;

import com.brijframework.content.device.mapper.DeviceJournalLibararyMapper;
import com.brijframework.content.device.model.UIDeviceJournalLibarary;
import com.brijframework.content.device.service.DeviceJournalLibararyService;
import com.brijframework.content.global.entities.EOGlobalJournalLibarary;
import com.brijframework.content.global.repository.GlobalJournalLibararyRepository;

@Service
public class DeviceJournalLibararyServiceImpl extends QueryServiceImpl<UIDeviceJournalLibarary, EOGlobalJournalLibarary, Long> implements DeviceJournalLibararyService {
	
	@Autowired
	private GlobalJournalLibararyRepository globalJournalLibararyRepository;
	
	@Autowired
	private DeviceJournalLibararyMapper deviceJournalLibararyMapper;
	
	@Override
	public JpaRepository<EOGlobalJournalLibarary, Long> getRepository() {
		return globalJournalLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalJournalLibarary, UIDeviceJournalLibarary> getMapper() {
		return deviceJournalLibararyMapper;
	}

	@Override
	public List<UIDeviceJournalLibarary> findTodayJournalLibarary() {
		return postFetch(globalJournalLibararyRepository.findTodayJournalLibarary());
	}
	
	@Override
	public List<UIDeviceJournalLibarary> findYesterdayJournalLibarary() {
		return postFetch(globalJournalLibararyRepository.findYesterdayJournalLibarary());
	}
	
	@Override
	protected List<UIDeviceJournalLibarary> postFetch(List<EOGlobalJournalLibarary> findObjects) {
		List<UIDeviceJournalLibarary> uiObjects = super.postFetch(findObjects);
		uiObjects.sort((op1,op2)->op1.getOrderSequence().compareTo(op2.getOrderSequence()));
		return uiObjects;
	}
}
