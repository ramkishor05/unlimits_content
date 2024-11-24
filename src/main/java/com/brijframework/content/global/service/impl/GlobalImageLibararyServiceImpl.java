package com.brijframework.content.global.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.brijframework.util.text.StringUtil;
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
import com.brijframework.content.constants.VisualiseType;
import com.brijframework.content.exceptions.InvalidParameterException;
import com.brijframework.content.forgin.repository.ResourceClient;
import com.brijframework.content.global.entities.EOGlobalImageLibarary;
import com.brijframework.content.global.entities.EOGlobalSubCategory;
import com.brijframework.content.global.entities.EOGlobalTagImageMapping;
import com.brijframework.content.global.entities.EOGlobalTagLibarary;
import com.brijframework.content.global.mapper.GlobalImageLibararyMapper;
import com.brijframework.content.global.mapper.GlobalTagLibararyMapper;
import com.brijframework.content.global.model.UIGlobalImageLibarary;
import com.brijframework.content.global.model.UIGlobalTagModel;
import com.brijframework.content.global.repository.GlobalImageLibararyRepository;
import com.brijframework.content.global.repository.GlobalSubCategoryRepository;
import com.brijframework.content.global.repository.GlobalTagImageMappingRepository;
import com.brijframework.content.global.repository.GlobalTagLibararyRepository;
import com.brijframework.content.global.service.GlobalImageLibararyService;
import com.brijframework.content.resource.modal.UIResourceModel;
import com.brijframework.content.util.BuilderUtil;
import com.brijframework.content.util.CommanUtil;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

@Service
public class GlobalImageLibararyServiceImpl extends CrudServiceImpl<UIGlobalImageLibarary, EOGlobalImageLibarary, Long> implements GlobalImageLibararyService {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(GlobalImageLibararyServiceImpl.class);

	private static final String RECORD_STATE = "recordState";

	private static final String IMAGE_URL = "imageUrl";

	private static final String POSTER_URL = "posterUrl";

	private static final String TAGS_WITH_IMAGES = "tags_with_images";

	@Autowired
	private GlobalImageLibararyRepository globalImageLibararyRepository;
	
	@Autowired
	private GlobalSubCategoryRepository globalCategoryItemRepository;
	
	@Autowired
	private GlobalTagLibararyRepository globalTagLibararyRepository;

	@Autowired
	private GlobalTagImageMappingRepository globalTagImageMappingRepository;
	
	@Autowired
	private GlobalTagLibararyMapper globalTagLibararyMapper;
	
	@Autowired
	private GlobalImageLibararyMapper globalImageLibararyMapper;
	
	@Autowired
	private ResourceClient resourceClient;
	
	@Value("${openapi.service.url}")
	private String serverUrl;

