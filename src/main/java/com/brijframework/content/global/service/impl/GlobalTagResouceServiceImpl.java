package com.brijframework.content.global.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.brijframework.json.schema.factories.JsonSchemaFile;
import org.brijframework.json.schema.factories.JsonSchemaObject;
import org.brijframework.util.accessor.PropertyAccessorUtil;
import org.brijframework.util.reflect.InstanceUtil;
import org.brijframework.util.support.ReflectionAccess;
import org.brijframework.util.text.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.constants.VisualiseType;
import com.brijframework.content.global.entities.EOGlobalSubCategory;
import com.brijframework.content.global.entities.EOGlobalTagLibarary;
import com.brijframework.content.global.mapper.GlobalTagLibararyMapper;
import com.brijframework.content.global.model.UIGlobalTagLibarary;
import com.brijframework.content.global.repository.GlobalSubCategoryRepository;
import com.brijframework.content.global.repository.GlobalTagLibararyRepository;
import com.brijframework.content.global.resource.UITagResource;
import com.brijframework.content.global.service.GlobalTagResourceService;
import com.brijframework.content.util.IdenUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;

@Service
public class GlobalTagResouceServiceImpl implements GlobalTagResourceService {

	@Autowired
	private GlobalTagLibararyRepository globalTagLibararyRepository;
	
	@Autowired
	private GlobalSubCategoryRepository globalSubCategoryRepository;

	@Autowired
	private GlobalTagLibararyMapper globalTagLibararyMapper;

	Map<String, String> keyMapper=new HashMap<>();
	{
		keyMapper.put("Tag Name".toUpperCase(), "name");
		keyMapper.put("Sub Category".toUpperCase(), "subCategoryName");
	}

	@Override
	public void init(List<EOGlobalTagLibarary> eoGlobalTagItemJson) {

		eoGlobalTagItemJson.forEach(eoGlobalTagItem -> {
			EOGlobalTagLibarary findGlobalTagItem = globalTagLibararyRepository
					.findByIdenNo(eoGlobalTagItem.getIdenNo()).orElse(eoGlobalTagItem);
			BeanUtils.copyProperties(eoGlobalTagItem, findGlobalTagItem, "id");
			findGlobalTagItem.setRecordState(RecordStatus.ACTIVETED.getStatus());
			findGlobalTagItem.setType(VisualiseType.WORDS_PAGE.getType());
			EOGlobalTagLibarary eoGlobalTagItemSave = globalTagLibararyRepository
					.saveAndFlush(findGlobalTagItem);
			eoGlobalTagItem.setId(eoGlobalTagItemSave.getId());
		});
	}
	

	protected void copyToAll() {
		File dirs = new File("C:/app_runs/unlimits-resources/resource/global_portal_tag_libarary");
		if (!dirs.exists()) {
			dirs.mkdirs();
		}
		String global_portal_prompt_libarary_file_name = "/global_portal_tag_libarary";
		globalTagLibararyRepository.findAll().stream()
		.filter(globalTagLibarary -> globalTagLibarary.getSubCategory() != null)
		.collect(Collectors.groupingBy(globalTagLibarary -> globalTagLibarary.getSubCategory()))
		.forEach((subCategory1, globalTagLibararyList) -> {
			globalSubCategoryRepository.findAll().forEach(subCategory -> {
				buildTagLibarary(dirs, global_portal_prompt_libarary_file_name, subCategory,
						globalTagLibararyList);
			});
		});
	}
	

	protected void export() {
		File dirs = new File("C:/app_runs/unlimits-resources/resource/global_portal_tag_libarary");
		if (!dirs.exists()) {
			dirs.mkdirs();
		}
		String global_portal_tag_libarary_file_name = "/global_portal_tag_libarary";
		globalTagLibararyRepository.findAll().stream()
				.collect(Collectors.groupingBy(globalTagLibarary -> globalTagLibarary.getSubCategory()))
				.forEach((subCategory, globalTagLibararyList) -> {
					buildTagLibarary(dirs, global_portal_tag_libarary_file_name, subCategory, globalTagLibararyList);
				});
	}

