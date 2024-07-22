package com.brijframework.content.global.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.content.constants.DataStatus;
import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.global.entities.EOGlobalPromptLibarary;
import com.brijframework.content.global.mapper.GlobalPromptLibararyMapper;
import com.brijframework.content.global.model.UIGlobalPromptLibarary;
import com.brijframework.content.global.repository.GlobalPromptLibararyRepository;
import com.brijframework.content.global.service.GlobalPromptLibararyService;

@Service
public class GlobalPromptLibararyServiceImpl  extends CrudServiceImpl<UIGlobalPromptLibarary, EOGlobalPromptLibarary, Long> implements GlobalPromptLibararyService {
	
	private static final String RECORD_STATE = "recordState";

	@Autowired
	private GlobalPromptLibararyRepository globalPromptLibararyRepository;
	
	@Autowired
	private GlobalPromptLibararyMapper globalPromptLibararyMapper;
	

	@Override
	public JpaRepository<EOGlobalPromptLibarary, Long> getRepository() {
		return globalPromptLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalPromptLibarary, UIGlobalPromptLibarary> getMapper() {
		return globalPromptLibararyMapper;
	}

	@Override
	public List<UIGlobalPromptLibarary> findAllByType(String type, MultiValueMap<String, String> headers) {
		return getMapper().mapToDTO(globalPromptLibararyRepository.findAllByType(type));
	}
	
	@Override
	public void preAdd(UIGlobalPromptLibarary data, Map<String, List<String>> headers) {
		if(data.getRecordState()==null) {
			data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		}
	}
	
	@Override
	public void preUpdate(UIGlobalPromptLibarary data, Map<String, List<String>> headers) {
		if(data.getRecordState()==null) {
			data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		}
	}
	
	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters) {
		if(filters!=null && !filters.containsKey(RECORD_STATE)) {
			filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
		}
	}
	
	@Override
	public Boolean delete(Long id) {
		Optional<EOGlobalPromptLibarary> findById = getRepository().findById(id);
		if(findById.isPresent()) {
			EOGlobalPromptLibarary eoGlobalPromptLibarary = findById.get();
			eoGlobalPromptLibarary.setRecordState(DataStatus.DACTIVETED.getStatus());
			getRepository().save(eoGlobalPromptLibarary);
			return true;
		}
		return false;
	}
}
