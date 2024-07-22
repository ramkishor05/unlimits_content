package com.brijframework.content.global.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.content.constants.DataStatus;
import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.global.entities.EOGlobalTenure;
import com.brijframework.content.global.mapper.GlobalTenureMapper;
import com.brijframework.content.global.model.UIGlobalTenure;
import com.brijframework.content.global.repository.GlobalTenureRepository;
import com.brijframework.content.global.service.GlobalTenureService;

@Service
public class GlobalTenureServiceImpl extends CrudServiceImpl<UIGlobalTenure, EOGlobalTenure, Long> implements GlobalTenureService {
	
	private static final String RECORD_STATE = "recordState";

	@Autowired
	private GlobalTenureRepository globalTenureRepository;
	
	@Autowired
	private GlobalTenureMapper globalTenureMapper;

	@Override
	public JpaRepository<EOGlobalTenure, Long> getRepository() {
		return globalTenureRepository;
	}

	@Override
	public GenericMapper<EOGlobalTenure, UIGlobalTenure> getMapper() {
		return globalTenureMapper;
	}
	
	@Override
	public void preAdd(UIGlobalTenure data, Map<String, List<String>> headers) {
		if(data.getRecordState()==null) {
			data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		}
	}
	
	@Override
	public void preUpdate(UIGlobalTenure data, Map<String, List<String>> headers) {
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
		Optional<EOGlobalTenure> findById = getRepository().findById(id);
		if(findById.isPresent()) {
			EOGlobalTenure eoGlobalTenure = findById.get();
			eoGlobalTenure.setRecordState(DataStatus.DACTIVETED.getStatus());
			getRepository().save(eoGlobalTenure);
			return true;
		}
		return false;
	}
}
