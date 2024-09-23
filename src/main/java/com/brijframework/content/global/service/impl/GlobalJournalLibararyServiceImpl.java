package com.brijframework.content.global.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.content.constants.DataStatus;
import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.global.entities.EOGlobalJournalLibarary;
import com.brijframework.content.global.mapper.GlobalJournalLibararyMapper;
import com.brijframework.content.global.model.UIGlobalJournalLibarary;
import com.brijframework.content.global.repository.GlobalJournalLibararyRepository;
import com.brijframework.content.global.service.GlobalJournalLibararyService;

@Service
public class GlobalJournalLibararyServiceImpl  extends CrudServiceImpl<UIGlobalJournalLibarary, EOGlobalJournalLibarary, Long> implements GlobalJournalLibararyService {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(GlobalJournalLibararyServiceImpl.class);

	private static final String RECORD_STATE = "recordState";

	@Autowired
	private GlobalJournalLibararyRepository globalJournalLibararyRepository;
	
	@Autowired
	private GlobalJournalLibararyMapper globalJournalLibararyMapper;
	

	@Override
	public JpaRepository<EOGlobalJournalLibarary, Long> getRepository() {
		return globalJournalLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalJournalLibarary, UIGlobalJournalLibarary> getMapper() {
		return globalJournalLibararyMapper;
	}
	
	@Override
	public void preAdd(UIGlobalJournalLibarary data, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		LOGGER.info("pre add");
		data.setRecordState(RecordStatus.ACTIVETED.getStatus());
	}
	
	@Override
	public void preUpdate(UIGlobalJournalLibarary data, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		if(data.getRecordState()==null) {
			data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		}
	}
	
	@Override
	public void merge(UIGlobalJournalLibarary dtoObject, EOGlobalJournalLibarary entityObject,
			UIGlobalJournalLibarary updateDtoObject, EOGlobalJournalLibarary updateEntityObject,
			Map<String, List<String>> headers) {
		super.merge(dtoObject, entityObject, updateDtoObject, updateEntityObject, headers);
	}

	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		if(filters!=null && !filters.containsKey(RECORD_STATE)) {
			filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
		}
	}
	
	@Override
	public Boolean deleteById(Long id) {
		Optional<EOGlobalJournalLibarary> findById = getRepository().findById(id);
		if(findById.isPresent()) {
			EOGlobalJournalLibarary eoGlobalJournalLibarary = findById.get();
			eoGlobalJournalLibarary.setRecordState(DataStatus.DACTIVETED.getStatus());
			getRepository().save(eoGlobalJournalLibarary);
			return true;
		}
		return false;
	}
}
