package com.brijframework.content.global.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.brijframework.json.schema.factories.JsonSchemaFile;
import org.brijframework.json.schema.factories.JsonSchemaObject;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import com.google.common.util.concurrent.AtomicDouble;

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
	public void postUpdate(UIGlobalImageLibarary data, EOGlobalImageLibarary entity,
			Map<String, List<String>> headers) {
		saveTagImageMappingList(data, entity);
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
			saveImageTagMappings(eoGlobalSubCategory, entity, data.getName());
		}
	}
	
	private void saveResource(UIGlobalImageLibarary data, EOGlobalImageLibarary find) {
		ignoreProperties().clear();
		ignoreProperties().add(getPrimaryKey());
		UIResourceModel resource = data.getFileResource();
		if(resource!=null && BuilderUtil.isValidResource(resource)) {
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
	
	private EOGlobalTagLibarary findOrCreateTagLibarary(EOGlobalSubCategory globalCategoryItem, String tagName) {
		EOGlobalTagLibarary tag = globalTagLibararyRepository.findBySubCategoryAndName(globalCategoryItem, tagName);
		if(tag!=null) {
			return tag;
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
			EOGlobalTagLibarary eoGlobalTagLibarary=globalTagLibararyRepository.findBySubCategoryAndName(globalCategoryItem, name);
			return eoGlobalTagLibarary;
		}catch (Exception e) {
			e.printStackTrace();
			EOGlobalTagLibarary eoGlobalTagLibarary=globalTagLibararyRepository.findBySubCategoryAndName(globalCategoryItem, name);
			return eoGlobalTagLibarary;
		}
	}
	private void saveImageTagMappings(EOGlobalSubCategory subCategory, EOGlobalImageLibarary globalImageLibarary, String tagNames) {
		String finalTags = StringUtil.capitalFirstLetterText(tagNames.replace("_", " "));
		for(String tagName :finalTags.split(",")) {
			String finalTag=tagName.trim();
			createTagMappingLibarary(subCategory, globalImageLibarary, finalTag);
		}
	}

	private EOGlobalTagImageMapping createTagMappingLibarary(EOGlobalSubCategory subCategory, EOGlobalImageLibarary imageLibarary, String name) {
		EOGlobalTagLibarary tagLibarary = findOrCreateTagLibarary(subCategory, name);
		EOGlobalTagImageMapping globalTagImageMapping = globalTagImageMappingRepository.findOneByImageLibararyIdAndTagLibararyId(imageLibarary.getId(),tagLibarary.getId()).orElse(new EOGlobalTagImageMapping());
		globalTagImageMapping.setImageLibarary(imageLibarary);
		globalTagImageMapping.setTagLibarary(tagLibarary);
		globalTagImageMapping.setRecordState(RecordStatus.ACTIVETED.getStatus());
		return globalTagImageMappingRepository.saveAndFlush(globalTagImageMapping);
	}
	
	public void export() {
		File dirs = new File("C:/app_runs/unlimits-resources/resource/global_portal_image_libarary");
		if (!dirs.exists()) {
			dirs.mkdirs();
		}
		String global_portal_image_libarary_file_name = "/global_portal_image_libarary";
		globalImageLibararyRepository.findAll().stream()
		.collect(Collectors.groupingBy(globalTagLibarary -> globalTagLibarary.getSubCategory()))
		.forEach((subCategory, globalTagLibararyList) -> {
			buildImageLibarary(dirs, global_portal_image_libarary_file_name, subCategory, globalTagLibararyList);
		});
	}
	
	protected void buildImageLibarary(File dirs, String global_portal_image_libarary_file_name,
			EOGlobalSubCategory subCategory, List<EOGlobalImageLibarary> globalImageLibararyList) {
		String fileName = global_portal_image_libarary_file_name + "_" + subCategory.getMainCategory().getName() + "_"
				+ BuilderUtil.replaceContent(subCategory.getName()) + ".json";
		JsonSchemaFile jsonSchemaFile = new JsonSchemaFile();
		jsonSchemaFile.setId("Global_Portal_ImageLibarary" + "_" + subCategory.getName());
		jsonSchemaFile.setOrderSequence(subCategory.getOrderSequence());
		AtomicDouble counter=new AtomicDouble(subCategory.getOrderSequence());
		globalImageLibararyList.forEach(globalImageLibarary -> {
			String idenNo = BuilderUtil.buildImageLibararyIdenNo(subCategory, globalImageLibarary);
			JsonSchemaObject jsonObject = new JsonSchemaObject();
			jsonObject.setId(idenNo);
			jsonObject.setName("Global_Portal_ImageLibarary");
			jsonObject.setType(globalImageLibarary.getClass().getName());
			jsonSchemaFile.setType(globalImageLibarary.getClass().getName());
			jsonObject.getProperties().put("idenNo", idenNo);
			jsonObject.getProperties().put("name", globalImageLibarary.getName());
			jsonObject.getProperties().put("orderSequence", counter.getAndAdd(0.1));
			jsonObject.getProperties().put("color", subCategory.getColor());
			jsonObject.getProperties().put("type", globalImageLibarary.getType());
			jsonObject.getProperties().put("imageUrl", globalImageLibarary.getImageUrl());
			jsonObject.getProperties().put("subCategory",
					"LK@" + BuilderUtil.buildSubCategoryIdenNo(subCategory.getMainCategory(), subCategory));
			jsonSchemaFile.getObjects().add(jsonObject);
		});
		ObjectMapper mapper = new ObjectMapper();
		try {
			Files.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchemaFile),
					new File(dirs, fileName), Charset.defaultCharset());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
