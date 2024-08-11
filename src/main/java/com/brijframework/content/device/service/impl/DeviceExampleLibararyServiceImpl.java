package com.brijframework.content.device.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.brijframework.content.device.mapper.DeviceExampleItemMapper;
import com.brijframework.content.device.mapper.DeviceExampleLibararyMapper;
import com.brijframework.content.device.mapper.DeviceExampleVisualizeMapper;
import com.brijframework.content.device.model.UIDeviceExampleItemModel;
import com.brijframework.content.device.model.UIDeviceExampleModel;
import com.brijframework.content.device.model.UIDeviceExampleVisualize;
import com.brijframework.content.device.service.DeviceExampleLibararyService;
import com.brijframework.content.global.entities.EOGlobalExampleItem;
import com.brijframework.content.global.entities.EOGlobalExampleLibarary;
import com.brijframework.content.global.entities.EOGlobalExampleVisualize;
import com.brijframework.content.global.entities.EOGlobalSubCategory;
import com.brijframework.content.global.repository.GlobalExampleLibararyRepository;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

@Service
public class DeviceExampleLibararyServiceImpl extends QueryServiceImpl<UIDeviceExampleModel, EOGlobalExampleLibarary, Long> implements DeviceExampleLibararyService {
	
	private static final String RECORD_STATE = "recordState";
	
	@Autowired
	private GlobalExampleLibararyRepository globalExampleLibararyRepository;

	@Autowired
	private DeviceExampleLibararyMapper deviceExampleLibararyMapper;
	
	@Autowired
	private DeviceExampleItemMapper deviceExampleItemMapper;
	
	@Autowired
	private DeviceExampleVisualizeMapper deviceExampleVisualizeMapper;
	
	@Value("${openapi.service.url}")
	private String serverUrl;
	
	@Override
	public JpaRepository<EOGlobalExampleLibarary, Long> getRepository() {
		return globalExampleLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalExampleLibarary, UIDeviceExampleModel> getMapper() {
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
	
	public void postFetch(EOGlobalExampleLibarary findObject, UIDeviceExampleModel dtoObject) {
		if(StringUtils.isEmpty(dtoObject.getIdenNo())) {
			dtoObject.setIdenNo(findObject.getId()+"");
		}
		if(StringUtils.isEmpty(dtoObject.getIdenNo())) {
			dtoObject.setIdenNo(findObject.getId()+"");
		}
		if(StringUtils.isNotEmpty(dtoObject.getPosterUrl())) {
			dtoObject.setPosterUrl(dtoObject.getPosterUrl().startsWith("/")? serverUrl+""+dtoObject.getPosterUrl() :  serverUrl+"/"+dtoObject.getPosterUrl());
		}
		if(StringUtils.isNotEmpty(dtoObject.getProfilePictureURL())) {
			dtoObject.setProfilePictureURL(dtoObject.getProfilePictureURL().startsWith("/")? serverUrl+""+dtoObject.getProfilePictureURL() :  serverUrl+"/"+dtoObject.getProfilePictureURL());
		}
		List<EOGlobalExampleVisualize> visualizeItems = findObject.getVisualizeItems();
		if(!CollectionUtils.isEmpty(visualizeItems)) {
			Map<Integer, UIDeviceExampleVisualize> visualizeMap = visualizeItems.stream().collect(Collectors.toMap(visualizeItem->visualizeItem.getTenure().getYear(), (visualizeItem)->{
				UIDeviceExampleVisualize deviceExampleVisualize=deviceExampleVisualizeMapper.mapToDTO(visualizeItem);  
				return deviceExampleVisualize;
			}));
			dtoObject.setVisualizeMap(visualizeMap);
		}
		
		List<EOGlobalExampleItem> exampleItems = findObject.getExampleItems();
		if(!CollectionUtils.isEmpty(exampleItems)) {
			List<UIDeviceExampleItemModel> exampleDTOItems = exampleItems.stream().map(exampleItem->{
				UIDeviceExampleItemModel deviceExampleItemModel= deviceExampleItemMapper.mapToDTO(exampleItem);
				if(exampleItem.getTenure()!=null) {
					deviceExampleItemModel.setYear(exampleItem.getTenure().getYear());
				}
				return deviceExampleItemModel;
			}).toList();
			dtoObject.setExampleItems(exampleDTOItems);
		}
	}


	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters) {
		filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
	}

	@Override
	public List<UIDeviceExampleModel> postFetch(List<EOGlobalExampleLibarary> findObjects) {
		List<UIDeviceExampleModel> uiObjects = super.postFetch(findObjects);
		uiObjects.sort((op1,op2)->op1.getOrderSequence().compareTo(op2.getOrderSequence()));
		return uiObjects;
	}

}
