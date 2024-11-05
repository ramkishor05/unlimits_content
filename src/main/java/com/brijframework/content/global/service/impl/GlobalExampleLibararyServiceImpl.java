package com.brijframework.content.global.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;
import org.unlimits.rest.repository.CustomPredicate;

import com.brijframework.content.constants.DataStatus;
import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.forgin.repository.ResourceClient;
import com.brijframework.content.global.entities.EOGlobalExampleLibarary;
import com.brijframework.content.global.entities.EOGlobalSubCategory;
import com.brijframework.content.global.mapper.GlobalExampleLibararyMapper;
import com.brijframework.content.global.model.UIGlobalExampleItem;
import com.brijframework.content.global.model.UIGlobalExampleLibarary;
import com.brijframework.content.global.model.UIGlobalExampleVisualize;
import com.brijframework.content.global.repository.GlobalExampleLibararyRepository;
import com.brijframework.content.global.service.GlobalExampleItemService;
import com.brijframework.content.global.service.GlobalExampleLibararyService;
import com.brijframework.content.global.service.GlobalExampleVisualizeService;
import com.brijframework.content.resource.modal.UIResourceModel;
import com.brijframework.content.util.BuilderUtil;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

@Service
public class GlobalExampleLibararyServiceImpl
		extends CrudServiceImpl<UIGlobalExampleLibarary, EOGlobalExampleLibarary, Long>
		implements GlobalExampleLibararyService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExampleLibararyServiceImpl.class);

	private static final String EXAMPLE_LIBARARY_ID = "exampleLibararyId";

	private static final String PROFILE_PICTURE_URL = "profilePictureURL";

	private static final String RECORD_STATE = "recordState";

	private static final String EXAMPLE = "examples";

	private static final String POSTER_URL = "posterUrl";

	@Autowired
	private GlobalExampleLibararyRepository globalExampleLibararyRepository;

	@Autowired
	private GlobalExampleLibararyMapper globalExampleLibararyMapper;

	@Autowired
	private GlobalExampleVisualizeService globalExampleVisualizeService;

	@Autowired
	private GlobalExampleItemService globalExampleItemService;

	@Value("${openapi.service.url}")
	private String serverUrl;

	@Autowired
	private ResourceClient resourceClient;

	@Override
	public JpaRepository<EOGlobalExampleLibarary, Long> getRepository() {
		return globalExampleLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalExampleLibarary, UIGlobalExampleLibarary> getMapper() {
		return globalExampleLibararyMapper;
	}

	{
		CustomPredicate<EOGlobalExampleLibarary> subCategoryId = (type, root, criteriaQuery, criteriaBuilder,
				filter) -> {
			try {
				Subquery<EOGlobalSubCategory> subquery = criteriaQuery.subquery(EOGlobalSubCategory.class);
				Root<EOGlobalSubCategory> fromProject = subquery.from(EOGlobalSubCategory.class);
				subquery.select(fromProject)
						.where(criteriaBuilder.equal(fromProject.get("id").as(String.class), filter.getColumnValue().toString()));
				Path<Object> subCategoryIdPath = root.get("subCategory");
				In<Object> subCategoryIdIn = criteriaBuilder.in(subCategoryIdPath);
				subCategoryIdIn.value(subquery);
				return subCategoryIdIn;
			} catch (Exception e) {
				LOGGER.error("WARN: unexpected exception for subCategoryId: " + filter.getColumnValue(), e);
				return null;
			}
		};

		CustomPredicate<EOGlobalExampleLibarary> subCategoryName = (type, root, criteriaQuery, criteriaBuilder,
				filter) -> {
			try {
				Subquery<EOGlobalSubCategory> subquery = criteriaQuery.subquery(EOGlobalSubCategory.class);
				Root<EOGlobalSubCategory> fromProject = subquery.from(EOGlobalSubCategory.class);
				subquery.select(fromProject)
						.where(criteriaBuilder.like(fromProject.get("name").as(String.class), "%" + filter.getColumnValue() + "%"));
				Path<Object> subCategoryIdPath = root.get("subCategory");
				In<Object> subCategoryIdIn = criteriaBuilder.in(subCategoryIdPath);
				subCategoryIdIn.value(subquery);
				return subCategoryIdIn;
			} catch (Exception e) {
				LOGGER.error("WARN: unexpected exception for subCategoryName: " + filter.getColumnValue(), e);
				return null;
			}
		};

		addCustomPredicate("subCategoryId", subCategoryId);
		addCustomPredicate("subCategory.id", subCategoryId);
		addCustomPredicate("subCategoryName", subCategoryName);
		addCustomPredicate("subCategory.name", subCategoryName);
	}
	
	@Override
	public void preAdd(UIGlobalExampleLibarary data, Map<String, List<String>> headers, Map<String, Object> filters,
			Map<String, Object> actions) {
		LOGGER.warn("pre add: {}", headers);
		globalExampleLibararyRepository
		.findBySubCategoryIdAndProfileName(data.getSubCategoryId(), data.getProfileName())
		.ifPresent(globalSubCategory -> {
			data.setId(globalSubCategory.getId());
		});
		data.setRecordState(RecordStatus.ACTIVETED.getStatus());
	}

	@Override
	public void preAdd(UIGlobalExampleLibarary data, EOGlobalExampleLibarary entity,
			Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		saveResource(data, entity);
	}

	@Override
	public void preUpdate(UIGlobalExampleLibarary data, EOGlobalExampleLibarary find,
			Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		if (data.getRecordState() == null) {
			data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		}
		saveResource(data, find);
	}

	private void saveResource(UIGlobalExampleLibarary data, EOGlobalExampleLibarary find) {
		UIResourceModel resource = data.getProfileResource();
		ignoreProperties().clear();
		ignoreProperties().add(getPrimaryKey());
		if (resource != null && BuilderUtil.isValidResource(resource)) {
			resource.setIncludeId(true);
			resource.setId(find != null ? find.getResourceId() : null);
			resource.setFolderName(EXAMPLE);
			UIResourceModel resourceFile = resourceClient.add(resource);
			resourceFile.setIncludeId(true);
			data.setResourceId(resourceFile.getId());
			if (BuilderUtil.isValidFile(resource)) {
				data.setProfilePictureURL(resourceFile.getFileUrl());
				find.setProfilePictureURL(resourceFile.getFileUrl());
			} else {
				ignoreProperties().add(PROFILE_PICTURE_URL);
			}
			if (BuilderUtil.isValidPoster(resource)) {
				data.setPosterUrl(resourceFile.getPosterUrl());
				find.setPosterUrl(resourceFile.getPosterUrl());
			} else {
				ignoreProperties().add(POSTER_URL);
			}
		} else {
			ignoreProperties().add(POSTER_URL);
			ignoreProperties().add(PROFILE_PICTURE_URL);
		}
	}

	@Override
	public List<String> ignoreProperties() {
		List<String> ignoreProperties = super.ignoreProperties();
		ignoreProperties.add(POSTER_URL);
		ignoreProperties.add(PROFILE_PICTURE_URL);
		return ignoreProperties;
	}

	@Override
	public Boolean deleteById(Long id) {
		Optional<EOGlobalExampleLibarary> findById = getRepository().findById(id);
		if (findById.isPresent()) {
			EOGlobalExampleLibarary eoGlobalReprogramLibarary = findById.get();
			eoGlobalReprogramLibarary.setRecordState(DataStatus.DACTIVETED.getStatus());
			getRepository().save(eoGlobalReprogramLibarary);
			return true;
		}
		return false;
	}
	
	@Override
	public void merge(UIGlobalExampleLibarary dtoObject, EOGlobalExampleLibarary entityObject,
			UIGlobalExampleLibarary updateDtoObject, EOGlobalExampleLibarary updateEntityObject,Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		saveExampleItems(dtoObject, updateEntityObject);
	}

	private void saveExampleItems(UIGlobalExampleLibarary dtoObject, EOGlobalExampleLibarary entity) {
		Map<Integer, UIGlobalExampleVisualize> visualizeDTOMap = dtoObject.getVisualizeMap();
		globalExampleVisualizeService.deleteByExampleLibararyId(entity.getId());
		if (!CollectionUtils.isEmpty(visualizeDTOMap)) {
			List<UIGlobalExampleVisualize> exampleVisualizes=new ArrayList<UIGlobalExampleVisualize>(visualizeDTOMap.values());
			if(exampleVisualizes!=null) {
				exampleVisualizes.forEach(exampleVisualize-> exampleVisualize.setExampleLibararyId(entity.getId()));
				List<UIGlobalExampleVisualize> updateAll = globalExampleVisualizeService.updateAll(exampleVisualizes, new HashMap<String, List<String>>(), new HashMap<String, Object>(), new HashMap<String, Object>());
				Map<Integer, UIGlobalExampleVisualize> visualizeMap=updateAll.stream().collect(Collectors.toMap(globalExampleVisualize->globalExampleVisualize.getVisualizeYear(), Function.identity()));
				dtoObject.setVisualizeMap(visualizeMap);
			}
		}
		List<UIGlobalExampleItem> exampleItems = dtoObject.getExampleItems();
		globalExampleItemService.deleteByExampleLibararyId(entity.getId());
		if (!CollectionUtils.isEmpty(exampleItems)) {
			exampleItems.forEach(exampleItem->exampleItem.setExampleLibararyId(entity.getId()));
			globalExampleItemService.updateAll(exampleItems, new HashMap<String, List<String>>(), new HashMap<String, Object>(), new HashMap<String, Object>());
		}
	}

	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		if (filters != null && !filters.containsKey(RECORD_STATE)) {
			filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
		}
	}

	@Override
	public List<UIGlobalExampleLibarary> postFetch(List<EOGlobalExampleLibarary> findObjects,
			Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		List<UIGlobalExampleLibarary> uiObjects = super.postFetch(findObjects, headers, filters, actions);
		uiObjects.sort((op1, op2) -> op1.getOrderSequence().compareTo(op2.getOrderSequence()));
		return uiObjects;
	}

	@Override
	public void postFetch(EOGlobalExampleLibarary findObject, UIGlobalExampleLibarary dtoObject,
			Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		if (StringUtils.isEmpty(dtoObject.getIdenNo())) {
			dtoObject.setIdenNo(findObject.getId() + "");
		}
		if (StringUtils.isNotEmpty(dtoObject.getProfilePictureURL())) {
			dtoObject.setProfilePictureURL(dtoObject.getProfilePictureURL().startsWith("/") ? serverUrl + "" + dtoObject.getProfilePictureURL()
					: serverUrl + "/" + dtoObject.getProfilePictureURL());
		}
		if (StringUtils.isNotEmpty(dtoObject.getPosterUrl())) {
			dtoObject.setPosterUrl(dtoObject.getPosterUrl().startsWith("/") ? serverUrl + "" + dtoObject.getPosterUrl()
					: serverUrl + "/" + dtoObject.getPosterUrl());
		}
		filters.clear();
		filters.put(EXAMPLE_LIBARARY_ID, findObject.getId());
		List<UIGlobalExampleVisualize> visualizeItems = globalExampleVisualizeService.findAll(headers, filters, actions);
		if (!CollectionUtils.isEmpty(visualizeItems)) {
			Map<Integer, UIGlobalExampleVisualize> visualizeMap = visualizeItems.stream().collect(Collectors.toMap(visualizeItem -> visualizeItem.getVisualizeYear(),(visualizeItem) -> visualizeItem));
			dtoObject.setVisualizeMap(visualizeMap);
		} else {
			dtoObject.setVisualizeMap(new HashMap<Integer, UIGlobalExampleVisualize>());
		}
		List<UIGlobalExampleItem> exampleItems = globalExampleItemService.findAll(headers, filters, actions);
		dtoObject.setExampleItems(exampleItems);
	}


}
