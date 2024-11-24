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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.constants.ResourceType;
import com.brijframework.content.global.entities.EOGlobalImageLibarary;
import com.brijframework.content.global.entities.EOGlobalSubCategory;
import com.brijframework.content.global.mapper.GlobalImageLibararyMapper;
import com.brijframework.content.global.model.UIGlobalImageLibarary;
import com.brijframework.content.global.repository.GlobalImageLibararyRepository;
import com.brijframework.content.global.repository.GlobalSubCategoryRepository;
import com.brijframework.content.global.service.GlobalImageLibararyService;
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
	private GlobalSubCategoryRepository globalSubCategoryRepository;
	
	@Autowired
	private GlobalImageLibararyService globalImageLibararyService;
	
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
	public void importData (ResourceType resourceType){
		Resource resource = resourceUtil.getResource(TAGS_WITH_IMAGES);
		try {
			File resourceFile = resource.getFile();
			if(!resourceFile.exists()) {
				return;
			}
			List<EOGlobalSubCategory> globalCategoryItems = globalSubCategoryRepository.findAll();
			Map<String, EOGlobalSubCategory> globalCategoryNameMap = globalCategoryItems.stream().collect(Collectors.toMap(globalSubCategory->globalSubCategory.getName().toUpperCase(), Function.identity()));
			List<EOGlobalImageLibarary> globalImageLibararys = globalImageLibararyRepository.findAll(resourceType.toString());
			Map<Long, List<EOGlobalImageLibarary>> globalCategoryImgMap = globalImageLibararys.stream().collect(Collectors.groupingBy(ImageLibarary->ImageLibarary.getSubCategory().getId()));
			importResourceSubCategory(resourceFile, globalCategoryNameMap, globalCategoryImgMap, resourceType);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void importResourceSubCategory(File resourceFile, Map<String, EOGlobalSubCategory> globalCategoryNameMap, Map<Long, List<EOGlobalImageLibarary>> globalCategoryImgMap, ResourceType resourceType) {
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
					importResourceTag(tagFile, globalCategoryItem, tagFile.getName(), categoryImgUrlMap, resourceType);
				} else {
					importResourceFile(tagFile, globalCategoryItem, null, categoryImgUrlMap, resourceType);
				}
			}
		}
	}
	
	private void importResourceTag(File tageFolder, EOGlobalSubCategory globalCategoryItem, String string, Map<String, EOGlobalImageLibarary> categoryImgUrlMap, ResourceType resourceType) {
		File[] tageFiles = tageFolder.listFiles();
		for (File file :  tageFiles) {
			if(file.isDirectory()) {
				importResourceDir(file, globalCategoryItem, tageFolder.getName(), categoryImgUrlMap, resourceType);
			} else {
				importResourceFile(file, globalCategoryItem, tageFolder.getName(), categoryImgUrlMap, resourceType);
			}
		}
	}
	
	private void importResourceDir(File resourceFile, EOGlobalSubCategory globalCategoryItem, String tageFolder, Map<String, EOGlobalImageLibarary> categoryImgUrlMap, ResourceType resourceType) {
		File[] listFiles = resourceFile.listFiles();
		for (File file :  listFiles) {
			if(file.isDirectory()) {
				importResourceDir(file, globalCategoryItem, tageFolder, categoryImgUrlMap, resourceType);
			} else {
				importResourceFile(file, globalCategoryItem,tageFolder, categoryImgUrlMap, resourceType);
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
	 * @param resourceType 
	 */
	private void importResourceFile(File file, EOGlobalSubCategory subCategory,  String tageFolder, Map<String, EOGlobalImageLibarary> categoryImgUrlMap, ResourceType resourceType) {
		try {
			String url = "resource/"+TAGS_WITH_IMAGES+file.getAbsolutePath().split(TAGS_WITH_IMAGES)[1].replace("\\", "/");
			String title=file.getName().split("\\.")[0];
			EOGlobalImageLibarary globalImageLibarary = categoryImgUrlMap.getOrDefault(url, new EOGlobalImageLibarary());
			globalImageLibarary.setIdenNo(url.replace("/", "_").replace(",", "_").toUpperCase().split("\\.")[0].replaceFirst("_", ""));
			globalImageLibarary.setResourceType(resourceType.toString());
			globalImageLibarary.setSubCategory(subCategory);
			globalImageLibarary.setName(file.getName());
			globalImageLibarary.setTitle(title);
			globalImageLibarary.setType(tageFolder);
			globalImageLibarary.setImageUrl(url);
			globalImageLibarary.setRecordState(RecordStatus.ACTIVETED.getStatus());
			globalImageLibarary=globalImageLibararyRepository.save(globalImageLibarary);
			globalImageLibararyService.saveImageTagMappings(subCategory, globalImageLibarary, title);
			categoryImgUrlMap.put(url, globalImageLibarary);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void exportData(File dirs) {
		if (!dirs.exists()) {
			dirs.mkdirs();
		}
		String global_portal_image_libarary_file_name = "/global_portal_image_libarary";
		globalImageLibararyRepository.findAll().stream()
		.collect(Collectors.groupingBy(globalTagLibarary -> globalTagLibarary.getSubCategory()))
		.forEach((subCategory, globalTagLibararyList) -> {
			exportBuildImageLibarary(dirs, global_portal_image_libarary_file_name, subCategory, globalTagLibararyList);
		});
	}
	
	protected void exportBuildImageLibarary(File dirs, String global_portal_image_libarary_file_name,
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
