package com.brijframework.content.device.service.impl;

import java.util.List;
import java.util.Map;

import org.brijframework.util.text.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.QueryServiceImpl;
import org.unlimits.rest.repository.CustomPredicate;

import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.device.mapper.DeviceTagLibararyMapper;
import com.brijframework.content.device.model.UIDeviceImageModel;
import com.brijframework.content.device.model.UIDeviceTagLibarary;
import com.brijframework.content.device.service.DeviceTagLibararyService;
import com.brijframework.content.global.entities.EOGlobalSubCategory;
import com.brijframework.content.global.entities.EOGlobalTagImageMapping;
import com.brijframework.content.global.entities.EOGlobalTagLibarary;
import com.brijframework.content.global.repository.GlobalTagImageMappingRepository;
import com.brijframework.content.global.repository.GlobalTagLibararyRepository;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

@Service
public class DeviceTagLibararyServiceImpl extends QueryServiceImpl<UIDeviceTagLibarary, EOGlobalTagLibarary, Long> implements DeviceTagLibararyService {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(DeviceTagLibararyServiceImpl.class);

	private static final String RECORD_STATE = "recordState";
	
	@Autowired
	private GlobalTagLibararyRepository globalTagLibararyRepository;
	
	@Autowired
	private DeviceTagLibararyMapper deviceTagLibararyMapper;

	@Autowired
	private GlobalTagImageMappingRepository globalTagImageMappingRepository;

	@Value("${openapi.service.url}")
	private String serverUrl;
	
	@Override
	public JpaRepository<EOGlobalTagLibarary, Long> getRepository() {
		return globalTagLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalTagLibarary, UIDeviceTagLibarary> getMapper() {
		return deviceTagLibararyMapper;
	}
	
	{
		CustomPredicate<EOGlobalTagLibarary> subCategoryId = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			try {
				Subquery<EOGlobalSubCategory> subquery = criteriaQuery.subquery(EOGlobalSubCategory.class);
				Root<EOGlobalSubCategory> fromProject = subquery.from(EOGlobalSubCategory.class);
				subquery.select(fromProject).where(criteriaBuilder.equal(fromProject.get("id").as(String.class), filter.getColumnValue().toString()));
				Path<Object> subCategoryIdPath = root.get("subCategory");
				In<Object> subCategoryIdIn = criteriaBuilder.in(subCategoryIdPath);
				subCategoryIdIn.value(subquery);
				return subCategoryIdIn;
			}catch (Exception e) {
				LOGGER.error("WARN: unexpected exception for subCategoryId: " + filter.getColumnValue(), e);
				return null;
			}
		};
		
		CustomPredicate<EOGlobalTagLibarary> subCategoryName= (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			try {
				Subquery<EOGlobalSubCategory> subquery = criteriaQuery.subquery(EOGlobalSubCategory.class);
				Root<EOGlobalSubCategory> fromProject = subquery.from(EOGlobalSubCategory.class);
				subquery.select(fromProject).where(criteriaBuilder.like(fromProject.get("name").as(String.class), "%"+filter.getColumnValue().toString()+"%"));
				Path<Object> subCategoryIdPath = root.get("subCategory");
				In<Object> subCategoryIdIn = criteriaBuilder.in(subCategoryIdPath);
				subCategoryIdIn.value(subquery);
				return subCategoryIdIn;
			}catch (Exception e) {
				LOGGER.error("WARN: unexpected exception for subCategoryName: " + filter.getColumnValue(), e);
				return null;
			}
		};
		
		CustomPredicate<EOGlobalTagLibarary> tagLibararyId = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			try {
				Subquery<EOGlobalTagLibarary> subquery = criteriaQuery.subquery(EOGlobalTagLibarary.class);
				Root<EOGlobalTagLibarary> fromProject = subquery.from(EOGlobalTagLibarary.class);
				subquery.select(fromProject).where(criteriaBuilder.equal(fromProject.get("id").as(String.class), filter.getColumnValue().toString()));
				Path<Object> subCategoryIdPath = root.get("tagLibarary");
				In<Object> subCategoryIdIn = criteriaBuilder.in(subCategoryIdPath);
				subCategoryIdIn.value(subquery);
				return subCategoryIdIn;
			}catch (Exception e) {
				LOGGER.error("WARN: unexpected exception for tagLibararyId: " + filter.getColumnValue(), e);
				return null;
			}
		};
		
