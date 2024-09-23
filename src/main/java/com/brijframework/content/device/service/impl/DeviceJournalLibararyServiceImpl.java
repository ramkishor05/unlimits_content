package com.brijframework.content.device.service.impl;

import static com.brijframework.content.constants.ClientConstants.UI_DATE_FORMAT_MMMM_DD_YYYY;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.QueryServiceImpl;
import org.unlimits.rest.repository.CustomPredicate;

import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.device.mapper.DeviceJournalLibararyMapper;
import com.brijframework.content.device.model.UIDeviceJournalModel;
import com.brijframework.content.device.service.DeviceJournalLibararyService;
import com.brijframework.content.global.entities.EOGlobalJournalLibarary;
import com.brijframework.content.global.repository.GlobalJournalLibararyRepository;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;

@Service
public class DeviceJournalLibararyServiceImpl extends QueryServiceImpl<UIDeviceJournalModel, EOGlobalJournalLibarary, Long> implements DeviceJournalLibararyService {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(DeviceJournalLibararyServiceImpl.class);

	private static final String RECORD_STATE = "recordState";
	
	@Autowired
	private GlobalJournalLibararyRepository globalJournalLibararyRepository;
	
	@Autowired
	private DeviceJournalLibararyMapper deviceJournalLibararyMapper;

	{
		CustomPredicate<EOGlobalJournalLibarary> affirmationDate = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			try {
				Path<Date> custBusinessAppPath = root.get("journalDate");
				In<Object> custBusinessAppIn = criteriaBuilder.in(custBusinessAppPath);
				DateFormat timeFormat = new SimpleDateFormat(UI_DATE_FORMAT_MMMM_DD_YYYY);
				Date date = timeFormat.parse(filter.getColumnValue().toString());
				custBusinessAppIn.value(new java.sql.Date(date.getTime()) );
				return custBusinessAppIn;
			}catch (ParseException e) {
				LOGGER.error("WARN: unexpected exception for affirmationDate: " + filter.getColumnValue());
				return null;
			}catch (Exception e) {
				LOGGER.error("WARN: unexpected exception for affirmationDate: " + filter.getColumnValue(), e);
				return null;
			}
		};
 
		addCustomPredicate("journalDate", affirmationDate);
	}
	
	
	@Override
	public JpaRepository<EOGlobalJournalLibarary, Long> getRepository() {
		return globalJournalLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalJournalLibarary, UIDeviceJournalModel> getMapper() {
		return deviceJournalLibararyMapper;
	}

	@Override
	public List<UIDeviceJournalModel> findTodayJournalLibarary(Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		List<EOGlobalJournalLibarary> findTodayJournalLibarary = globalJournalLibararyRepository.findTodayJournalLibarary(RecordStatus.ACTIVETED.getStatusIds());
		if(CollectionUtils.isEmpty(findTodayJournalLibarary)) {
			return postFetch(globalJournalLibararyRepository.findLastJournalLibarary(RecordStatus.ACTIVETED.getStatusIds()), headers, filters, actions);
		}
		return postFetch(findTodayJournalLibarary, headers, filters, actions);
	}
	
	@Override
	public List<UIDeviceJournalModel> findYesterdayJournalLibarary(Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		return postFetch(globalJournalLibararyRepository.findYesterdayJournalLibarary(RecordStatus.ACTIVETED.getStatusIds()), headers, filters, actions);
	}
	
	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
	}
	
	@Override
	public List<UIDeviceJournalModel> postFetch(List<EOGlobalJournalLibarary> findObjects, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		List<UIDeviceJournalModel> uiObjects = super.postFetch(findObjects, headers, filters, actions);
		uiObjects.sort((op1,op2)->op1.getOrderSequence().compareTo(op2.getOrderSequence()));
		return uiObjects;
	}
}
