package com.brijframework.content.device.service.impl;

import static com.brijframework.content.constants.ClientConstants.UI_DATE_FORMAT_MMMM_DD_YYYY;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.QueryServiceImpl;
import org.unlimits.rest.repository.CustomPredicate;

import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.device.mapper.DeviceJournalLibararyMapper;
import com.brijframework.content.device.model.UIDeviceJournalLibarary;
import com.brijframework.content.device.service.DeviceJournalLibararyService;
import com.brijframework.content.global.entities.EOGlobalJournalLibarary;
import com.brijframework.content.global.repository.GlobalJournalLibararyRepository;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;

@Service
public class DeviceJournalLibararyServiceImpl extends QueryServiceImpl<UIDeviceJournalLibarary, EOGlobalJournalLibarary, Long> implements DeviceJournalLibararyService {
	
	private static final String RECORD_STATE = "recordState";
	
	@Autowired
	private GlobalJournalLibararyRepository globalJournalLibararyRepository;
	
	@Autowired
	private DeviceJournalLibararyMapper deviceJournalLibararyMapper;

	{
		CustomPredicate<EOGlobalJournalLibarary> affirmationDate = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Path<Date> custBusinessAppPath = root.get("journalDate");
			In<Object> custBusinessAppIn = criteriaBuilder.in(custBusinessAppPath);
			DateFormat timeFormat = new SimpleDateFormat(UI_DATE_FORMAT_MMMM_DD_YYYY);
			Date date = null;
			try {
				date = timeFormat.parse(filter.getColumnValue().toString());
			} catch (ParseException e) {
				System.err.println("WARN: unexpected object in Object.dateValue(): " + filter.getColumnValue());
			}
			custBusinessAppIn.value(new java.sql.Date(date.getTime()) );
			return custBusinessAppIn;
		};
 
		addCustomPredicate("journalDate", affirmationDate);
	}
	
	
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
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters) {
		filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
	}
	
	@Override
	public List<UIDeviceJournalLibarary> postFetch(List<EOGlobalJournalLibarary> findObjects) {
		List<UIDeviceJournalLibarary> uiObjects = super.postFetch(findObjects);
		uiObjects.sort((op1,op2)->op1.getOrderSequence().compareTo(op2.getOrderSequence()));
		return uiObjects;
	}
}