		CustomPredicate<EOGlobalTagLibarary> tagLibararyName= (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			try{
				Subquery<EOGlobalTagLibarary> subquery = criteriaQuery.subquery(EOGlobalTagLibarary.class);
				Root<EOGlobalTagLibarary> fromProject = subquery.from(EOGlobalTagLibarary.class);
				subquery.select(fromProject).where(criteriaBuilder.like(fromProject.get("name").as(String.class), "%"+filter.getColumnValue()+"%"));
				Path<Object> subCategoryIdPath = root.get("tagLibarary");
				In<Object> subCategoryIdIn = criteriaBuilder.in(subCategoryIdPath);
				subCategoryIdIn.value(subquery);
				return subCategoryIdIn;
			}catch (Exception e) {
				LOGGER.error("WARN: unexpected exception for tagLibararyName: " + filter.getColumnValue(), e);
				return null;
			}
		};
 
		addCustomPredicate("subCategoryId", subCategoryId);
		addCustomPredicate("subCategory.id", subCategoryId);
		addCustomPredicate("subCategoryName", subCategoryName);
		addCustomPredicate("subCategory.name", subCategoryName);
		
		addCustomPredicate("tagLibararyId", tagLibararyId);
		addCustomPredicate("tagLibarary.id", tagLibararyId);
		addCustomPredicate("tagLibararyName", tagLibararyName);
		addCustomPredicate("tagLibarary.name", tagLibararyName);
	}

	@Override
	public List<UIDeviceTagLibarary> findAllBySubCategoryId(Long subCategoryId, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		return postFetch(globalTagLibararyRepository.findAllBSubCategoryId(subCategoryId), headers, filters, actions);
	}

	@Override
	public List<UIDeviceTagLibarary> search(Long subCategoryId, String name, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		if(StringUtil.isNonEmpty(name)) {
			return postFetch(globalTagLibararyRepository.search(subCategoryId, name), headers, filters, actions);
		} else {
			return postFetch(globalTagLibararyRepository.findAllBSubCategoryId(subCategoryId), headers, filters, actions);
		}
	}
	
	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
	}
	
	@Override
	public void postFetch(EOGlobalTagLibarary findObject, UIDeviceTagLibarary dtoObject, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		List<EOGlobalTagImageMapping> imageMappingList = globalTagImageMappingRepository.findAllByTagLibararyId(findObject.getId(),  RecordStatus.ACTIVETED.getStatusIds());
		if(!CollectionUtils.isEmpty(imageMappingList)) {
			List<UIDeviceImageModel> tagMappingForImageList = deviceTagLibararyMapper.tagMappingForImageList(imageMappingList);
			for(UIDeviceImageModel uiDeviceImageModel: tagMappingForImageList) {
				uiDeviceImageModel.setImageUrl(uiDeviceImageModel.getImageUrl().startsWith("/")? serverUrl+""+uiDeviceImageModel.getImageUrl() :  serverUrl+"/"+uiDeviceImageModel.getImageUrl());
			}
			dtoObject.setImageList(tagMappingForImageList);
		}
	}

	@Override
	public List<UIDeviceTagLibarary> postFetch(List<EOGlobalTagLibarary> findObjects, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		List<UIDeviceTagLibarary> uiObjects = super.postFetch(findObjects, headers, filters, actions);
		uiObjects.sort((op1,op2)->op1.getOrderSequence().compareTo(op2.getOrderSequence()));
		return uiObjects;
	}
}
