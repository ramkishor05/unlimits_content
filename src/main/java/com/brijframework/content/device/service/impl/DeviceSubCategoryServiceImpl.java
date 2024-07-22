package com.brijframework.content.device.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
			Subquery<EOGlobalMainCategory> subquery = criteriaQuery.subquery(EOGlobalMainCategory.class);
			Root<EOGlobalMainCategory> fromProject = subquery.from(EOGlobalMainCategory.class);
			subquery.select(fromProject).where(criteriaBuilder.equal(fromProject.get("id"), filter.getColumnValue()));
			Path<Object> subCategoryIdPath = root.get("mainCategory");
			In<Object> subCategoryIdIn = criteriaBuilder.in(subCategoryIdPath);
			subCategoryIdIn.value(subquery);
			return subCategoryIdIn;
		};
		
		CustomPredicate<EOGlobalSubCategory> mainCategoryName= (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Subquery<EOGlobalMainCategory> subquery = criteriaQuery.subquery(EOGlobalMainCategory.class);
			Root<EOGlobalMainCategory> fromProject = subquery.from(EOGlobalMainCategory.class);
			subquery.select(fromProject).where(criteriaBuilder.like(fromProject.get("name"), "%"+filter.getColumnValue()+"%"));
			Path<Object> subCategoryIdPath = root.get("mainCategory");
			In<Object> subCategoryIdIn = criteriaBuilder.in(subCategoryIdPath);
			subCategoryIdIn.value(subquery);
			return subCategoryIdIn;
		};
		 
		addCustomPredicate("mainCategoryId", mainCategoryId);
		addCustomPredicate("mainCategory.id", mainCategoryId);
		addCustomPredicate("mainCategoryName", mainCategoryName);
		addCustomPredicate("mainCategory.name", mainCategoryName);
	}
	

	@Override
	public List<UIDeviceSubCategory> findAllByMainCategoryId(Long mainCategoryId) {
		return postFetch(globalCategoryItemRepository.findAllByMainCategoryId(mainCategoryId));
	}
	
	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters) {
		filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
	}
	
	@Override
	public void postFetch(EOGlobalSubCategory findObject, UIDeviceSubCategory dtoObject) {
		if(StringUtils.isNotEmpty(dtoObject.getLogoUrl())) {
			dtoObject.setLogoUrl(dtoObject.getLogoUrl().startsWith("/")? serverUrl+""+dtoObject.getLogoUrl() :  serverUrl+"/"+dtoObject.getLogoUrl());
		}
	}

	@Override
	public List<UIDeviceSubCategory> postFetch(List<EOGlobalSubCategory> findObjects) {
		List<UIDeviceSubCategory> uiObjects = super.postFetch(findObjects);
		uiObjects.sort((op1,op2)->op1.getOrderSequence().compareTo(op2.getOrderSequence()));
		return uiObjects;
	}
}
