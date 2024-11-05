package com.brijframework.content.global.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.brijframework.json.schema.factories.JsonSchemaFile;
import org.brijframework.json.schema.factories.JsonSchemaObject;
import org.brijframework.util.text.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.constants.ResourceType;
import com.brijframework.content.constants.VisualiseType;
import com.brijframework.content.global.entities.EOGlobalImageLibarary;
import com.brijframework.content.global.entities.EOGlobalSubCategory;
import com.brijframework.content.global.entities.EOGlobalTagImageMapping;
import com.brijframework.content.global.entities.EOGlobalTagLibarary;
import com.brijframework.content.global.mapper.GlobalImageLibararyMapper;
import com.brijframework.content.global.model.UIGlobalImageLibarary;
import com.brijframework.content.global.repository.GlobalImageLibararyRepository;
import com.brijframework.content.global.repository.GlobalSubCategoryRepository;
import com.brijframework.content.global.repository.GlobalTagImageMappingRepository;
import com.brijframework.content.global.repository.GlobalTagLibararyRepository;
import com.brijframework.content.global.service.GlobalImageResourceService;
import com.brijframework.content.util.BuilderUtil;
import com.brijframework.content.util.ResourceUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import com.google.common.util.concurrent.AtomicDouble;

@Service
public class GlobalImageResourceServiceImpl extends CrudServiceImpl<UIGlobalImageLibarary, EOGlobalImageLibarary, Long> implements GlobalImageResourceService {
	
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
	private GlobalImageLibararyMapper globalImageLibararyMapper;
	
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
			globalImageLibarary.setResourceType(ResourceType.EXTENAL.toString());
			globalImageLibarary.setSubCategory(subCategory);
			globalImageLibarary.setName(file.getName());
			globalImageLibarary.setTitle(title);
			globalImageLibarary.setType(tageFolder);
			globalImageLibarary.setImageUrl(url);
			globalImageLibarary.setRecordState(RecordStatus.ACTIVETED.getStatus());
			globalImageLibarary=globalImageLibararyRepository.save(globalImageLibarary);
			saveImageTagMappings(subCategory, globalImageLibarary, title);
			categoryImgUrlMap.put(url, globalImageLibarary);
		}catch (Exception e) {
			e.printStackTrace();
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

	private String buildTagId(EOGlobalTagLibarary globalTagLibarary) {
		return buildTagId(globalTagLibarary.getSubCategory(), globalTagLibarary.getName());
	}
	
	private String buildTagId(EOGlobalSubCategory globalSubCategory, String name) {
		return globalSubCategory.getId()+"_"+name;
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