	protected void buildTagLibarary(File dirs, String global_portal_prompt_libarary_file_name,
			EOGlobalSubCategory subCategory, List<EOGlobalTagLibarary> globalTagLibararyList) {
		String fileName = global_portal_prompt_libarary_file_name + "_" + subCategory.getMainCategory().getName() + "_"
				+ IdenUtil.replaceContent(subCategory.getName()) + ".json";
		JsonSchemaFile jsonSchemaFile = new JsonSchemaFile();
		jsonSchemaFile.setId("Global_Portal_TagLibarary" + "_" + subCategory.getName());
		jsonSchemaFile.setOrderSequence(subCategory.getOrderSequence());
		globalTagLibararyList.forEach(globalTagLibarary -> {
			String idenNo = IdenUtil.buildIdenNo(subCategory, globalTagLibarary);
			JsonSchemaObject jsonObject = new JsonSchemaObject();
			jsonObject.setId(idenNo);
			jsonObject.setName("Global_Portal_TagLibarary");
			jsonObject.setType(globalTagLibarary.getClass().getName());
			jsonSchemaFile.setType(globalTagLibarary.getClass().getName());
			jsonObject.getProperties().put("idenNo", idenNo);
			jsonObject.getProperties().put("name", globalTagLibarary.getName());
			jsonObject.getProperties().put("orderSequence", globalTagLibarary.getOrderSequence());
			jsonObject.getProperties().put("color", subCategory.getColor());
			jsonObject.getProperties().put("type", globalTagLibarary.getType());
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
	

	@Override
	public List<UIGlobalTagLibarary> importCsv(String csvData) {
		List<UITagResource> csvList = loadFromCsvBuffer(csvData, UITagResource.class, keyMapper);
		List<String> subCategoryNameList = csvList.stream().filter(csvObject->StringUtil.isNonEmpty(csvObject.getSubCategoryName())).map(csvObject->csvObject.getSubCategoryName().toUpperCase()).distinct().toList();
		Map<String, EOGlobalSubCategory> subCategoryNameMap = globalSubCategoryRepository.findAllBySubCategoryNameIgnoreCaseIn(subCategoryNameList).stream().collect(Collectors.toMap(subCategory->subCategory.getName().toUpperCase(), subCategory->subCategory));
		final List<EOGlobalTagLibarary> dataList=new ArrayList<EOGlobalTagLibarary>();
		Map<String, List<UITagResource>> tagListGroupBySubCategoryName=csvList.stream().filter(csvObject->StringUtil.isNonEmpty(csvObject.getSubCategoryName())).collect(Collectors.groupingBy(UITagResource::getSubCategoryName));
		tagListGroupBySubCategoryName.forEach((subCategoryName, tagList)->{
			try {
				EOGlobalSubCategory eoGlobalSubCategory = subCategoryNameMap.get(subCategoryName.toUpperCase());
				if(eoGlobalSubCategory!=null) {
					Map<String, EOGlobalTagLibarary> tagLibararyMap = globalTagLibararyRepository.findAllBSubCategoryId(eoGlobalSubCategory.getId()).stream().collect(Collectors.toMap(tagLibarary->buildTagId(tagLibarary.getName(),tagLibarary.getSubCategory().getName()), tagLibarary->tagLibarary));
					Map<String, EOGlobalTagLibarary> dataListBySubCategory=new HashMap<String, EOGlobalTagLibarary>();
					for(UITagResource uiTagResource: csvList) {
						try {
							String tagId=buildTagId(uiTagResource.getName(),uiTagResource.getSubCategoryName());
							EOGlobalTagLibarary eoGlobalTagLibarary = tagLibararyMap.getOrDefault(tagId, new EOGlobalTagLibarary());
							eoGlobalTagLibarary.setName(uiTagResource.getName());
							eoGlobalTagLibarary.setType(VisualiseType.WORDS_PAGE.getType());
							eoGlobalTagLibarary.setRecordState(RecordStatus.ACTIVETED.getStatus());
							eoGlobalTagLibarary.setColor(eoGlobalSubCategory.getColor());
							eoGlobalTagLibarary.setSubCategory(eoGlobalSubCategory);
							eoGlobalTagLibarary.setIdenNo(IdenUtil.buildIdenNo(eoGlobalSubCategory, eoGlobalTagLibarary));
							eoGlobalTagLibarary=globalTagLibararyRepository.saveAndFlush(eoGlobalTagLibarary);
							tagLibararyMap.put(tagId, eoGlobalTagLibarary);
							dataListBySubCategory.put(tagId, eoGlobalTagLibarary);
						}catch (Exception e) {
							e.printStackTrace();
						}
					}
					tagLibararyMap.forEach((tagId, tag)->{
						if(!dataListBySubCategory.containsKey(tagId) && VisualiseType.WORDS_PAGE.getType().equalsIgnoreCase(tag.getType())) {
							tag.setRecordState(RecordStatus.DACTIVETED.getStatus());
							globalTagLibararyRepository.saveAndFlush(tag);
						}
					});
					dataList.addAll(dataListBySubCategory.values());
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		});
		return globalTagLibararyMapper.mapToDTO(dataList);
	}
	
	private String buildTagId(String tagName, String subCategoryName) {
		return tagName.toUpperCase().trim()+"_"+subCategoryName.toUpperCase();
	}

	public <T> List<T> loadFromCsvBuffer(String csvData, Class<T> type, Map<String, String> keyMapper) {
		List<T> list = new ArrayList<>();
		if (csvData == null || csvData.isEmpty()) {
			return list;
		}
		String[] csvDataArray = csvData.trim().split("\n");
		if (csvDataArray.length == 0) {
			System.err.println("Invalid csv file ");
		}
		String[] headerKeys = csvDataArray[0].split(",");
		
		for (int i = 1; i < csvDataArray.length; i++) {
			if (csvDataArray[i].trim().isEmpty()) {
				continue;
			}
			String[] dataValues = csvDataArray[i].trim().split(",");
			
			T instance = InstanceUtil.getInstance(type);
			for (int j = 0; j < headerKeys.length; j++) {
				String key =keyMapper.getOrDefault(headerKeys[j].trim().toUpperCase(), headerKeys[j]);
				Object value = "";
				if (j < dataValues.length) {
					value = dataValues[j];
				}
				PropertyAccessorUtil.setProperty(instance, key, ReflectionAccess.PRIVATE, value);
			}
			list.add(instance);
		}
		return list;
	}

	@Override
	public String exportCsv() {
		return null;
	}

}
