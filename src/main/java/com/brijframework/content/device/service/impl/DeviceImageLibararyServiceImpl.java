package com.brijframework.content.device.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.QueryServiceImpl;
import org.unlimits.rest.repository.CustomPredicate;

import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.device.mapper.DeviceImageLibararyMapper;
import com.brijframework.content.device.model.UIDeviceImageLibarary;
import com.brijframework.content.device.model.UIDeviceTagModel;
import com.brijframework.content.device.service.DeviceImageLibararyService;
import com.brijframework.content.global.entities.EOGlobalImageLibarary;
import com.brijframework.content.global.entities.EOGlobalSubCategory;
import com.brijframework.content.global.entities.EOGlobalTagImageMapping;
import com.brijframework.content.global.repository.GlobalImageLibararyRepository;
import com.brijframework.content.global.repository.GlobalTagImageMappingRepository;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

@Service
public class DeviceImageLibararyServiceImpl extends QueryServiceImpl<UIDeviceImageLibarary, EOGlobalImageLibarary, Long> implements DeviceImageLibararyService {
	
	private static final String RECORD_STATE = "recordState";
	
	@Autowired
	private GlobalImageLibararyRepository globalImageLibararyRepository;
	
	@Autowired
	private GlobalTagImageMappingRepository globalTagImageMappingRepository;

	@Autowired
	private DeviceImageLibararyMapper deviceImageLibararyMapper;

	@Value("${openapi.service.url}")
	private String serverUrl;
	
	@Override
	public JpaRepository<EOGlobalImageLibarary, Long> getRepository() {
		return globalImageLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalImageLibarary, UIDeviceImageLibarary> getMapper() {
		return deviceImageLibararyMapper;
	}
	
	{
		CustomPredicate<EOGlobalImageLibarary> subCategoryId = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Subquery<EOGlobalSubCategory> subquery = criteriaQuery.subquery(EOGlobalSubCategory.class);
			Root<EOGlobalSubCategory> fromProject = subquery.from(EOGlobalSubCategory.class);
			subquery.select(fromProject).where(criteriaBuilder.equal(fromProject.get("id"), filter.getColumnValue()));
			Path<Object> subCategoryIdPath = root.get("subCategory");
			In<Object> subCategoryIdIn = criteriaBuilder.in(subCategoryIdPath);
			subCategoryIdIn.value(subquery);
			return subCategoryIdIn;
		};
		
		CustomPredicate<EOGlobalImageLibarary> subCategoryName= (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Subquery<EOGlobalSubCategory> subquery = criteriaQuery.subquery(EOGlobalSubCategory.class);
			Root<EOGlobalSubCategory> fromProject = subquery.from(EOGlobalSubCategory.class);
			subquery.select(fromProject).where(criteriaBuilder.like(fromProject.get("name"), "%"+filter.getColumnValue()+"%"));
			Path<Object> subCategoryIdPath = root.get("subCategory");
			In<Object> subCategoryIdIn = criteriaBuilder.in(subCategoryIdPath);
			subCategoryIdIn.value(subquery);
			return subCategoryIdIn;
		};
		
		addCustomPredicate("subCategoryId", subCategoryId);
		addCustomPredicate("subCategory.id", subCategoryId);
		addCustomPredicate("subCategoryName", subCategoryName);
		addCustomPredicate("subCategory.name", subCategoryName);
	}
	
	@Override
	public List<String> getTypes(Long subCategoryId) {
		return globalImageLibararyRepository.findTypeBySubCategoryId(subCategoryId);
	}

	public void postFetch(EOGlobalImageLibarary findObject, UIDeviceImageLibarary dtoObject) {
		if(StringUtils.isEmpty(dtoObject.getIdenNo())) {
			dtoObject.setIdenNo(findObject.getId()+"");
		}
		if(StringUtils.isNotEmpty(dtoObject.getImageUrl())) {
			dtoObject.setImageUrl(dtoObject.getImageUrl().startsWith("/")? serverUrl+""+dtoObject.getImageUrl() :  serverUrl+"/"+dtoObject.getImageUrl());
		}
		List<EOGlobalTagImageMapping> tagMappingList = globalTagImageMappingRepository.findAllByImageLibararyId(findObject.getId());
		if(!CollectionUtils.isEmpty(tagMappingList)) {
			List<UIDeviceTagModel> tagMappingForTagList = deviceImageLibararyMapper.tagMappingForTagList(tagMappingList);
			dtoObject.setTagList(tagMappingForTagList);
		}
	}
	
	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters) {
		filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
	}

	@Override
	public List<UIDeviceImageLibarary> postFetch(List<EOGlobalImageLibarary> findObjects) {
		List<UIDeviceImageLibarary> uiObjects = super.postFetch(findObjects);
		uiObjects.sort((op1,op2)->op1.getOrderSequence().compareTo(op2.getOrderSequence()));
		return uiObjects;
	}
	
}
