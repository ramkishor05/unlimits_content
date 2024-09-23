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
import com.brijframework.content.device.mapper.DeviceSubCategoryMapper;
import com.brijframework.content.device.model.UIDeviceSubCategory;
import com.brijframework.content.device.service.DeviceSubCategoryService;
import com.brijframework.content.global.entities.EOGlobalMainCategory;
import com.brijframework.content.global.entities.EOGlobalSubCategory;
import com.brijframework.content.global.repository.GlobalSubCategoryRepository;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

@Service
public class DeviceSubCategoryServiceImpl extends QueryServiceImpl<UIDeviceSubCategory, EOGlobalSubCategory, Long> implements DeviceSubCategoryService {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(DeviceSubCategoryServiceImpl.class);

	private static final String RECORD_STATE = "recordState";
	
	@Autowired
	private GlobalSubCategoryRepository globalCategoryItemRepository;
	
	@Autowired
	private DeviceSubCategoryMapper deviceSubCategoryMapper;
	
	@Value("${openapi.service.url}")
	private String serverUrl;

	@Override
	public JpaRepository<EOGlobalSubCategory, Long> getRepository() {
		return globalCategoryItemRepository;
	}

	@Override
	public GenericMapper<EOGlobalSubCategory, UIDeviceSubCategory> getMapper() {
		return deviceSubCategoryMapper;
	}
	

	{
		CustomPredicate<EOGlobalSubCategory> mainCategoryId = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			try {
				Subquery<EOGlobalMainCategory> subquery = criteriaQuery.subquery(EOGlobalMainCategory.class);
				Root<EOGlobalMainCategory> fromProject = subquery.from(EOGlobalMainCategory.class);
				subquery.select(fromProject).where(criteriaBuilder.equal(fromProject.get("id").as(String.class), filter.getColumnValue().toString()));
				Path<Object> subCategoryIdPath = root.get("mainCategory");
				In<Object> subCategoryIdIn = criteriaBuilder.in(subCategoryIdPath);
				subCategoryIdIn.value(subquery);
				return subCategoryIdIn;
			}catch (Exception e) {
				LOGGER.error("WARN: unexpected exception for mainCategoryId: " + filter.getColumnValue(), e);
				return null;
			}
		};
		
		CustomPredicate<EOGlobalSubCategory> mainCategoryName= (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			try {
				Subquery<EOGlobalMainCategory> subquery = criteriaQuery.subquery(EOGlobalMainCategory.class);
				Root<EOGlobalMainCategory> fromProject = subquery.from(EOGlobalMainCategory.class);
				subquery.select(fromProject).where(criteriaBuilder.like(fromProject.get("name").as(String.class), "%"+filter.getColumnValue()+"%"));
				Path<Object> subCategoryIdPath = root.get("mainCategory");
				In<Object> subCategoryIdIn = criteriaBuilder.in(subCategoryIdPath);
				subCategoryIdIn.value(subquery);
				return subCategoryIdIn;
			}catch (Exception e) {
				LOGGER.error("WARN: unexpected exception for mainCategoryName: " + filter.getColumnValue(), e);
				return null;
			}
		};
		 
		addCustomPredicate("mainCategoryId", mainCategoryId);
		addCustomPredicate("mainCategory.id", mainCategoryId);
		addCustomPredicate("mainCategoryName", mainCategoryName);
		addCustomPredicate("mainCategory.name", mainCategoryName);
	}
	

	@Override
	public List<UIDeviceSubCategory> findAllByMainCategoryId(Long mainCategoryId, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		return postFetch(globalCategoryItemRepository.findAllByMainCategoryId(mainCategoryId), headers, filters, actions);
	}
	
	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
	}
	
	@Override
	public void postFetch(EOGlobalSubCategory findObject, UIDeviceSubCategory dtoObject, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		if(StringUtils.isNotEmpty(dtoObject.getLogoUrl())) {
			dtoObject.setLogoUrl(dtoObject.getLogoUrl().startsWith("/")? serverUrl+""+dtoObject.getLogoUrl() :  serverUrl+"/"+dtoObject.getLogoUrl());
		}
	}

	@Override
	public List<UIDeviceSubCategory> postFetch(List<EOGlobalSubCategory> findObjects, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		List<UIDeviceSubCategory> uiObjects = super.postFetch(findObjects, headers, filters, actions);
		uiObjects.sort((op1,op2)->op1.getOrderSequence().compareTo(op2.getOrderSequence()));
		return uiObjects;
	}
}
