package com.brijframework.content.device.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.QueryServiceImpl;
import org.unlimits.rest.repository.CustomPredicate;

import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.device.mapper.DeviceReProgramItemMapper;
import com.brijframework.content.device.model.UIDeviceReProgramItem;
import com.brijframework.content.device.service.DeviceReProgramItemService;
import com.brijframework.content.global.entities.EOGlobalReProgramItem;
import com.brijframework.content.global.entities.EOGlobalReProgramLibarary;
import com.brijframework.content.global.repository.GlobalReProgramItemRepository;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

@Service
public class DeviceReprogramItemServiceImpl extends QueryServiceImpl<UIDeviceReProgramItem, EOGlobalReProgramItem, Long> implements DeviceReProgramItemService {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(DeviceReprogramItemServiceImpl.class);

	private static final String RECORD_STATE = "recordState";
	
	@Autowired
	private GlobalReProgramItemRepository globalReProgramItemRepository;
	
	@Autowired
	private DeviceReProgramItemMapper deviceReProgramItemMapper;
	
	@Value("${openapi.service.url}")
	private String serverUrl;
	
	@Override
	public JpaRepository<EOGlobalReProgramItem, Long> getRepository() {
		return globalReProgramItemRepository;
	}

	@Override
	public GenericMapper<EOGlobalReProgramItem, UIDeviceReProgramItem> getMapper() {
		return deviceReProgramItemMapper;
	}
	
	{
		CustomPredicate<EOGlobalReProgramItem> reProgramLibararyId = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			try {
				Subquery<EOGlobalReProgramLibarary> subquery = criteriaQuery.subquery(EOGlobalReProgramLibarary.class);
				Root<EOGlobalReProgramLibarary> fromProject = subquery.from(EOGlobalReProgramLibarary.class);
				subquery.select(fromProject).where(criteriaBuilder.equal(fromProject.get("id"), filter.getColumnValue()));
				Path<Object> reProgramLibararyIdPath = root.get("reProgramLibarary");
				In<Object> reProgramLibararyIdIn = criteriaBuilder.in(reProgramLibararyIdPath);
				reProgramLibararyIdIn.value(subquery);
				return reProgramLibararyIdIn;
			}catch (Exception e) {
				LOGGER.error("WARN: unexpected exception for mainCategoryId: " + filter.getColumnValue(), e);
				return null;
			}
		};
		addCustomPredicate("reProgramLibarary.id", reProgramLibararyId);
		addCustomPredicate("reProgramLibararyId", reProgramLibararyId);
	}
	
	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		LOGGER.warn("preFetch");
		filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
	}
	
	@Override
	public List<UIDeviceReProgramItem> postFetch(List<EOGlobalReProgramItem> findObjects, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		List<UIDeviceReProgramItem> uiObjects = super.postFetch(findObjects, headers, filters, actions);
		uiObjects.sort((op1,op2)->op1.getOrderSequence().compareTo(op2.getOrderSequence()));
		return uiObjects;
	}
	
	@Override
	public void postFetch(EOGlobalReProgramItem findObject, UIDeviceReProgramItem dtoObject, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		if(StringUtils.isEmpty(dtoObject.getIdenNo())) {
			dtoObject.setIdenNo(findObject.getId()+"");
		}
		if(StringUtils.isNotEmpty(dtoObject.getMusicUrl())) {
			dtoObject.setMusicUrl(dtoObject.getMusicUrl().startsWith("/")? serverUrl+""+dtoObject.getMusicUrl() :  serverUrl+"/"+dtoObject.getMusicUrl());
		}
		
		if(StringUtils.isNotEmpty(dtoObject.getPosterUrl())) {
			dtoObject.setPosterUrl(dtoObject.getPosterUrl().startsWith("/")? serverUrl+""+dtoObject.getPosterUrl() :  serverUrl+"/"+dtoObject.getPosterUrl());
		}
	}
}
