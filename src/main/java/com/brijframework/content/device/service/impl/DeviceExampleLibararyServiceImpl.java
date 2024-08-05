package com.brijframework.content.device.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.beans.PageDetail;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.QueryServiceImpl;
import org.unlimits.rest.repository.CustomPredicate;

import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.device.mapper.DeviceExampleLibararyMapper;
import com.brijframework.content.device.model.UIDeviceExampleLibarary;
import com.brijframework.content.device.service.DeviceExampleLibararyService;
import com.brijframework.content.forgin.repository.PexelMediaRepository;
import com.brijframework.content.global.entities.EOGlobalExampleLibarary;
import com.brijframework.content.global.entities.EOGlobalSubCategory;
import com.brijframework.content.global.entities.EOGlobalTagLibarary;
import com.brijframework.content.global.repository.GlobalExampleLibararyRepository;
import com.brijframework.content.global.repository.GlobalTagLibararyRepository;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

@Service
public class DeviceExampleLibararyServiceImpl extends QueryServiceImpl<UIDeviceExampleLibarary, EOGlobalExampleLibarary, Long> implements DeviceExampleLibararyService {
	
	private static final String RECORD_STATE = "recordState";
	
	@Autowired
	private GlobalExampleLibararyRepository globalExampleLibararyRepository;
	
	@Autowired
	private GlobalTagLibararyRepository globalTagLibararyRepository;
	
	@Autowired
	private DeviceExampleLibararyMapper deviceExampleLibararyMapper;
	
	@Autowired
	private PexelMediaRepository pexelMediaRepository;
	
	@Value("${openapi.service.url}")
	private String serverUrl;
	
	@Override
	public JpaRepository<EOGlobalExampleLibarary, Long> getRepository() {
		return globalExampleLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalExampleLibarary, UIDeviceExampleLibarary> getMapper() {
		return deviceExampleLibararyMapper;
	}
	
	{
		CustomPredicate<EOGlobalExampleLibarary> subCategoryId = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Subquery<EOGlobalSubCategory> subquery = criteriaQuery.subquery(EOGlobalSubCategory.class);
			Root<EOGlobalSubCategory> fromProject = subquery.from(EOGlobalSubCategory.class);
			subquery.select(fromProject).where(criteriaBuilder.equal(fromProject.get("id"), filter.getColumnValue()));
			Path<Object> subCategoryIdPath = root.get("subCategory");
			In<Object> subCategoryIdIn = criteriaBuilder.in(subCategoryIdPath);
			subCategoryIdIn.value(subquery);
			return subCategoryIdIn;
		};
		
		CustomPredicate<EOGlobalExampleLibarary> subCategoryName= (type, root, criteriaQuery, criteriaBuilder, filter) -> {
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
	
	private List<UIDeviceExampleLibarary> findByPexels(Long subCategoryId, Long tagLibararyId, String name) {
		EOGlobalTagLibarary eoGlobalTagLibarary = globalTagLibararyRepository.getReferenceById(tagLibararyId);
		if(eoGlobalTagLibarary!=null) {
			name=eoGlobalTagLibarary.getSubCategory().getName()+" "+eoGlobalTagLibarary.getName();
		}
		List<UIDeviceExampleLibarary>deviceExampleLibararies=new ArrayList<UIDeviceExampleLibarary>();
		try {
			pexelMediaRepository.getAllFiles(name).forEach(photo->{ 
				UIDeviceExampleLibarary deviceExampleLibarary=new UIDeviceExampleLibarary();
				deviceExampleLibarary.setIdenNo(photo.getId());
				deviceExampleLibararies.add(deviceExampleLibarary); 
			});
		}catch (Exception e) {
			e.printStackTrace();
		}
		return deviceExampleLibararies;
	}

	public void postFetch(EOGlobalExampleLibarary findObject, UIDeviceExampleLibarary dtoObject) {
		if(StringUtils.isEmpty(dtoObject.getIdenNo())) {
			dtoObject.setIdenNo(findObject.getId()+"");
		}
	}
	
	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters) {
		filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
	}

	@Override
	public List<UIDeviceExampleLibarary> postFetch(List<EOGlobalExampleLibarary> findObjects) {
		List<UIDeviceExampleLibarary> uiObjects = super.postFetch(findObjects);
		uiObjects.sort((op1,op2)->op1.getOrderSequence().compareTo(op2.getOrderSequence()));
		return uiObjects;
	}

	private PageDetail findByPexels(Long subCategoryId, Long tagLibararyId, int pageNumber, int count) {
		String name = "";
		EOGlobalTagLibarary eoGlobalTagLibarary = globalTagLibararyRepository.getReferenceById(tagLibararyId);
		if(eoGlobalTagLibarary!=null) {
			name=eoGlobalTagLibarary.getSubCategory().getName()+" "+eoGlobalTagLibarary.getName();
		}
		try {
			return pexelMediaRepository.getPageDetail(name, pageNumber, count);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private PageDetail findByPexels(Long subCategoryId, Long tagLibararyId, String name, int pageNumber, int count) {
		EOGlobalTagLibarary eoGlobalTagLibarary = globalTagLibararyRepository.getReferenceById(tagLibararyId);
		if(eoGlobalTagLibarary!=null) {
			name=eoGlobalTagLibarary.getSubCategory().getName()+" "+eoGlobalTagLibarary.getName()+" "+name;
		}
		try {
			return pexelMediaRepository.getPageDetail(name, pageNumber, count);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