	@Override
	public JpaRepository<EOGlobalImageLibarary, Long> getRepository() {
		return globalImageLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalImageLibarary, UIGlobalImageLibarary> getMapper() {
		return globalImageLibararyMapper;
	}
	
	{
		CustomPredicate<EOGlobalImageLibarary> subCategoryId = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
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
		
		CustomPredicate<EOGlobalImageLibarary> subCategoryName= (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			try {
				Subquery<EOGlobalSubCategory> subquery = criteriaQuery.subquery(EOGlobalSubCategory.class);
				Root<EOGlobalSubCategory> fromProject = subquery.from(EOGlobalSubCategory.class);
				subquery.select(fromProject).where(criteriaBuilder.like(fromProject.get("name").as(String.class), "%"+filter.getColumnValue()+"%"));
				Path<Object> subCategoryIdPath = root.get("subCategory");
				In<Object> subCategoryIdIn = criteriaBuilder.in(subCategoryIdPath);
				subCategoryIdIn.value(subquery);
				return subCategoryIdIn;
			}catch (Exception e) {
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
	public void preAdd(UIGlobalImageLibarary data, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		data.setRecordState(RecordStatus.ACTIVETED.getStatus());
	}
	
	@Override
	public void preAdd(UIGlobalImageLibarary data, EOGlobalImageLibarary entity, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		saveResource(data, entity);
	}
	
	@Override
	public void postAdd(UIGlobalImageLibarary data, EOGlobalImageLibarary entity) {
		EOGlobalSubCategory eoGlobalSubCategory = globalCategoryItemRepository.findById(data.getSubCategoryId()).orElseThrow(()->new InvalidParameterException("Subcategory not found"));
		data.setIdenNo(BuilderUtil.buildTagLibararyIdenNo(eoGlobalSubCategory, data.getName()));
		saveTagImageMappingList(data, entity);
	}

	@Override
	public void preUpdate(UIGlobalImageLibarary data, EOGlobalImageLibarary entity, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		if(data.getRecordState()==null) {
			data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		}
		saveResource(data, entity);
	}
	
	@Override
	public void merge(UIGlobalImageLibarary dtoObject, EOGlobalImageLibarary entityObject,
			UIGlobalImageLibarary updateDtoObject, EOGlobalImageLibarary updateEntityObject,
			Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		saveTagImageMappingList(dtoObject, updateEntityObject);
	}
	
	@Override
	public void merge(UIGlobalImageLibarary dtoObject, EOGlobalImageLibarary entityObject,
			UIGlobalImageLibarary updateDtoObject, EOGlobalImageLibarary updateEntityObject,
			Map<String, List<String>> headers, Map<String, Object> filters) {
		saveTagImageMappingList(dtoObject, updateEntityObject);
	}
	
	@Override
	public void merge(UIGlobalImageLibarary dtoObject, EOGlobalImageLibarary entityObject,
			UIGlobalImageLibarary updateDtoObject, EOGlobalImageLibarary updateEntityObject,
			Map<String, List<String>> headers) {
		saveTagImageMappingList(dtoObject, updateEntityObject);
	}
	
	@Override
	public void merge(UIGlobalImageLibarary dtoObject, EOGlobalImageLibarary entityObject,
			UIGlobalImageLibarary updateDtoObject, EOGlobalImageLibarary updateEntityObject) {
		saveTagImageMappingList(dtoObject, updateEntityObject);
	}
	
	
	private void saveTagImageMappingList(UIGlobalImageLibarary data, EOGlobalImageLibarary entity) {
		List<UIGlobalTagModel> uiTagList = data.getTagList();
		if(!CollectionUtils.isEmpty(uiTagList)) {
			globalTagImageMappingRepository.deleteAllByImageLibararyId(entity.getId());
			List<EOGlobalTagImageMapping> eoTagImageList = new ArrayList<EOGlobalTagImageMapping>();
			uiTagList.forEach(uiTagLibarary->{
				EOGlobalTagImageMapping globalTagImageMapping=new EOGlobalTagImageMapping();
				globalTagImageMapping.setImageLibarary(entity);
				globalTagImageMapping.setTagLibarary(globalTagLibararyMapper.modelToDAO(uiTagLibarary));
				eoTagImageList.add(globalTagImageMapping);
			});
			globalTagImageMappingRepository.saveAllAndFlush(eoTagImageList);
		} else {
			globalTagImageMappingRepository.deleteAllByImageLibararyId(entity.getId());
			EOGlobalSubCategory eoGlobalSubCategory = globalCategoryItemRepository.findById(data.getSubCategoryId()).orElseThrow(()->new InvalidParameterException("SubCategoryId is required!!"));
			saveImageTagMappings(eoGlobalSubCategory, entity, data.getTitle());
		}
	}
	
	private void saveResource(UIGlobalImageLibarary data, EOGlobalImageLibarary find) {
		ignoreProperties().clear();
		ignoreProperties().add(getPrimaryKey());
		UIResourceModel resource = data.getFileResource();
		if(resource!=null && BuilderUtil.isValidResource(resource)) {
			if(StringUtil.isEmpty(data.getName())) {
				data.setName(resource.getFileName());
				find.setName(resource.getFileName());
				data.setTitle(resource.getFileName().split("\\.")[0]);
				find.setTitle(resource.getFileName().split("\\.")[0]);
			}
			resource.setId(find!=null? find.getResourceId(): null);
			StringBuilder dir=new StringBuilder(TAGS_WITH_IMAGES);
			globalCategoryItemRepository.findById(data.getSubCategoryId()).ifPresent(globalCategoryItem->{
				dir.append("/"+BuilderUtil.replaceContent(globalCategoryItem.getName()));
			});
			if(StringUtil.isNonEmpty(data.getType())){
				dir.append("/"+BuilderUtil.replaceContent(data.getType()));
			};
			resource.setFolderName(dir.toString());
			UIResourceModel resourceFile =resourceClient.add(resource);
			data.setResourceId(resourceFile.getId());
			find.setResourceId(resourceFile.getId());
			if(BuilderUtil.isValidFile(resource)) {
				data.setImageUrl(resourceFile.getFileUrl());
				find.setImageUrl(resourceFile.getFileUrl());
			}else {
				ignoreProperties().add(IMAGE_URL);
			}
			if(BuilderUtil.isValidPoster(resource)) {
				data.setPosterUrl(resourceFile.getPosterUrl());
			}else {
				ignoreProperties().add(POSTER_URL);
			}
		}else {
			ignoreProperties().add(POSTER_URL);
			ignoreProperties().add(IMAGE_URL);
		}
	}
	
	@Override
	public List<String> ignoreProperties() {
		List<String> ignoreProperties = super.ignoreProperties();
		ignoreProperties.add(POSTER_URL);
		ignoreProperties.add(IMAGE_URL);
		return ignoreProperties;
	}
	
	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		if(filters!=null && !filters.containsKey(RECORD_STATE)) {
			filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
		}
	}

	@Override
	public void postFetch(EOGlobalImageLibarary findObject, UIGlobalImageLibarary dtoObject, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		if(StringUtils.isEmpty(dtoObject.getIdenNo())) {
			dtoObject.setIdenNo(findObject.getId()+"");
		}
		if(StringUtils.isNotEmpty(dtoObject.getImageUrl())) {
			dtoObject.setImageUrl(dtoObject.getImageUrl().startsWith("/")? serverUrl+""+dtoObject.getImageUrl() :  serverUrl+"/"+dtoObject.getImageUrl());
		}
		
		if(StringUtils.isNotEmpty(dtoObject.getPosterUrl())) {
			dtoObject.setPosterUrl(dtoObject.getPosterUrl().startsWith("/")? serverUrl+""+dtoObject.getPosterUrl() :  serverUrl+"/"+dtoObject.getPosterUrl());
		}
		List<EOGlobalTagImageMapping> tagImageMappings = globalTagImageMappingRepository.findAllByImageLibararyId(findObject.getId(), RecordStatus.ACTIVETED.getStatusIds());
		if(!CollectionUtils.isEmpty(tagImageMappings)) {
			List<UIGlobalTagModel> tagMappingForTagList = globalImageLibararyMapper.tagMappingForImageList(tagImageMappings);
			dtoObject.setTagList(tagMappingForTagList);
		}
	}

	@Override
	public Boolean deleteById(Long id) {
		Optional<EOGlobalImageLibarary> findById = getRepository().findById(id);
		if(findById.isPresent()) {
			EOGlobalImageLibarary eoGlobalImageLibarary = findById.get();
			eoGlobalImageLibarary.setRecordState(DataStatus.DACTIVETED.getStatus());
			getRepository().save(eoGlobalImageLibarary);
			globalTagImageMappingRepository.deleteAllByImageLibararyId(id);
			return true;
		}
		return false;
	}


	@Override
	public void saveImageTagMappings(EOGlobalSubCategory subCategory, EOGlobalImageLibarary globalImageLibarary, String tagNames) {
		String finalTags = CommanUtil.ignoreEndWithNumber(StringUtil.capitalFirstLetterText(tagNames.replace("_", " ")));
		createTagMappingLibarary(subCategory, globalImageLibarary, finalTags);
	}

	private EOGlobalTagImageMapping createTagMappingLibarary(EOGlobalSubCategory subCategory, EOGlobalImageLibarary imageLibarary, String name) {
		EOGlobalTagLibarary tagLibarary = findOrCreateTagLibarary(subCategory, name);
		EOGlobalTagImageMapping globalTagImageMapping = globalTagImageMappingRepository.findOneByImageLibararyIdAndTagLibararyId(imageLibarary.getId(),tagLibarary.getId()).orElse(new EOGlobalTagImageMapping());
		globalTagImageMapping.setImageLibarary(imageLibarary);
		globalTagImageMapping.setTagLibarary(tagLibarary);
		globalTagImageMapping.setRecordState(RecordStatus.ACTIVETED.getStatus());
		return globalTagImageMappingRepository.saveAndFlush(globalTagImageMapping);
	}

	private EOGlobalTagLibarary findOrCreateTagLibarary(EOGlobalSubCategory globalCategoryItem, String tagName) {
		List<EOGlobalTagLibarary> tags = globalTagLibararyRepository.findBySubCategoryAndName(globalCategoryItem.getId(), tagName);
		if(!CollectionUtils.isEmpty(tags)) {
			return tags.get(0);
		}
		return createTagLibarary(globalCategoryItem, tagName);
	}
	
	private EOGlobalTagLibarary createTagLibarary(EOGlobalSubCategory globalCategoryItem, String name){
		try {
			EOGlobalTagLibarary eoGlobalTagLibarary= new EOGlobalTagLibarary();
			eoGlobalTagLibarary.setIdenNo(BuilderUtil.buildTagLibararyIdenNo(globalCategoryItem, name));
			eoGlobalTagLibarary.setName(name);
			eoGlobalTagLibarary.setSubCategory(globalCategoryItem);
			eoGlobalTagLibarary.setRecordState(RecordStatus.ACTIVETED.getStatus());
			eoGlobalTagLibarary.setType(VisualiseType.IMAGE_PAGE.getType());
			eoGlobalTagLibarary=globalTagLibararyRepository.saveAndFlush(eoGlobalTagLibarary);
			return eoGlobalTagLibarary;
		}catch (RuntimeException e) {
			e.printStackTrace();
			List<EOGlobalTagLibarary> tags = globalTagLibararyRepository.findBySubCategoryAndName(globalCategoryItem.getId(), name);
			if(!CollectionUtils.isEmpty(tags)) {
				return tags.get(0);
			}
		}catch (Exception e) {
			e.printStackTrace();
			List<EOGlobalTagLibarary> tags = globalTagLibararyRepository.findBySubCategoryAndName(globalCategoryItem.getId(), name);
			if(!CollectionUtils.isEmpty(tags)) {
				return tags.get(0);
			}
		}
		return null;
	}
}
