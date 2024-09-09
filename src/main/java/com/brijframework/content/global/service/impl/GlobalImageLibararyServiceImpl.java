package com.brijframework.content.global.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.brijframework.json.schema.factories.JsonSchemaFile;
import org.brijframework.json.schema.factories.JsonSchemaObject;
import org.brijframework.util.text.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
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
import com.brijframework.content.util.IdenUtil;
import com.brijframework.content.util.ResourceUtil;
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

	@Autowired
	private ResourceUtil resourceUtil;
	
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
			Subquery<EOGlobalSubCategory> subquery = criteriaQuery.subquery(EOGlobalSubCategory.class);
			Root<EOGlobalSubCategory> fromProject = subquery.from(EOGlobalSubCategory.class);
			subquery.select(fromProject).where(criteriaBuilder.equal(fromProject.get("id"), filter.getColumnValue()));
			Path<Object> subCategoryIdPath = root.get("subCategory");
			In<Object> subCategoryIdIn = criteriaBuilder.in(subCategoryIdPath);
			subCategoryIdIn.value(subquery);
			return subCategoryIdIn;
		};
		
		CustomPredicate<EOGlobalImageLibarary> subCategoryName= (type, root, criteriaQuery, criteriaBuilder, filter) -> {
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
	public void preAdd(UIGlobalImageLibarary data, Map<String, List<String>> headers) {
		data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		if(data.getFileResource()!=null) {
			saveResource(data , null);
		}
	}
	
	@Override
	public void postAdd(UIGlobalImageLibarary data, EOGlobalImageLibarary entity) {
		EOGlobalSubCategory eoGlobalSubCategory = globalCategoryItemRepository.findById(data.getSubCategoryId()).orElseThrow(()->new InvalidParameterException("Subcategory not found"));
		data.setIdenNo(IdenUtil.buildTagIdenNo(eoGlobalSubCategory, data.getName()));
		saveTagImageMappingList(data, entity);
	}

	@Override
	public void preUpdate(UIGlobalImageLibarary data, EOGlobalImageLibarary entity, Map<String, List<String>> headers) {
		if(data.getRecordState()==null) {
			data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		}
		if(data.getFileResource()!=null) {
			saveResource(data, entity);
		}
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
			Map<String, EOGlobalTagLibarary> globalTagLibararyNameMap = globalTagLibararyRepository.findAll().stream().collect(Collectors.toMap(globalTagLibarary->buildTagId(globalTagLibarary), Function.identity()));
			saveImageTagMappings(entity, data.getName(), globalTagLibararyNameMap);
		}
	}
	
	private void saveResource(UIGlobalImageLibarary data, EOGlobalImageLibarary find) {
		ignoreProperties().clear();
		ignoreProperties().add(getPrimaryKey());
		UIResourceModel resource = data.getFileResource();
		if(resource!=null) {
			resource.setId(find!=null? find.getResourceId(): null);
			StringBuilder dir=new StringBuilder(TAGS_WITH_IMAGES);
			globalCategoryItemRepository.findById(data.getSubCategoryId()).ifPresent(globalCategoryItem->{
				dir.append("/"+globalCategoryItem.getName());
			});
			if(StringUtil.isNonEmpty(data.getType())){
				dir.append("/"+data.getType());
			};
			resource.setFolderName(dir.toString());
			UIResourceModel resourceFile =resourceClient.add(resource);
			data.setResourceId(resourceFile.getId());
			if(StringUtil.isNonEmpty(resource.getFileName()) && StringUtil.isNonEmpty(resource.getFileContent())) {
				data.setImageUrl(resourceFile.getFileUrl());
			}else {
				ignoreProperties().add(IMAGE_URL);
			}
			if(StringUtil.isNonEmpty(resource.getPosterName()) && StringUtil.isNonEmpty(resource.getPosterContent())) {
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
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters) {
		if(filters!=null && !filters.containsKey(RECORD_STATE)) {
			filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
		}
	}

	@Override
	public void postFetch(EOGlobalImageLibarary findObject, UIGlobalImageLibarary dtoObject) {
		if(StringUtils.isEmpty(dtoObject.getIdenNo())) {
			dtoObject.setIdenNo(findObject.getId()+"");
		}
		if(StringUtils.isNotEmpty(dtoObject.getImageUrl())) {
			dtoObject.setImageUrl(dtoObject.getImageUrl().startsWith("/")? serverUrl+""+dtoObject.getImageUrl() :  serverUrl+"/"+dtoObject.getImageUrl());
		}
		
		if(StringUtils.isNotEmpty(dtoObject.getPosterUrl())) {
			dtoObject.setPosterUrl(dtoObject.getPosterUrl().startsWith("/")? serverUrl+""+dtoObject.getPosterUrl() :  serverUrl+"/"+dtoObject.getPosterUrl());
		}
		List<EOGlobalTagImageMapping> tagImageMappings = globalTagImageMappingRepository.findAllByImageLibararyId(findObject.getId());
		if(!CollectionUtils.isEmpty(tagImageMappings)) {
			List<UIGlobalTagModel> tagMappingForTagList = globalImageLibararyMapper.tagMappingForImageList(tagImageMappings);
			dtoObject.setTagList(tagMappingForTagList);
		}
	}

	@Override
	public Boolean delete(Long id) {
		Optional<EOGlobalImageLibarary> findById = getRepository().findById(id);
		if(findById.isPresent()) {
			EOGlobalImageLibarary eoGlobalImageLibarary = findById.get();
			eoGlobalImageLibarary.setRecordState(DataStatus.DACTIVETED.getStatus());
			getRepository().save(eoGlobalImageLibarary);
			return true;
		}
		return false;
	}
	
	@Override
	public void init () throws IOException {
		Resource resource = resourceUtil.getResource(TAGS_WITH_IMAGES);
		File resourceFile = resource.getFile();
		if(!resourceFile.exists()) {
			return;
		}
		List<EOGlobalSubCategory> globalCategoryItems = globalCategoryItemRepository.findAll();
		Map<String, EOGlobalSubCategory> globalCategoryNameMap = globalCategoryItems.stream().collect(Collectors.toMap(globalSubCategory->globalSubCategory.getName().toUpperCase(), Function.identity()));
		List<EOGlobalImageLibarary> globalImageLibararys = globalImageLibararyRepository.findAll();
		Map<Long, List<EOGlobalImageLibarary>> globalCategoryImgMap = globalImageLibararys.stream().collect(Collectors.groupingBy(ImageLibarary->ImageLibarary.getSubCategory().getId()));
		Map<String, EOGlobalTagLibarary> globalTagLibararyNameMap = globalTagLibararyRepository.findAll().stream().collect(Collectors.toMap(globalTagLibarary->buildTagId(globalTagLibarary), Function.identity()));
		loadResourceSubCategory(resourceFile, globalCategoryNameMap, globalCategoryImgMap, globalTagLibararyNameMap);
	}

	private String buildTagId(EOGlobalTagLibarary globalTagLibarary) {
		return buildTagId(globalTagLibarary.getSubCategory(), globalTagLibarary.getName());
	}
	
	private String buildTagId(EOGlobalSubCategory globalSubCategory, String name) {
		return globalSubCategory.getId()+"_"+name;
	}
	
	private void loadResourceSubCategory(File resourceFile, Map<String, EOGlobalSubCategory> globalCategoryNameMap, Map<Long, List<EOGlobalImageLibarary>> globalCategoryImgMap, Map<String, EOGlobalTagLibarary> globalTagLibararyNameMap) {
		File[] subCategoryFolders = resourceFile.listFiles();
		for (File subCategoryFolder :  subCategoryFolders) {
			EOGlobalSubCategory globalCategoryItem = globalCategoryNameMap.get(subCategoryFolder.getName().trim().toUpperCase());
			if(globalCategoryItem==null) {
				continue;
			}
			Map<String, EOGlobalImageLibarary> categoryImgUrlMap = globalCategoryImgMap.getOrDefault(globalCategoryItem.getId(), new ArrayList<EOGlobalImageLibarary>()).stream().collect(Collectors.toMap(EOGlobalImageLibarary::getImageUrl, Function.identity()));
			File[] tagFiles = subCategoryFolder.listFiles();
			for(File tagFile:  tagFiles) {
				if(tagFile.isDirectory()) {
					loadResourceTag(tagFile, globalCategoryItem, tagFile.getName(), categoryImgUrlMap, globalTagLibararyNameMap);
				} else {
					loadResourceFile(tagFile, globalCategoryItem, null, categoryImgUrlMap, globalTagLibararyNameMap);
				}
			}
		}
	}
	
	private void loadResourceTag(File tageFolder, EOGlobalSubCategory globalCategoryItem, String string, Map<String, EOGlobalImageLibarary> categoryImgUrlMap, Map<String, EOGlobalTagLibarary> globalTagLibararyNameMap) {
		File[] tageFiles = tageFolder.listFiles();
		for (File file :  tageFiles) {
			if(file.isDirectory()) {
				loadResourceDir(file, globalCategoryItem, tageFolder.getName(), categoryImgUrlMap, globalTagLibararyNameMap);
			} else {
				loadResourceFile(file, globalCategoryItem, tageFolder.getName(), categoryImgUrlMap, globalTagLibararyNameMap);
			}
		}
	}
	
	/*
	private void loadResourceTag(File tageFile, EOGlobalSubCategory globalCategoryItem, Map<String, EOGlobalImageLibarary> categoryImgUrlMap, Map<String, EOGlobalTagLibarary> globalTagLibararyNameMap) {
		EOGlobalTagLibarary eoGlobalTagLibarary = findOrCreateTagLibarary(globalCategoryItem, tageFile.getName(), globalTagLibararyNameMap);
		File[] tageFiles = tageFile.listFiles();
		for (File file :  tageFiles) {
			if(file.isDirectory()) {
				loadResourceDir(file, globalCategoryItem, eoGlobalTagLibarary, categoryImgUrlMap);
			} else {
				loadResourceFile(file, globalCategoryItem, eoGlobalTagLibarary, categoryImgUrlMap);
			}
		}
	}
	*/
	
	private EOGlobalTagLibarary findOrCreateTagLibarary(EOGlobalSubCategory globalCategoryItem, String name,
			Map<String, EOGlobalTagLibarary> globalTagLibararyNameMap) {
		return globalTagLibararyNameMap.getOrDefault(buildTagId(globalCategoryItem,name),createTagLibarary(globalTagLibararyNameMap, globalCategoryItem, name) );
	}
	
	private EOGlobalTagLibarary createTagLibarary(Map<String, EOGlobalTagLibarary> globalTagLibararyNameMap, EOGlobalSubCategory globalCategoryItem, String name){
		try {
			EOGlobalTagLibarary eoGlobalTagLibarary= new EOGlobalTagLibarary();
			eoGlobalTagLibarary.setIdenNo(IdenUtil.buildTagIdenNo(globalCategoryItem, name));
			eoGlobalTagLibarary.setName(name);
			eoGlobalTagLibarary.setSubCategory(globalCategoryItem);
			eoGlobalTagLibarary.setRecordState(RecordStatus.ACTIVETED.getStatus());
			eoGlobalTagLibarary.setType(VisualiseType.IMAGE_PAGE.getType());
			eoGlobalTagLibarary=globalTagLibararyRepository.save(eoGlobalTagLibarary);
			globalTagLibararyNameMap.put(buildTagId(globalCategoryItem, name), eoGlobalTagLibarary);
			return eoGlobalTagLibarary;
		}catch (RuntimeException e) {
			EOGlobalTagLibarary eoGlobalTagLibarary=globalTagLibararyRepository.findBySubCategoryAndName(globalCategoryItem, name);
			globalTagLibararyNameMap.put(buildTagId(globalCategoryItem, name), eoGlobalTagLibarary);
			return eoGlobalTagLibarary;
		}catch (Exception e) {
			EOGlobalTagLibarary eoGlobalTagLibarary=globalTagLibararyRepository.findBySubCategoryAndName(globalCategoryItem, name);
			globalTagLibararyNameMap.put(buildTagId(globalCategoryItem, name), eoGlobalTagLibarary);
			return eoGlobalTagLibarary;
		}
	}

	private void loadResourceDir(File resourceFile, EOGlobalSubCategory globalCategoryItem, String tageFolder, Map<String, EOGlobalImageLibarary> categoryImgUrlMap, Map<String, EOGlobalTagLibarary> globalTagLibararyNameMap) {
		File[] listFiles = resourceFile.listFiles();
		for (File file :  listFiles) {
			if(file.isDirectory()) {
				loadResourceDir(file, globalCategoryItem, tageFolder, categoryImgUrlMap, globalTagLibararyNameMap);
			} else {
				loadResourceFile(file, globalCategoryItem,tageFolder, categoryImgUrlMap, globalTagLibararyNameMap);
			}
		}
	}

	/**
	 * @param file
	 * @param tageFolder 
	 * @param eoGlobalTagLibarary 
	 * @param globalCategoryItem 
	 * @param categoryImgUrlMap 
	 * @param globalTagLibararyNameMap 
	 */
	private void loadResourceFile(File file, EOGlobalSubCategory subCategory,  String tageFolder, Map<String, EOGlobalImageLibarary> categoryImgUrlMap, Map<String, EOGlobalTagLibarary> globalTagLibararyNameMap) {
		try {
			String url = "resource/"+TAGS_WITH_IMAGES+file.getAbsolutePath().split(TAGS_WITH_IMAGES)[1].replace("\\", "/");
			String title=file.getName().split("\\.")[0];
			EOGlobalImageLibarary globalImageLibarary = categoryImgUrlMap.getOrDefault(url, new EOGlobalImageLibarary());
			globalImageLibarary.setIdenNo(url.replace("/", "_").replace(",", "_").toUpperCase().split("\\.")[0].replaceFirst("_", ""));
			globalImageLibarary.setSubCategory(subCategory);
			globalImageLibarary.setName(file.getName());
			globalImageLibarary.setTitle(title);
			globalImageLibarary.setType(tageFolder);
			globalImageLibarary.setImageUrl(url);
			globalImageLibarary.setRecordState(RecordStatus.ACTIVETED.getStatus());
			globalImageLibarary=globalImageLibararyRepository.save(globalImageLibarary);
			
			saveImageTagMappings(globalImageLibarary, title, globalTagLibararyNameMap);
			
			categoryImgUrlMap.put(url, globalImageLibarary);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveImageTagMappings(EOGlobalImageLibarary globalImageLibarary, String tagNames, Map<String, EOGlobalTagLibarary> globalTagLibararyNameMap) {
		Map<String, EOGlobalTagImageMapping> tagMappingMap = globalImageLibarary.getTagList().stream().collect(Collectors.toMap((tagMapping)->tagMapping.getTagLibarary().getName(), (tagMapping)->tagMapping));
		String finalTags = StringUtil.capitalFirstLetterText(tagNames.replace("_", " "));
		for(String tagName :finalTags.split(",")) {
			String finalTag=tagName.trim();
			EOGlobalTagImageMapping globalTagImageMapping = tagMappingMap.getOrDefault(finalTag, findOrCreateTagMappingLibarary(globalImageLibarary, finalTag, tagMappingMap,  globalTagLibararyNameMap));
			globalTagImageMapping=globalTagImageMappingRepository.saveAndFlush(globalTagImageMapping);
			tagMappingMap.put(finalTag, globalTagImageMapping);
		}
	}
	
	private EOGlobalTagImageMapping findOrCreateTagMappingLibarary(EOGlobalImageLibarary globalImageLibarary, String name,
			Map<String, EOGlobalTagImageMapping> globalTagImageLibararyNameMap, Map<String, EOGlobalTagLibarary> globalTagLibararyNameMap) {
		return globalTagImageLibararyNameMap.getOrDefault(name, createTagMappingLibarary(globalTagImageLibararyNameMap, globalImageLibarary, name, globalTagLibararyNameMap) );
	}

	private EOGlobalTagImageMapping createTagMappingLibarary(
			Map<String, EOGlobalTagImageMapping> globalTagImageLibararyNameMap, EOGlobalImageLibarary imageLibarary,
			String name, Map<String, EOGlobalTagLibarary> globalTagLibararyNameMap) {
		EOGlobalTagLibarary tagLibarary = findOrCreateTagLibarary(imageLibarary.getSubCategory(), name, globalTagLibararyNameMap);
		EOGlobalTagImageMapping globalTagImageMapping=new EOGlobalTagImageMapping();
		globalTagImageMapping.setImageLibarary(imageLibarary);
		globalTagImageMapping.setTagLibarary(tagLibarary);
		globalTagImageMapping.setRecordState(RecordStatus.ACTIVETED.getStatus());
		return globalTagImageMapping;
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
				+ IdenUtil.replaceContent(subCategory.getName()) + ".json";
		JsonSchemaFile jsonSchemaFile = new JsonSchemaFile();
		jsonSchemaFile.setId("Global_Portal_ImageLibarary" + "_" + subCategory.getName());
		jsonSchemaFile.setOrderSequence(subCategory.getOrderSequence());
		AtomicDouble counter=new AtomicDouble(subCategory.getOrderSequence());
		globalImageLibararyList.forEach(globalImageLibarary -> {
			String idenNo = IdenUtil.buildIdenNo(subCategory, globalImageLibarary);
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
					"LK@" + IdenUtil.buildIdenNo(subCategory.getMainCategory(), subCategory));
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
