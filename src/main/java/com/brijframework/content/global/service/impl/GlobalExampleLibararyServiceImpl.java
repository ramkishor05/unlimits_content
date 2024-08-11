package com.brijframework.content.global.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;
import org.unlimits.rest.repository.CustomPredicate;

import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.global.entities.EOGlobalExampleItem;
import com.brijframework.content.global.entities.EOGlobalExampleLibarary;
import com.brijframework.content.global.entities.EOGlobalExampleVisualize;
import com.brijframework.content.global.entities.EOGlobalSubCategory;
import com.brijframework.content.global.mapper.GlobalExampleItemMapper;
import com.brijframework.content.global.mapper.GlobalExampleLibararyMapper;
import com.brijframework.content.global.mapper.GlobalExampleVisualizeMapper;
import com.brijframework.content.global.model.UIGlobalExampleItem;
import com.brijframework.content.global.model.UIGlobalExampleLibarary;
import com.brijframework.content.global.model.UIGlobalExampleVisualize;
import com.brijframework.content.global.repository.GlobalExampleItemRepository;
import com.brijframework.content.global.repository.GlobalExampleLibararyRepository;
import com.brijframework.content.global.repository.GlobalExampleVisualizeRepository;
import com.brijframework.content.global.repository.GlobalImageLibararyRepository;
import com.brijframework.content.global.repository.GlobalTagLibararyRepository;
import com.brijframework.content.global.repository.GlobalTenureRepository;
import com.brijframework.content.global.service.GlobalExampleLibararyService;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

@Service
public class GlobalExampleLibararyServiceImpl extends CrudServiceImpl<UIGlobalExampleLibarary, EOGlobalExampleLibarary, Long> implements GlobalExampleLibararyService {
	
	private static final String RECORD_STATE = "recordState";
	
	@Autowired
	private GlobalExampleLibararyRepository globalExampleLibararyRepository;
	
	@Autowired
	private GlobalExampleLibararyMapper globalExampleLibararyMapper;
	
	@Autowired
	private GlobalExampleVisualizeMapper globalExampleVisualizeMapper;
	
	@Autowired
	private GlobalExampleItemMapper globalExampleItemMapper;
	
	@Autowired 
	private GlobalTenureRepository  globalTenureRepository;
	
	@Autowired 
	private GlobalImageLibararyRepository  globalImageLibararyRepository;
	
	@Autowired 
	private GlobalTagLibararyRepository  globalTagLibararyRepository;
	
	@Autowired
	private GlobalExampleItemRepository globalExampleItemRepository;
	
	@Autowired
	private GlobalExampleVisualizeRepository globalExampleVisualizeRepository;
	
	@Value("${openapi.service.url}")
	private String serverUrl;
	
