package com.brijframework.content.global.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.brijframework.content.forgin.repository.ResourceClient;
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

	/**
	 * 
	 */
	private static final String PROFILE_PICTURE_URL = "profilePictureURL";

	private static final String RECORD_STATE = "recordState";

	private static final String EXAMPLE = "examples";

	private static final String POSTER_URL = "posterUrl";

	@Autowired
	private GlobalExampleLibararyRepository globalExampleLibararyRepository;

	@Autowired
	private GlobalExampleLibararyMapper globalExampleLibararyMapper;

	@Autowired
	private GlobalExampleVisualizeMapper globalExampleVisualizeMapper;

	@Autowired
	private GlobalExampleItemMapper globalExampleItemMapper;

	@Autowired
	private GlobalTenureRepository globalTenureRepository;

	@Autowired
	private GlobalImageLibararyRepository globalImageLibararyRepository;

	@Autowired
	private GlobalTagLibararyRepository globalTagLibararyRepository;

	@Autowired
	private GlobalExampleItemRepository globalExampleItemRepository;

	@Autowired
	private GlobalExampleVisualizeRepository globalExampleVisualizeRepository;

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
	public void init(List<EOGlobalExampleLibarary> eoGlobalExampleLibararyJson) {

		eoGlobalExampleLibararyJson.forEach(eoGlobalExampleLibarary -> {
			List<EOGlobalExampleItem> exampleItems = new ArrayList<EOGlobalExampleItem>(
					eoGlobalExampleLibarary.getExampleItems());
			eoGlobalExampleLibarary.getExampleItems().clear();

			List<EOGlobalExampleVisualize> exampleVisualizes = new ArrayList<EOGlobalExampleVisualize>(
					eoGlobalExampleLibarary.getVisualizeItems());
			eoGlobalExampleLibarary.getVisualizeItems().clear();

			EOGlobalExampleLibarary findGlobalExampleLibarary = globalExampleLibararyRepository
					.findByIdenNo(eoGlobalExampleLibarary.getIdenNo()).orElse(eoGlobalExampleLibarary);
			BeanUtils.copyProperties(eoGlobalExampleLibarary, findGlobalExampleLibarary, "id");
			findGlobalExampleLibarary.setRecordState(RecordStatus.ACTIVETED.getStatus());
			EOGlobalExampleLibarary eoGlobalExampleItemSave = globalExampleLibararyRepository
					.saveAndFlush(findGlobalExampleLibarary);
			eoGlobalExampleLibarary.setId(eoGlobalExampleItemSave.getId());
			Map<String, EOGlobalExampleItem> exampleItemMap = globalExampleItemRepository
					.findAllByExampleLibararyId(eoGlobalExampleItemSave.getId()).stream()
					.collect(Collectors.toMap((eoGlobalExampleItem) -> getExampleItemId(eoGlobalExampleItem),
							eoGlobalExampleItem -> eoGlobalExampleItem));

			exampleItems.forEach(eoExampleItem -> {
				EOGlobalExampleItem findExampleItem = exampleItemMap.getOrDefault(getExampleItemId(eoExampleItem),
						eoExampleItem);
				BeanUtils.copyProperties(eoExampleItem, findExampleItem, "id");
				findExampleItem.setExampleLibarary(eoGlobalExampleItemSave);
				findExampleItem.setRecordState(RecordStatus.ACTIVETED.getStatus());
				globalExampleItemRepository.saveAndFlush(findExampleItem);
			});

			Map<String, EOGlobalExampleVisualize> exampleVisualizeMap = globalExampleVisualizeRepository
					.findAllByExampleLibararyId(eoGlobalExampleItemSave.getId()).stream()
					.collect(Collectors.toMap((exampleVisualize) -> getExampleVisualizeId(exampleVisualize),
							(exampleVisualize) -> exampleVisualize));
			exampleVisualizes.forEach(eoVisualizeItem -> {
				EOGlobalExampleVisualize findVisualizeItem = exampleVisualizeMap
						.getOrDefault(getExampleVisualizeId(eoVisualizeItem), eoVisualizeItem);
				BeanUtils.copyProperties(eoVisualizeItem, findVisualizeItem, "id");
				findVisualizeItem.setRecordState(RecordStatus.ACTIVETED.getStatus());
				findVisualizeItem.setExampleLibarary(eoGlobalExampleItemSave);
				globalExampleVisualizeRepository.saveAndFlush(findVisualizeItem);
			});
		});
	}

	private String getExampleItemId(EOGlobalExampleItem exampleItem) {
		return exampleItem.getTenure().getYear() + "_"
				+ (exampleItem.getImageLibarary() != null ? exampleItem.getImageLibarary().getId() : 0) + "_"
				+ (exampleItem.getTagLibarary() != null ? exampleItem.getTagLibarary().getId() : 0);
	}

	private Object getExampleItemId(UIGlobalExampleItem exampleItem) {
		return exampleItem.getYear() + "_"
				+ (exampleItem.getImageLibararyId() != null ? exampleItem.getImageLibararyId()
						: exampleItem.getTagLibararyId());
	}

	private String getExampleVisualizeId(EOGlobalExampleVisualize exampleVisualize) {
		return exampleVisualize.getTenure().getYear() + "";
	}

	private String getExampleVisualizeId(UIGlobalExampleVisualize exampleVisualize) {
		return exampleVisualize.getVisualizeYear() + "";
	}

	@Override
	public void preAdd(UIGlobalExampleLibarary data, Map<String, List<String>> headers, Map<String, Object> filters,
			Map<String, Object> actions) {
		globalExampleLibararyRepository
				.findBySubCategoryIdAndProfileName(data.getSubCategoryId(), data.getProfileName())
				.ifPresent(globalSubCategory -> {
					data.setId(globalSubCategory.getId());
				});
		data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		data.setProfilePictureURL(null);
		data.setPosterUrl(null);
	}

	@Override
	public void preAdd(UIGlobalExampleLibarary data, EOGlobalExampleLibarary entity, Map<String, List<String>> headers,
			Map<String, Object> filters, Map<String, Object> actions) {
		saveResource(data, entity);
	}

	@Override
	public void preUpdate(UIGlobalExampleLibarary data, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		data.setProfilePictureURL(null);
		data.setPosterUrl(null);
	}

	@Override
	public void preUpdate(UIGlobalExampleLibarary data, EOGlobalExampleLibarary entity,
			Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		saveResource(data, entity);
	}

	@Override
	public void merge(UIGlobalExampleLibarary dtoObject, EOGlobalExampleLibarary entityObject,
			UIGlobalExampleLibarary updateDtoObject, EOGlobalExampleLibarary updateEntityObject,
			Map<String, List<String>> headers) {
		saveExampleItems(dtoObject, updateEntityObject);
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
			find.setResourceId(resourceFile.getId());
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

	private void saveExampleItems(UIGlobalExampleLibarary data, EOGlobalExampleLibarary entity) {
		Map<Integer, UIGlobalExampleVisualize> visualizeMap = data.getVisualizeMap();

		if (!CollectionUtils.isEmpty(visualizeMap)) {
			globalExampleVisualizeRepository.deleteByExampleLibararyId(entity.getId());
			Map<String, EOGlobalExampleVisualize> exampleVisualizeMap = globalExampleVisualizeRepository
					.findAllByExampleLibararyId(entity.getId()).stream()
					.collect(Collectors.toMap((exampleVisualize) -> getExampleVisualizeId(exampleVisualize),
							(exampleVisualize) -> exampleVisualize));
			List<EOGlobalExampleVisualize> eoGlobalExampleVisualizes = new ArrayList<EOGlobalExampleVisualize>();
			visualizeMap.entrySet().forEach(entry -> {
				EOGlobalExampleVisualize eoGlobalExampleVisualize = exampleVisualizeMap.getOrDefault(
						getExampleVisualizeId(entry.getValue()),
						globalExampleVisualizeMapper.mapToDAO(entry.getValue()));
				eoGlobalExampleVisualize.setRecordState(RecordStatus.ACTIVETED.getStatus());
				eoGlobalExampleVisualize.setTenure(globalTenureRepository.findOneByYear(entry.getKey()).orElse(null));
				eoGlobalExampleVisualize.setExampleLibarary(entity);
				eoGlobalExampleVisualizes.add(eoGlobalExampleVisualize);
			});
			entity.setVisualizeItems(globalExampleVisualizeRepository.saveAll(eoGlobalExampleVisualizes));
		}
		List<UIGlobalExampleItem> exampleItems = data.getExampleItems();
		if (!CollectionUtils.isEmpty(exampleItems)) {
			globalExampleItemRepository.deleteByExampleLibararyId(entity.getId());
			Map<String, EOGlobalExampleItem> exampleItemMap = globalExampleItemRepository
					.findAllByExampleLibararyId(entity.getId()).stream()
					.collect(Collectors.toMap((eoGlobalExampleItem) -> getExampleItemId(eoGlobalExampleItem),
							eoGlobalExampleItem -> eoGlobalExampleItem));
			List<EOGlobalExampleItem> eoGlobalExampleItems = new ArrayList<EOGlobalExampleItem>();
			exampleItems.forEach(exampleItem -> {
				EOGlobalExampleItem eoGlobalExampleItem = exampleItemMap.getOrDefault(getExampleItemId(exampleItem),
						globalExampleItemMapper.mapToDAO(exampleItem));
				if (exampleItem.getYear() != null) {
					eoGlobalExampleItem
							.setTenure(globalTenureRepository.findOneByYear(exampleItem.getYear()).orElse(null));
				}
				if (exampleItem.getImageLibararyId() != null) {
					eoGlobalExampleItem.setImageLibarary(
							globalImageLibararyRepository.getReferenceById(exampleItem.getImageLibararyId()));
				}
				if (exampleItem.getTagLibararyId() != null) {
					eoGlobalExampleItem.setTagLibarary(
							globalTagLibararyRepository.getReferenceById(exampleItem.getTagLibararyId()));
				}
				eoGlobalExampleItem.setExampleLibarary(entity);
				eoGlobalExampleItem.setRecordState(RecordStatus.ACTIVETED.getStatus());
				eoGlobalExampleItems.add(eoGlobalExampleItem);
			});
			entity.setExampleItems(globalExampleItemRepository.saveAll(eoGlobalExampleItems));
		}
	}

	public void postFetch(EOGlobalExampleLibarary findObject, UIGlobalExampleLibarary dtoObject) {
		if (StringUtils.isEmpty(findObject.getIdenNo())) {
			dtoObject.setIdenNo(findObject.getId() + "");
		}
		if (StringUtils.isNotEmpty(findObject.getPosterUrl())) {
			dtoObject
					.setPosterUrl(findObject.getPosterUrl().startsWith("/") ? serverUrl + "" + findObject.getPosterUrl()
							: serverUrl + "/" + findObject.getPosterUrl());
		}
		if (StringUtils.isNotEmpty(findObject.getProfilePictureURL())) {
			dtoObject.setProfilePictureURL(findObject.getProfilePictureURL().startsWith("/")
					? serverUrl + "" + findObject.getProfilePictureURL()
					: serverUrl + "/" + findObject.getProfilePictureURL());
		}
		List<EOGlobalExampleVisualize> visualizeItems = findObject.getVisualizeItems();
		if (!CollectionUtils.isEmpty(visualizeItems)) {
			Map<Integer, UIGlobalExampleVisualize> visualizeMap = visualizeItems.stream()
					.collect(Collectors.toMap(visualizeItem -> visualizeItem.getTenure().getYear(),
							(visualizeItem) -> globalExampleVisualizeMapper.mapToDTO(visualizeItem)));
			dtoObject.setVisualizeMap(visualizeMap);
		}

		List<EOGlobalExampleItem> exampleItems = findObject.getExampleItems();
		if (!CollectionUtils.isEmpty(exampleItems)) {
			List<UIGlobalExampleItem> exampleDTOItems = exampleItems.stream().map(exampleItem -> {
				UIGlobalExampleItem uiGlobalExampleItem = globalExampleItemMapper.mapToDTO(exampleItem);
				if (exampleItem.getTenure() != null) {
					uiGlobalExampleItem.setYear(exampleItem.getTenure().getYear());
				}
				return uiGlobalExampleItem;
			}).toList();
			dtoObject.setExampleItems(exampleDTOItems);
		}
	}

	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
	}

	@Override
	public List<UIGlobalExampleLibarary> postFetch(List<EOGlobalExampleLibarary> findObjects,
			Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		List<UIGlobalExampleLibarary> uiObjects = super.postFetch(findObjects, headers, filters, actions);
		uiObjects.sort((op1, op2) -> op1.getOrderSequence().compareTo(op2.getOrderSequence()));
		return uiObjects;
	}

}
