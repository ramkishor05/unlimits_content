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
import com.brijframework.content.device.mapper.DeviceMindSetItemMapper;
import com.brijframework.content.device.model.UIDeviceMindSetItemModel;
import com.brijframework.content.device.service.DeviceMindSetItemService;
import com.brijframework.content.global.entities.EOGlobalMindSetItem;
import com.brijframework.content.global.entities.EOGlobalMindSetLibarary;
import com.brijframework.content.global.repository.GlobalMindSetItemRepository;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

@Service
public class DeviceMindSetItemServiceImpl extends QueryServiceImpl<UIDeviceMindSetItemModel, EOGlobalMindSetItem, Long> implements DeviceMindSetItemService {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(DeviceMindSetItemServiceImpl.class);

	private static final String RECORD_STATE = "recordState";
	
	@Autowired
	private GlobalMindSetItemRepository globalMindSetItemRepository;
	
	@Autowired
	private DeviceMindSetItemMapper deviceMindSetItemMapper;
	
	@Value("${openapi.service.url}")
	private String serverUrl;
	
	{
		CustomPredicate<EOGlobalMindSetItem> mindSetLibararyId = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			try {
				Subquery<EOGlobalMindSetLibarary> subquery = criteriaQuery.subquery(EOGlobalMindSetLibarary.class);
				Root<EOGlobalMindSetLibarary> fromProject = subquery.from(EOGlobalMindSetLibarary.class);
				subquery.select(fromProject).where(criteriaBuilder.equal(fromProject.get("id"), filter.getColumnValue()));
				Path<Object> mindSetLibararyIdPath = root.get("mindSetLibarary");
				In<Object> mindSetLibararyIdIn = criteriaBuilder.in(mindSetLibararyIdPath);
				mindSetLibararyIdIn.value(subquery);
				return mindSetLibararyIdIn;
			}catch (Exception e) {
				LOGGER.error("WARN: unexpected exception for mainCategoryId: " + filter.getColumnValue(), e);
				return null;
			}
		};
		addCustomPredicate("mindSetLibarary.id", mindSetLibararyId);
		addCustomPredicate("mindSetLibararyId", mindSetLibararyId);
	}
	
	@Override
	public JpaRepository<EOGlobalMindSetItem, Long> getRepository() {
		return globalMindSetItemRepository;
	}

	@Override
	public GenericMapper<EOGlobalMindSetItem, UIDeviceMindSetItemModel> getMapper() {
		return deviceMindSetItemMapper;
	}
	
	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		LOGGER.info("preFetch");
		filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
	}
	
	@Override
	public List<UIDeviceMindSetItemModel> postFetch(List<EOGlobalMindSetItem> findObjects, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		List<UIDeviceMindSetItemModel> uiObjects = super.postFetch(findObjects, headers, filters, actions);
		uiObjects.sort((op1,op2)->op1.getOrderSequence().compareTo(op2.getOrderSequence()));
		return uiObjects;
	}
	
	@Override
	public void postFetch(EOGlobalMindSetItem findObject, UIDeviceMindSetItemModel dtoObject, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
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