	@Override
	public JpaRepository<EOGlobalExampleLibarary, Long> getRepository() {
		return globalExampleLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalExampleLibarary, UIGlobalExampleLibarary> getMapper() {
		return globalExampleLibararyMapper;
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
	
	@Override
	public void init(List<EOGlobalExampleLibarary> eoGlobalExampleLibararyJson) {

		eoGlobalExampleLibararyJson.forEach(eoGlobalExampleLibarary -> {
			List<EOGlobalExampleItem> exampleItems = new ArrayList<EOGlobalExampleItem>(eoGlobalExampleLibarary.getExampleItems()) ;
			eoGlobalExampleLibarary.getExampleItems().clear();
			
			List<EOGlobalExampleVisualize> exampleVisualizes = new ArrayList<EOGlobalExampleVisualize>(eoGlobalExampleLibarary.getVisualizeItems()) ;
			eoGlobalExampleLibarary.getVisualizeItems().clear();
			
			EOGlobalExampleLibarary findGlobalExampleLibarary = globalExampleLibararyRepository
					.findByIdenNo(eoGlobalExampleLibarary.getIdenNo()).orElse(eoGlobalExampleLibarary);
			BeanUtils.copyProperties(eoGlobalExampleLibarary, findGlobalExampleLibarary, "id");
			findGlobalExampleLibarary.setRecordState(RecordStatus.ACTIVETED.getStatus());
			EOGlobalExampleLibarary eoGlobalExampleItemSave = globalExampleLibararyRepository
					.saveAndFlush(findGlobalExampleLibarary);
			eoGlobalExampleLibarary.setId(eoGlobalExampleItemSave.getId());
			Map<String, EOGlobalExampleItem> exampleItemMap = globalExampleItemRepository.findAllByExampleLibararyId(eoGlobalExampleItemSave.getId()).stream().collect(Collectors.toMap((eoGlobalExampleItem)->getExampleItemId(eoGlobalExampleItem), eoGlobalExampleItem->eoGlobalExampleItem));
			
			exampleItems.forEach(eoExampleItem->{
				EOGlobalExampleItem findExampleItem = exampleItemMap.getOrDefault(getExampleItemId(eoExampleItem), eoExampleItem);
				BeanUtils.copyProperties(eoExampleItem, findExampleItem, "id");
				findExampleItem.setExampleLibarary(eoGlobalExampleItemSave);
				findExampleItem.setRecordState(RecordStatus.ACTIVETED.getStatus());
				globalExampleItemRepository.saveAndFlush(findExampleItem);
			});

			Map<String, EOGlobalExampleVisualize> exampleVisualizeMap = globalExampleVisualizeRepository.findAllByExampleLibararyId(eoGlobalExampleItemSave.getId()).stream().collect(Collectors.toMap((exampleVisualize)->getExampleVisualizeId(exampleVisualize), (exampleVisualize)->exampleVisualize));
			exampleVisualizes.forEach(eoVisualizeItem->{
				EOGlobalExampleVisualize findVisualizeItem = exampleVisualizeMap.getOrDefault(getExampleVisualizeId(eoVisualizeItem), eoVisualizeItem);
				BeanUtils.copyProperties(eoVisualizeItem, findVisualizeItem, "id");
				findVisualizeItem.setRecordState(RecordStatus.ACTIVETED.getStatus());
				findVisualizeItem.setExampleLibarary(eoGlobalExampleItemSave);
				globalExampleVisualizeRepository.saveAndFlush(findVisualizeItem);
			});
		});
	}
	

	private String getExampleItemId(EOGlobalExampleItem exampleItem) {
		return exampleItem.getTenure().getId()+"_"+(exampleItem.getImageLibarary()!=null? exampleItem.getImageLibarary().getId(): 0 )+"_"+(exampleItem.getTagLibarary()!=null? exampleItem.getTagLibarary().getId(): 0);
	}

	private String getExampleVisualizeId(EOGlobalExampleVisualize exampleVisualize) {
		return exampleVisualize.getTenure().getId()+"";
	}

	
	@Override
	public void preAdd(UIGlobalExampleLibarary data, EOGlobalExampleLibarary entity,
			Map<String, List<String>> headers) {
		saveExampleItems(data, entity);
	}
	
	@Override
	public void preUpdate(UIGlobalExampleLibarary data, EOGlobalExampleLibarary entity,
			Map<String, List<String>> headers) {
		saveExampleItems(data, entity);
	}

	private void saveExampleItems(UIGlobalExampleLibarary data, EOGlobalExampleLibarary entity) {
		Map<Integer, UIGlobalExampleVisualize> visualizeMap = data.getVisualizeMap();
		if(!CollectionUtils.isEmpty(visualizeMap)) {
			List<EOGlobalExampleVisualize> eoGlobalExampleVisualizes=new ArrayList<EOGlobalExampleVisualize>();
			visualizeMap.entrySet().forEach(entry->{
				EOGlobalExampleVisualize eoGlobalExampleVisualize = globalExampleVisualizeMapper.mapToDAO(entry.getValue());
				eoGlobalExampleVisualize.setTenure(globalTenureRepository.findOneByYear(entry.getKey()));
				eoGlobalExampleVisualize.setExampleLibarary(entity);
				eoGlobalExampleVisualizes.add(eoGlobalExampleVisualize);
			});
			entity.setVisualizeItems(eoGlobalExampleVisualizes);
		}
		List<UIGlobalExampleItem> exampleItems = data.getExampleItems();
		if(!CollectionUtils.isEmpty(exampleItems)) {
			List<EOGlobalExampleItem> eoGlobalExampleItems=new ArrayList<EOGlobalExampleItem>();
			exampleItems.forEach(exampleItem->{
				EOGlobalExampleItem eoGlobalExampleItem = globalExampleItemMapper.mapToDAO(exampleItem);
				if(exampleItem.getYear()!=null) {
					eoGlobalExampleItem.setTenure(globalTenureRepository.findOneByYear(exampleItem.getYear()));
				}
				if(exampleItem.getImageLibararyId()!=null) {
					eoGlobalExampleItem.setImageLibarary(globalImageLibararyRepository.getReferenceById(exampleItem.getImageLibararyId()));
				}
				if(exampleItem.getTagLibararyId()!=null) {
					eoGlobalExampleItem.setTagLibarary(globalTagLibararyRepository.getReferenceById(exampleItem.getTagLibararyId()));
				}
				eoGlobalExampleItem.setExampleLibarary(entity);
				eoGlobalExampleItems.add(eoGlobalExampleItem);
			});
			entity.setExampleItems(eoGlobalExampleItems);
		}
	}

	public void postFetch(EOGlobalExampleLibarary findObject, UIGlobalExampleLibarary dtoObject) {
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
			Map<Integer, UIGlobalExampleVisualize> visualizeMap = visualizeItems.stream().collect(Collectors.toMap(visualizeItem->visualizeItem.getTenure().getYear(), (visualizeItem)->globalExampleVisualizeMapper.mapToDTO(visualizeItem)));
			dtoObject.setVisualizeMap(visualizeMap);
		}
		
		List<EOGlobalExampleItem> exampleItems = findObject.getExampleItems();
		if(!CollectionUtils.isEmpty(exampleItems)) {
			List<UIGlobalExampleItem> exampleDTOItems = exampleItems.stream().map(exampleItem->{
				UIGlobalExampleItem uiGlobalExampleItem= globalExampleItemMapper.mapToDTO(exampleItem);
				if(exampleItem.getTenure()!=null) {
					uiGlobalExampleItem.setYear(exampleItem.getTenure().getYear());
				}
				return uiGlobalExampleItem;
			}).toList();
			dtoObject.setExampleItems(exampleDTOItems);
		}
	}
	
	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters) {
		filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
	}

	@Override
	public List<UIGlobalExampleLibarary> postFetch(List<EOGlobalExampleLibarary> findObjects) {
		List<UIGlobalExampleLibarary> uiObjects = super.postFetch(findObjects);
		uiObjects.sort((op1,op2)->op1.getOrderSequence().compareTo(op2.getOrderSequence()));
		return uiObjects;
	}
	
	
}
